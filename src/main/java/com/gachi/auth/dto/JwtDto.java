package com.gachi.auth.dto;

import com.gachi.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class JwtDto {
  private UserDto userDto;
  private String accessToken;
}
