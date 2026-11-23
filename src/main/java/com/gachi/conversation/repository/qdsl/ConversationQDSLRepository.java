package com.gachi.conversation.repository.qdsl;

import com.gachi.conversation.dto.ConversationFindAllRequest;
import com.gachi.conversation.dto.ConversationListItemDto;
import com.gachi.conversation.entity.Conversation;
import java.util.List;
import java.util.UUID;

public interface ConversationQDSLRepository {

  List<Conversation> findAllWithCursor(ConversationFindAllRequest request, UUID currentUserId);

  List<ConversationListItemDto> findAllDtoWithCursor(ConversationFindAllRequest request, UUID currentUserId);

  long countWithCondition(ConversationFindAllRequest request, UUID currentUserId);
}
