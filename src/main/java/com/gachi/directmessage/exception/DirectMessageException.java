package com.gachi.directmessage.exception;

import com.gachi.common.error.ErrorCode;
import com.gachi.common.error.ModuPlyException;
import java.util.Map;

public class DirectMessageException extends ModuPlyException {

  public DirectMessageException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }

  public DirectMessageException(ErrorCode errorCode, Map<String, Object> details, Throwable cause) {
    super(errorCode, details, cause);
  }
}
