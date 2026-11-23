package com.gachi.directmessage.service;

import com.gachi.conversation.entity.Conversation;
import com.gachi.conversation.entity.ConversationUserState;
import com.gachi.conversation.exception.ConversationErrorCode;
import com.gachi.conversation.exception.ConversationException;
import com.gachi.conversation.repository.ConversationRepository;
import com.gachi.conversation.repository.ConversationUserStateRepository;
import com.gachi.directmessage.dto.DirectMessageCreateRequest;
import com.gachi.directmessage.dto.DirectMessageDto;
import com.gachi.directmessage.entity.DirectMessage;
import com.gachi.directmessage.mapper.DirectMessageMapper;
import com.gachi.directmessage.repository.DirectMessageRepository;
import com.gachi.notification.event.DirectMessageReceivedEvent;
import com.gachi.user.dto.UserSummaryDto;
import com.gachi.user.entity.User;
import com.gachi.user.exception.UserErrorCode;
import com.gachi.user.exception.UserException;
import com.gachi.user.mapper.UserMapper;
import com.gachi.user.repository.UserRepository;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectMessageService {

  private final ConversationRepository conversationRepository;
  private final ConversationUserStateRepository conversationUserStateRepository;
  private final DirectMessageRepository directMessageRepository;
  private final UserRepository userRepository;
  private final DirectMessageMapper directMessageMapper;
  private final UserMapper userMapper;
  private final ApplicationEventPublisher eventPublisher;

  @Transactional
  public DirectMessageDto create(
      UUID conversationId,
      DirectMessageCreateRequest request,
      UUID currentUserId
  ) {
    log.debug("DM 생성 처리를 시작합니다. conversationId={}, currentUserId={}", conversationId, currentUserId);

    Conversation conversation = conversationRepository.findById(conversationId)
        .orElseThrow(() -> new ConversationException(
            ConversationErrorCode.CONVERSATION_NOT_FOUND,
            Map.of("conversationId", conversationId)
        ));
    validateParticipant(conversation, currentUserId);

    User currentUser = findUser(currentUserId);
    UUID withUserId = resolveWithUserId(conversation, currentUserId);
    User withUser = findUser(withUserId);
    UserSummaryDto currentUserSummary = toUserSummaryDto(currentUser);
    UserSummaryDto withUserSummary = toUserSummaryDto(withUser);

    DirectMessage directMessage = directMessageRepository.save(
        DirectMessage.create(conversation, currentUser, request.content())
    );
    /// DM 동시 생성시 conversation.lastMessage가 lost update 방지
    conversationRepository.updateLastMessageIfNewer(
        conversationId,
        directMessage.getId(),
        directMessage.getCreatedAt(),
        directMessage.getContent(),
        currentUserId
    );
    increaseUnreadCount(conversation, withUserId);

    eventPublisher.publishEvent(new DirectMessageReceivedEvent(
        withUserId,
        currentUserId,
        currentUser.getName(),
        conversationId,
        request.content()
    ));

    DirectMessageDto response = directMessageMapper.toDto(
        directMessage,
        conversation,
        currentUserId,
        currentUserSummary,
        withUserSummary
    );

    log.debug("DM 생성 처리를 완료했습니다. directMessageId={}", response.id());
    return response;
  }

  private User findUser(UUID userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new UserException(
            UserErrorCode.USER_NOT_FOUND_EXCEPTION,
            Map.of("userId", userId)
        ));
  }

  private ConversationUserState createState(Conversation conversation, UUID userId) {
    ConversationUserState state = ConversationUserState.create(conversation, userId);
    ConversationUserState savedState = conversationUserStateRepository.saveAndFlush(state);
    return savedState != null ? savedState : state;
  }

  private void increaseUnreadCount(Conversation conversation, UUID userId) {
    int updatedCount = conversationUserStateRepository.increaseUnreadCount(
        conversation.getId(),
        userId
    );
    if (updatedCount > 0) {
      return;
    }

    ensureState(conversation, userId);
    conversationUserStateRepository.increaseUnreadCount(conversation.getId(), userId);
  }

  private void ensureState(Conversation conversation, UUID userId) {
    try {
      createState(conversation, userId);
    } catch (DataIntegrityViolationException ignored) {
      log.debug(
          "대화방 사용자 상태가 이미 생성되어 있습니다. conversationId={}, userId={}",
          conversation.getId(),
          userId
      );
    }
  }

  private UserSummaryDto toUserSummaryDto(User user) {
    return userMapper.toSummaryDto(
        user,
        user.getProfileImageUrl()
    );
  }

  private void validateParticipant(Conversation conversation, UUID userId) {
    if (!conversation.getUser1Id().equals(userId) && !conversation.getUser2Id().equals(userId)) {
      throw new ConversationException(
          ConversationErrorCode.CONVERSATION_FORBIDDEN,
          Map.of("conversationId", conversation.getId(), "userId", userId)
      );
    }
  }

  private UUID resolveWithUserId(Conversation conversation, UUID currentUserId) {
    if (conversation.getUser1Id().equals(currentUserId)) {
      return conversation.getUser2Id();
    }
    return conversation.getUser1Id();
  }
}
