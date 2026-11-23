package com.gachi.content.service;

import com.gachi.common.config.CacheConfig;
import com.gachi.content.dto.ContentDetailCacheDto;
import com.gachi.content.entity.Content;
import com.gachi.content.exception.ContentErrorCode;
import com.gachi.content.exception.ContentException;
import com.gachi.content.repository.ContentRepository;
import com.gachi.content.repository.ContentTagRepository;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentDetailCacheService {

  private final ContentRepository contentRepository;
  private final ContentTagRepository contentTagRepository;

  @Cacheable(cacheNames = CacheConfig.CONTENT_DETAIL, key = "#contentId", sync = true)
  @Transactional(readOnly = true)
  public ContentDetailCacheDto find(UUID contentId) {
    Content content = contentRepository.findById(contentId)
        .orElseThrow(() -> {
          log.warn("콘텐츠 단건 캐시 조회 실패: 콘텐츠 없음. contentId={}", contentId);
          return new ContentException(
              ContentErrorCode.CONTENT_NOT_FOUND,
              Map.of("contentId", contentId)
          );
        });

    return new ContentDetailCacheDto(
        content.getId(),
        content.getType(),
        content.getTitle(),
        content.getDescription(),
        ContentImageUrlResolver.resolve(content),
        contentTagRepository.findTagNamesByContentId(contentId),
        content.getAverageRating(),
        content.getReviewCount()
    );
  }
}
