package com.gachi.playlist.repository.qdsl;

import com.gachi.playlist.dto.PlaylistSearchRequest;
import com.gachi.playlist.entity.Playlist;
import java.util.List;

public interface PlaylistQDSLRepository {

  List<Playlist> findAllWithCursor(PlaylistSearchRequest request);

  long countWithCondition(PlaylistSearchRequest request);

}
