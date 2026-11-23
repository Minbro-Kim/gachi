package com.gachi.content.repository.qdsl;

import com.gachi.common.pagination.SortDirection;
import com.gachi.content.entity.Content;
import com.gachi.content.enums.ContentSortBy;
import com.gachi.content.enums.ContentType;
import java.util.List;
import java.util.UUID;

public interface ContentQDSLRepository {

  List<Content> findAllByCursor(
      ContentType typeEqual,
      String keywordLike,
      List<String> tagsIn,
      String cursor,
      UUID idAfter,
      int limit,
      ContentSortBy sortBy,
      SortDirection sortDirection
  );

  long countContents(
      ContentType typeEqual,
      String keywordLike,
      List<String> tagsIn
  );
}
