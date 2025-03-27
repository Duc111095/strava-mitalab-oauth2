package com.ducnh.oauth2_server.mapper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ducnh.oauth2_server.model.AthleteUser;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomAthleteDeserializer  extends StdDeserializer<AthleteUser>{
	
	private static final long serialVersionUID = 1L;
	
	public static final Logger logger = LoggerFactory.getLogger(CustomAthleteDeserializer.class);
	
	public CustomAthleteDeserializer() {
		this(null);
	}
	
	public CustomAthleteDeserializer(Class<?> vc) {
		super(vc);
	}
	
	
	@Override
	public AthleteUser deserialize(JsonParser parser, DeserializationContext deserializer) {
		AthleteUser user = new AthleteUser();
		ObjectCodec codec = parser.getCodec();
		try {
			TreeNode treeNode = codec.readTree(parser);
			logger.info("TreeNode :" + treeNode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return user;
	}
}
