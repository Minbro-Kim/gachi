package com.gachi.notification.entity;

import com.gachi.common.baseentity.BaseEntity;
import com.gachi.notification.enums.NotificationLevel;
import com.gachi.notification.enums.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

  @Column(nullable = false)
  private UUID receiverId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private NotificationType type;

  @Column(nullable = false, length = 100)
  private String title;

  @Column(nullable = false, length = 500)
  private String content;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private NotificationLevel level;

  @Column(nullable = false)
  private boolean isRead;

  @Builder
  public Notification(UUID receiverId, NotificationType type, String title,
      String content, NotificationLevel level) {
    this.receiverId = receiverId;
    this.type = type;
    this.title = title;
    this.content = content;
    this.level = level;
    this.isRead = false;
  }

  public void markAsRead() {
    this.isRead = true;
  }

}
