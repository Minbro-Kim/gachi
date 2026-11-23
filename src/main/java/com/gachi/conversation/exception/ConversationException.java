package com.gachi.conversation.exception;

import com.gachi.common.error.ErrorCode;
import com.gachi.common.error.ModuPlyException;
import java.util.Map;

public class ConversationException extends ModuPlyException {

  public ConversationException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }

  public ConversationException(ErrorCode errorCode, Map<String, Object> details, Throwable cause) {
    super(errorCode, details, cause);
  }
}
