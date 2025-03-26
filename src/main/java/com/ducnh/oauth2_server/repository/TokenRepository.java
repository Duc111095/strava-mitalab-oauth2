package com.ducnh.oauth2_server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ducnh.oauth2_server.model.StravaToken;

@Repository
public interface TokenRepository extends CrudRepository<StravaToken, Long>{

}
