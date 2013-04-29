package com.stylepopz.dao;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonObject;
import com.stylepopz.model.DataObject;

@Repository
public class SPopzDAO {

	private static final Logger logger = LoggerFactory.getLogger(SPopzDAO.class);

	@PostConstruct
	public void init(){
	}

	public void insertData(DataObject obj){
		
	}

	/**
	 * get access_token of a given ProfileId
	 * @param profileId
	 * @return
	 */
	public String getAccessToken(String profileId, String serviceName) {
		return "TBD";
	}

	public boolean isPrefSet(String account) {
		long count = 0;
		logger.info("pref count="+count);
		return count > 0;
	}
	
	public void insertProfile(JsonObject jsonNode, String collectionName) {
		
	}
}
