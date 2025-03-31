package com.ducnh.oauth2_server.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Service;

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

}
