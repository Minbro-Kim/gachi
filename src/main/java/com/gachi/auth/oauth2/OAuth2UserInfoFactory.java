package com.gachi.auth.oauth2;

import com.gachi.auth.exception.AuthErrorCode;
import com.gachi.auth.exception.AuthException;
import com.gachi.auth.oauth2.info.GoogleUserInfo;
import com.gachi.auth.oauth2.info.KakaoUserInfo;
import com.gachi.auth.oauth2.info.OAuth2UserInfo;
import java.util.Map;

public class OAuth2UserInfoFactory {
  public static OAuth2UserInfo getOAuth2UserInfo(String provider, Map<String, Object> attributes) {
    return switch (provider.toLowerCase()) {
      case "google" -> new GoogleUserInfo(attributes);
      case "kakao" -> new KakaoUserInfo(attributes);
      default -> throw new AuthException(AuthErrorCode.INVALID_PROVIDER_EXCEPTION, Map.of(
          "provider", provider
      ));
    };
  }

}
