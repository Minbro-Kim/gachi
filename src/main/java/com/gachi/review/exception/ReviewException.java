package com.gachi.review.exception;

import com.gachi.common.error.ErrorCode;
import com.gachi.common.error.ModuPlyException;
import java.util.Map;

public class ReviewException extends ModuPlyException {

  public ReviewException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }
}
