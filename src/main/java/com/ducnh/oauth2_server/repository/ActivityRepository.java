package com.ducnh.oauth2_server.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ducnh.oauth2_server.model.StravaActivity;

@Repository
public interface ActivityRepository extends CrudRepository<StravaActivity, Long>{
    List<StravaActivity> findByAthleteId(Long athleteId);

    @Query(value = "SELECT a.id, a.name, a.athlete_id, a.distance, a.moving_time, a.start_date_local, concat(b.first_name, \' \', b.last_name) as athlete_name, a.map_id FROM strava_activity a join strava_user b on a.athlete_id = b.id WHERE athlete_id = ?1 ORDER BY a.start_date_local DESC", nativeQuery = true) 
    List<Map<String,Object>> listExtendedActivities(Long athleteId);
}


