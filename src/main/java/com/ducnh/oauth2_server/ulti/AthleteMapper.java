package com.ducnh.oauth2_server.ulti;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.ducnh.oauth2_server.model.AthleteUser;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AthleteMapper {
	public static AthleteUser convertStringResponseToAthlete(String athleteJson) {
		//String atheleJson = 
		
		return null;
	}
	
	public static Map<String, Field> mapFieldsAnnotations(Class<?> clazz, Class<? extends Annotation> ann) {
		Map<String, Field> map = new HashMap<>();
		Class<?> c = clazz;
		while (c != null) {
			for (Field f : c.getDeclaredFields()) {
				JsonProperty jsonProperty = f.getAnnotation(JsonProperty.class);
				map.put(jsonProperty.defaultValue(), f);
			}
			c = c.getSuperclass();
		}
		return map;
	}
	
}
