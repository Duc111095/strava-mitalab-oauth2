package com.ducnh.oauth2_server.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ducnh.oauth2_server.dto.EventGeneralDTO;
import com.ducnh.oauth2_server.dto.SummaryEventDTO;
import com.ducnh.oauth2_server.dto.TeamDTO;
import com.ducnh.oauth2_server.dto.UserRankDTO;

@Service
public class SummaryService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<Object, Object> getSummaryGeneralById(String eventId) throws SQLException {
        System.out.println(eventId);
        String produceCall = "{call rpt_event_totals(?)}";
        Map<Object, Object> result = new HashMap<>();

        if (jdbcTemplate.getDataSource() == null) {
            throw new RuntimeException("Can't connect to the database");
        }

        Connection con = jdbcTemplate.getDataSource().getConnection();
        CallableStatement cstmt = con.prepareCall(produceCall);
        cstmt.setString(1, eventId);

        ResultSet t1 = cstmt.executeQuery();
        while (t1.next()) {
            EventGeneralDTO event = new EventGeneralDTO();
            event.setId(t1.getString("id"));
            event.setName(t1.getString("event_name"));
            event.setDescription(t1.getString("description"));
            event.setTotalDistance(t1.getDouble("total_distance"));
            event.setTotalDistanceRun(t1.getDouble("total_distance_run"));
            event.setTotalDistanceRide(t1.getDouble("total_distance_ride"));
            result.put("event", event);
        }

        if (cstmt.getMoreResults()) {
            ResultSet t2 = cstmt.getResultSet();
            List<UserRankDTO> top_3_nam_run = new ArrayList<>();
            while (t2.next()) {
                UserRankDTO user = new UserRankDTO();
                user.setId(t2.getString("athlete_id"));
                user.setName(t2.getString("athlete_name"));
                user.setTeamId(t2.getInt("team_id_t")); 
                user.setTotalDistance(t2.getDouble("total_distance"));
                top_3_nam_run.add(user);
            }
            result.put("top_3_nam_run", top_3_nam_run);
        }       
        
        if (cstmt.getMoreResults()) {
            ResultSet t3 = cstmt.getResultSet();
            List<UserRankDTO> top_3_nu_run = new ArrayList<>();

            while (t3.next()) {
                UserRankDTO user = new UserRankDTO();
                user.setId(t3.getString("athlete_id"));
                user.setName(t3.getString("athlete_name"));
                user.setTeamId(t3.getInt("team_id_t")); 
                user.setTotalDistance(t3.getDouble("total_distance"));
                top_3_nu_run.add(user);
            }

            result.put("top_3_nu_run", top_3_nu_run);
        }

        if (cstmt.getMoreResults()) {
            ResultSet t4 = cstmt.getResultSet();
            List<UserRankDTO> top_2_ride = new ArrayList<>();
            while (t4.next()) {
                UserRankDTO user = new UserRankDTO();
                user.setId(t4.getString("athlete_id"));
                user.setName(t4.getString("athlete_name"));
                user.setTeamId(t4.getInt("team_id_t")); 
                user.setTotalDistance(t4.getDouble("total_distance"));
                top_2_ride.add(user);
            }
            result.put("top_2_ride", top_2_ride);
        }

        if (cstmt.getMoreResults()) {
            ResultSet t5 = cstmt.getResultSet();
            List<TeamDTO> team_rank = new ArrayList<>();

            while (t5.next()) {
                TeamDTO team = new TeamDTO();
                team.setTeamId(t5.getInt("team_id"));
                team.setName("Đội " + t5.getInt("team_id"));
                team.setRunKm(t5.getDouble("total_distance_accepted"));
                team.setId(t5.getInt("stt"));
                team_rank.add(team);
            }
            result.put("team_rank", team_rank);
        }

        return result;
    }
}
