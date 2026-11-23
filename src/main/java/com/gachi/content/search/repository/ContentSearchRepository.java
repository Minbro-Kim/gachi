package com.gachi.content.search.repository;

import com.gachi.content.search.document.ContentSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ContentSearchRepository extends ElasticsearchRepository<ContentSearchDocument, String> {
}
