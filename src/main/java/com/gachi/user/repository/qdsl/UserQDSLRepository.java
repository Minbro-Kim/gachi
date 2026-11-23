package com.gachi.user.repository.qdsl;

import com.gachi.common.pagination.SortDirection;
import com.gachi.user.entity.User;
import com.gachi.user.enums.UserSortBy;
import java.util.List;
import java.util.UUID;

public interface UserQDSLRepository {
  List<User> findUsers(String keyword, UserSortBy orderBy, SortDirection direction, String cursor, UUID idAfter, int limit);
  long countUsers(String keyword);
}
