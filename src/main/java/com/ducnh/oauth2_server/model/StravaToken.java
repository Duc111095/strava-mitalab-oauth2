package com.ducnh.oauth2_server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "token_strava")
public class StravaToken {
	@Id
	private Long atheleteId;
	
	@Column(nullable = false, length = 24)
	private String tokenType;
	
	private String accessToken;
	private String refreshToken;
	private Long expiresAt;
	private Long expiresIn;
	
	public StravaToken(Long atheleteId, String tokenType, String accessToken, String refreshToken, Long expiresAt, Long expiresIn) {
		this.atheleteId = atheleteId;
		this.tokenType = tokenType;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresAt = expiresAt;
		this.expiresIn = expiresIn;
	}
	
	public StravaToken() {}
	
	public Long getAtheleteId() {
		return atheleteId;
	}
	
	public void setAtheleteId(Long id) {
		this.atheleteId = id;
	}
	
	public String getTokenType() {
		return this.tokenType;
	}
	
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
	public String getAccessToken() {
		return this.accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getRefreshToken() {
		return this.refreshToken;
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	} 
	
	public Long getExpiresAt() {
		return this.expiresAt;
	}
	
	public void setExpiresAt(Long expiresAt) {
		this.expiresAt = expiresAt;
	}
	
	public Long getExpiresIn() {
		return this.expiresIn;
	}
	
	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	public static class Builder {
		private Long atheleteId;
		private String tokenType;
		private String accessToken;
		private String refreshToken;
		private Long expiresAt;
		private Long expiresIn;
		
		public Builder(Long atheleteId) {
			this.atheleteId = atheleteId;
		}
		
		public Builder withTokenType(String tokenType) {
			this.tokenType = tokenType;
			return this;
		}
		
		public Builder accessToken(String accessToken) {
			this.accessToken = accessToken;
			return this;
		}
		
		public Builder refreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
			return this;
		}
		
		public Builder expiresAt(Long expiresAt) {
			this.expiresAt = expiresAt;
			return this;
		}
		
		public Builder expiresIn(Long expiresIn) {
			this.expiresIn = expiresIn;
			return this;
		}
		
		public StravaToken build() {
			StravaToken token = new StravaToken();
			token.setAtheleteId(this.atheleteId);
			token.setAccessToken(this.accessToken);
			token.setRefreshToken(this.refreshToken);
			token.setExpiresAt(this.expiresAt);
			token.setExpiresIn(this.expiresIn);
			token.setTokenType(this.tokenType);
			return token;
		}
	}
}

