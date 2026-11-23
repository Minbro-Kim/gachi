package com.gachi.watching.dto;

import com.gachi.user.dto.UserSummary;
import java.util.Objects;

public record ContentChatDto(
    UserSummary sender,
    String content
) {

  public ContentChatDto {
    Objects.requireNonNull(sender);
    Objects.requireNonNull(content);
  }

}
