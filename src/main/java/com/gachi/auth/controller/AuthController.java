package com.gachi.auth.controller;

import com.gachi.auth.dto.TokenRefreshDto;
import com.gachi.auth.service.AuthService;
import com.gachi.auth.JwtTokenProvider;
import com.gachi.auth.dto.JwtDto;
import com.gachi.auth.dto.ResetPasswordRequest;
import com.gachi.auth.exception.AuthErrorCode;
import com.gachi.auth.exception.AuthException;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApi {
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthService authService;
  @Value("${jwt.refresh-token-expiration-minutes}")
  private int refreshTokenExpirationMinutes;

  @GetMapping("/csrf-token")
  @Override
  public ResponseEntity<Void> getCsrfToken(CsrfToken csrfToken) {
    csrfToken.getToken();
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/refresh")
  @Override
  public ResponseEntity<JwtDto> refreshAccessToken(
      @CookieValue(value = "REFRESH_TOKEN", required = false) String refreshToken
  ) {
    if (refreshToken == null || refreshToken.isBlank()) {
      throw new AuthException(AuthErrorCode.MISSING_TOKEN_EXCEPTION, Map.of(
          "tokenType", "refresh"
      ));
    }

    TokenRefreshDto tokenRefreshDto = authService.refreshTokens(refreshToken);

    ResponseCookie newRefreshTokenCookie = ResponseCookie.from("REFRESH_TOKEN",
        tokenRefreshDto.getRefreshToken())
        .httpOnly(true)
        .secure(true)
        .path("/")
        .maxAge(refreshTokenExpirationMinutes * 60)
        .sameSite("Lax")
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, newRefreshTokenCookie.toString())
        .body(tokenRefreshDto.getJwtDto());
  }

  @PostMapping("/reset-password")
  @Override
  public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
    authService.resetPassword(request);
    return ResponseEntity.noContent().build();
  }
}
