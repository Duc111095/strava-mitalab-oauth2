package com.ducnh.oauth2_server.model;

import java.time.LocalDateTime;

import com.ducnh.oauth2_server.model.constants.MeasurementPreference;
import com.ducnh.oauth2_server.model.constants.ResourceState;
import com.ducnh.oauth2_server.model.constants.StravaGender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AthleteUser {
	private Long id;
	private String username;
	private ResourceState resourceState;
	private String firstName;
	private String lastName;
	private String city;
	private String state;
	private String country;
	private StravaGender sex;
	private boolean premium;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Integer badgeTypeId;
	private String profileMedium;
	private String profile;
	private String friend;
	private String follower;
	private Integer followerCount;
	private Integer friendCount;
	private Integer mutualFriendCount;
	private Integer athleteType;
	private String datePreference;
	private MeasurementPreference measurementPreference;
	private Integer ftp;
	private Float weights;
	private StravaClub[] clubs;
	private StravaBike[] bikes;
	private StravaShoe[] shoes;
}
