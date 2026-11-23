package com.gachi.watching.dto;

import com.gachi.content.dto.ContentSummary;
import com.gachi.user.dto.UserSummary;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record WatchingSessionDto(

    @Schema(description = "시청 세션 ID")
    @NotNull
    UUID id,

    @Schema(description = "시청 세션 생성 시간")
    @NotNull
    Instant createdAt,

    @Schema(description = "시청자 정보")
    @NotNull
    UserSummary watcher,

    @Schema(description = "시청 중인 콘텐츠 정보")
    @NotNull
    ContentSummary content
) {

}
