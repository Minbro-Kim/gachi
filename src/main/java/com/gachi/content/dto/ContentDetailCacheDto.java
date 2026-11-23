package com.gachi.content.dto;

import com.gachi.content.enums.ContentType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record ContentDetailCacheDto(
    UUID id,
    ContentType type,
    String title,
    String description,
    String thumbnailUrl,
    List<String> tags,
    BigDecimal averageRating,
    int reviewCount
) {
  public ContentDetailCacheDto {
    tags = tags == null ? new ArrayList<>() : new ArrayList<>(tags);
  }
}
