package com.ducnh.oauth2_server.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ducnh.oauth2_server.dto.ActivitiesDTO;
import com.ducnh.oauth2_server.model.ActivitySummary;
import com.ducnh.oauth2_server.model.PolylineMap;
import com.ducnh.oauth2_server.model.StravaActivity;
import com.ducnh.oauth2_server.repository.ActivityRepository;
import com.ducnh.oauth2_server.repository.MapRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Service
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepo;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private MapRepository mapRepo;

	@Autowired
	private SplitMetricService metricService;

	@Value("${strava.url.activities.lap}")
	private String lapUrl;
	
	@Value("${strava.url.athlete.activities}")
	private String activitiesUrl;

	@Value("${strava.url.athlete.activitiesafter0104}")
	private String activitiesUrlAfter0104;

	@Autowired
	@PersistenceContext
	private EntityManager em;
	
	public void save(StravaActivity activity) {
		activityRepo.save(activity);
	}
	
	public Iterable<StravaActivity> findAll() {
		return activityRepo.findAll();
	}
	
	public List<ActivitySummary> getActivitySummaryByPeriodDate(LocalDateTime startDate, LocalDateTime endDate) {
		StoredProcedureQuery spQuery = em.createNamedStoredProcedureQuery("summary_activities");
		spQuery.setParameter("start_date", startDate);
		spQuery.setParameter("end_date", endDate);
		spQuery.setParameter("type_summary", "1");
		spQuery.execute();

		List<Object[]> listObject = spQuery.getResultList();
		List<ActivitySummary> result = new ArrayList<>();
		for (Object[] object : listObject) {
			result.add(new ActivitySummary(new Long((Integer)object[0]), (String)object[1], 
									(double)object[2], (int)object[3],
									(int)object[4], (double)object[5]));
		}
				
		return result;
	}

    public Object findAllByAthleteId(Long athleteId) {
		return activityRepo.findByAthleteId(athleteId);   
	}

	public List<Map<String, Object>> listExtendedActivities(Long athleteId) {
		return activityRepo.listExtendedActivities(athleteId);
	}

	public List<Object[]> getSummaryEvent(String eventId, int teamId, int type) {
		Query query = em.createNativeQuery("call rpt_event_schedule(?, ?, ?)");
		query.setParameter(1, eventId);
		query.setParameter(2, teamId);
		query.setParameter(3, type);
		List<Object[]> list = query.getResultList();
		return list;
	}

	public void saveActivitiesFromStravaResponse(Long athleteId) {
		ResponseEntity<String> resultActivites = tokenService.sendGetRequest(athleteId, activitiesUrlAfter0104);
		try {
			// Save activities to database
			ArrayNode treeActivityRoot = (ArrayNode) mapper.readTree(resultActivites.getBody());
			if (treeActivityRoot.isArray() && treeActivityRoot.size() > 0) {
				for (JsonNode root : treeActivityRoot) {
					Long activityId = Long.parseLong(root.get("id").asText());
				
					// Get the map data from the response
					PolylineMap map = new PolylineMap();
					map.setId(root.get("map").get("id") == null ? null : root.get("map").get("id").asText());
					map.setSummaryPolyline(root.get("map").get("summary_polyline") == null ? null : root.get("map").get("summary_polyline").asText());
					mapRepo.save(map);

					// Create a new StravaActivity object and set the map
					StravaActivity activity = new StravaActivity();
					activity = StravaActivity.createActivityFromResponse(root);
					activity.setMap(map);
					this.save(activity);

					// Fetch the lap data for the activity has start_date > now - 3 days
					LocalDate startDate = LocalDate.parse(root.get("start_date_local").asText(), StravaActivity.formatter);
					if (startDate.isBefore(LocalDate.now().minusDays(60))) {
						continue; 
					}
					// Save Metrics for the activity
					// Save metrics not found in the database
					if (!metricService.existsByActivityId(activityId)) {
						metricService.saveSplitMetricFromStrava(activityId, athleteId);
					}
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<ActivitiesDTO> convertToActivitiesDTO(List<Map<String, Object>> listExtendedActivities) {
		List<ActivitiesDTO> listActivitiesDTO = new ArrayList<>();
		listExtendedActivities.stream().forEach(extendedActivity -> {
			ActivitiesDTO activitiesDTO = new ActivitiesDTO();
			activitiesDTO.setAthleteID(extendedActivity.get("athlete_id") != null ? Long.parseLong(extendedActivity.get("athlete_id").toString()) : null);
			activitiesDTO.setAthleteName(extendedActivity.get("athlete_name") != null ? extendedActivity.get("athlete_name").toString() : null);
			activitiesDTO.setDistance(extendedActivity.get("distance") != null ? Double.parseDouble(extendedActivity.get("distance").toString()) : null);
			activitiesDTO.setMovingTime(extendedActivity.get("moving_time") != null ? Integer.parseInt(extendedActivity.get("moving_time").toString()) : null);
			activitiesDTO.setStartDateLocal(extendedActivity.get("start_date_local") != null ? ((Timestamp)extendedActivity.get("start_date_local")).toLocalDateTime() : null);
			activitiesDTO.setActivityName(extendedActivity.get("name") != null ? extendedActivity.get("name").toString() : null);
			activitiesDTO.setActivityID(extendedActivity.get("id") != null ? Long.parseLong(extendedActivity.get("id").toString()) : null);
			
			String mapId = extendedActivity.get("map_id") != null ? extendedActivity.get("map_id").toString() : null;
			
			if (mapId != null) {
				PolylineMap map = mapRepo.findById(mapId).orElse(null);
				if (map != null) {
					activitiesDTO.setMap(map);
				}
			}
			
			listActivitiesDTO.add(activitiesDTO);
		});
		return listActivitiesDTO;
	}

}
