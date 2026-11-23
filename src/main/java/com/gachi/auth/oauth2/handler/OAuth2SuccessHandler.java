package com.gachi.auth.oauth2.handler;

import com.gachi.auth.JwtTokenProvider;
import com.gachi.auth.session.BrowserIdResolver;
import com.gachi.auth.service.AuthService;
import com.gachi.auth.userdetails.ModuPlyUserDetails;
import com.gachi.common.enums.RedisKeyPolicy;
import com.gachi.common.util.RedisUtil;
import com.gachi.user.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final JwtTokenProvider jwtTokenProvider;
  private final RedisUtil redisUtil;
  private final AuthService authService;
  private final BrowserIdResolver browserIdResolver;
  @Value("${jwt.refresh-token-expiration-minutes}")
  private int refreshTokenExpirationMinutes;
  @Value("${jwt.oauth-success-url}")
  private String redirectUrl;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    ModuPlyUserDetails userDetails = (ModuPlyUserDetails) authentication.getPrincipal();

    UserDto userDto = userDetails.getUserDto();

    // OAuth 로그인도 토큰 재발급 전에 사용자 토큰 버전을 준비한다.
    String tokenVersionKey =
        RedisKeyPolicy.USER_TOKEN_VERSION.generateKey(userDto.getEmail());
    redisUtil.setDataIfAbsent(tokenVersionKey, "0");

    // 일반 로그인과 동일하게 OAuth 로그인도 브라우저 단위 세션을 생성한다.
    // 같은 브라우저에서 OAuth로 다시 로그인하면 이전 세션은 REVOKED 되고 새 세션만 살아남는다.
    String browserId = browserIdResolver.resolveOrCreate(request, response);
    String sessionId = authService.createSession(userDto.getId(), browserId);

    // OAuth 성공 응답은 Access Token을 URL에 싣지 않으므로 Refresh Token만 발급한다.
    // 이후 프론트가 /api/auth/refresh를 호출하면 같은 sessionId로 Access Token을 복원한다.
    String refreshToken = jwtTokenProvider.generateRefreshToken(authentication, sessionId);

    // 다른 브라우저 세션과 충돌하지 않도록 sessionId 기준으로 Refresh Token을 저장한다.
    String redisKey = RedisKeyPolicy.REFRESH_TOKEN.generateKey(sessionId);
    redisUtil.setDataExpire(redisKey, refreshToken, RedisKeyPolicy.REFRESH_TOKEN.getTtl());

    ResponseCookie responseCookie = ResponseCookie.from("REFRESH_TOKEN", refreshToken)
        .httpOnly(true)
        .secure(true)
        .path("/").sameSite("Lax")
        .maxAge(refreshTokenExpirationMinutes * 60)
        .build();

    response.addHeader("Set-Cookie", responseCookie.toString());

    // Access Token은 URL에 노출하지 않고, Refresh Token 쿠키 기반 재발급 흐름으로 복원한다.
    getRedirectStrategy().sendRedirect(request, response, redirectUrl);
  }
}
