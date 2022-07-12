package com.k2dev.hospital.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
	  private String token;
	  @Builder.Default
	  private final String type = "Bearer";
	  private User user;
}
