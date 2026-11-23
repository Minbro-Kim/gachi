package com.gachi.notification.controller;

import com.gachi.common.pagination.CursorResponse;
import com.gachi.notification.dto.NotificationDto;
import com.gachi.notification.dto.NotificationSearchRequest;
import com.gachi.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController implements NotificationApi {

  private final NotificationService notificationService;

  @GetMapping
  public ResponseEntity<CursorResponse<NotificationDto>> getNotifications(
      @ModelAttribute @Valid NotificationSearchRequest request,
      @AuthenticationPrincipal(expression = "userDto.id") UUID receiverId) {
    return ResponseEntity.ok(notificationService.findAll(request, receiverId));
  }

  @DeleteMapping("/{notificationId}")
  @Operation(summary = "알림 읽음 처리", description = "알림을 읽음 처리합니다.")
  public ResponseEntity<Void> markAsRead(
      @PathVariable UUID notificationId,
      @AuthenticationPrincipal(expression = "userDto.id") UUID receiverId) {
    notificationService.markAsRead(notificationId, receiverId);
    return ResponseEntity.noContent().build();
  }
}
