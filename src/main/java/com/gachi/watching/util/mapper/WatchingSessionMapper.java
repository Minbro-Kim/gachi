package com.gachi.watching.util.mapper;

import com.gachi.content.dto.ContentSummary;
import com.gachi.watching.dto.WatchingSessionDto;
import com.gachi.watching.model.WatchingSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WatchingSessionMapper {

  @Mapping(source = "session.id", target = "id")
  @Mapping(source = "content", target = "content")
  WatchingSessionDto toDto(WatchingSession session, ContentSummary content);
}
