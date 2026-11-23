package com.gachi.content.service;

import com.gachi.content.entity.Content;

public final class ContentImageUrlResolver {

  private static final String DEFAULT_THUMBNAIL_URL = "/placeholder-movie.png";

  private ContentImageUrlResolver() {
  }

  public static String resolve(Content content) {
    String thumbnailUrl = content.getThumbnailUrl();
    return thumbnailUrl != null ? thumbnailUrl : DEFAULT_THUMBNAIL_URL;
  }
}
