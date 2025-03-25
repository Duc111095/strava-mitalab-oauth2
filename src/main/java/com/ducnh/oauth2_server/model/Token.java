package com.ducnh.oauth2_server.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsContructor
public class Token {
	@Id
	private Long atheleteId;
	private String tokenType;
	private String accessToken;
	private String refreshToken;
	private Long expiresAt;
	private Long expiresIn;
}
