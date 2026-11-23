package com.gachi.content.external.dto;

import com.gachi.content.enums.ContentType;
import java.util.List;

public record ExternalContentItem(
    String externalApiId,
    ContentType type,
    String title,
    String description,
    String thumbnailUrl,
    List<String> tags
) {
}
