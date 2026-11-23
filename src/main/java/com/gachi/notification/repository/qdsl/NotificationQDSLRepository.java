package com.gachi.notification.repository.qdsl;

import com.gachi.notification.dto.NotificationSearchRequest;
import com.gachi.notification.entity.Notification;
import java.util.List;
import java.util.UUID;

public interface NotificationQDSLRepository {

  List<Notification> findAllWithCursor(NotificationSearchRequest request, UUID receiverId);

  long countWithCondition(UUID receiverId);

}
