package com.gachi.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenRefreshDto {
  private JwtDto jwtDto;
  private String refreshToken;
}
