package com.gachi.content.service;

import com.gachi.common.config.CacheConfig;
import com.gachi.content.repository.ContentTagRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentTagCacheService {

  private final ContentTagRepository contentTagRepository;

  @Cacheable(cacheNames = CacheConfig.CONTENT_TAGS, key = "#contentId", sync = true)
  @Transactional(readOnly = true)
  public List<String> findTagNamesByContentId(UUID contentId) {
    return contentTagRepository.findTagNamesByContentId(contentId);
  }
}
