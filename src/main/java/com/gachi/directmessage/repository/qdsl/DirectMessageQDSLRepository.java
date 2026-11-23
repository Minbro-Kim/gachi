package com.gachi.directmessage.repository.qdsl;

import com.gachi.directmessage.entity.DirectMessage;
import com.gachi.directmessage.dto.DirectMessageFindAllRequest;
import com.gachi.directmessage.dto.LatestDirectMessageDto;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface DirectMessageQDSLRepository {

  List<LatestDirectMessageDto> findLatestMessagesByConversationIds(Collection<UUID> conversationIds);

  /// 특정 대화방의 DM 목록 조회
  List<DirectMessage> findAllWithCursor(DirectMessageFindAllRequest request, UUID conversationId);
  /// 특정 대화방의 DM 총 개수 조회
  long countWithCondition(UUID conversationId);
}
