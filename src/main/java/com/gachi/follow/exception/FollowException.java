package com.gachi.follow.exception;

import com.gachi.common.error.ErrorCode;
import com.gachi.common.error.ModuPlyException;
import java.util.Map;
import java.util.UUID;

public class FollowException extends ModuPlyException {

  public FollowException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }

  public FollowException(ErrorCode errorCode, UUID id) {
    super(errorCode, id);
  }
}
