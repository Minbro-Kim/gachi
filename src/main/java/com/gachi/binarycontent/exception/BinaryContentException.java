package com.gachi.binarycontent.exception;

import com.gachi.common.error.ErrorCode;
import com.gachi.common.error.ModuPlyException;
import java.util.Map;

public class BinaryContentException extends ModuPlyException {

  public BinaryContentException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }

  public BinaryContentException(ErrorCode errorCode, Map<String, Object> details, Throwable cause) {
    super(errorCode, details, cause);
  }
}
