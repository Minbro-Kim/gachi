package com.gachi.user.exception;

import com.gachi.common.error.ErrorCode;
import com.gachi.common.error.ModuPlyException;
import java.util.Map;
import java.util.UUID;

public class UserException extends ModuPlyException {

  public UserException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }

  public UserException(ErrorCode errorCode, Map<String, Object> details, Throwable cause) {
    super(errorCode, details, cause);
  }

  public UserException(ErrorCode errorCode, UUID id) {
    super(errorCode, id);
  }
}
