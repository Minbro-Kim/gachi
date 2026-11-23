package com.gachi.watching.controller;

import com.gachi.common.pagination.CursorResponse;
import com.gachi.watching.controller.api.WatchingSessionApi;
import com.gachi.watching.dto.WatchingSessionDto;
import com.gachi.watching.dto.WatchingSessionQueryCondition;
import com.gachi.watching.service.WatchingSessionService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WatchingSessionController implements WatchingSessionApi {

  private final WatchingSessionService watchingSessionService;

  @GetMapping("/users/{watcherId}/watching-sessions")
  public ResponseEntity<WatchingSessionDto> findWatchingSessionsByWatcher(
      @PathVariable UUID watcherId) {
    WatchingSessionDto response = watchingSessionService.findByUserId(watcherId);
    if (response == null) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(response);
  }

  @GetMapping("/contents/{contentId}/watching-sessions")
  public ResponseEntity<CursorResponse<WatchingSessionDto>> findWatchingSessionsByContent(
      @PathVariable UUID contentId,
      @Valid @ModelAttribute WatchingSessionQueryCondition condition) {
    CursorResponse<WatchingSessionDto> response = watchingSessionService.findAllByContentId(
        contentId, condition);
    return ResponseEntity.ok(response);
  }
}
