package com.gachi.playlist.exception;

import com.gachi.common.error.ErrorCode;
import com.gachi.common.error.ModuPlyException;
import java.util.Map;

public class PlaylistException extends ModuPlyException {

  public PlaylistException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }

  public PlaylistException(ErrorCode errorCode, Map<String, Object> details, Throwable cause) {
    super(errorCode, details, cause);
  }
}