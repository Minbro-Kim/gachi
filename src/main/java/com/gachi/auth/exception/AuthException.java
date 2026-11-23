package com.gachi.auth.exception;

import com.gachi.common.error.ErrorCode;
import com.gachi.common.error.ModuPlyException;
import java.util.Map;
import java.util.UUID;

public class AuthException extends ModuPlyException {

  public AuthException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }

  public AuthException(ErrorCode errorCode, UUID id) {
    super(errorCode, id);
  }
}
