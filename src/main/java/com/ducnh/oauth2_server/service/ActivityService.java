package com.ducnh.oauth2_server.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducnh.oauth2_server.dto.SummaryEventDTO;
import com.ducnh.oauth2_server.model.ActivitySummary;
import com.ducnh.oauth2_server.model.StravaActivity;
import com.ducnh.oauth2_server.repository.ActivityRepository;

@Service
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepo;
	
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
}
