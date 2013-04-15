package com.stylepopz.dao;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.stylepopz.common.exception.ApplicationException;
import com.stylepopz.model.DataObject;
import com.stylepopz.model.Preferences;
import com.stylepopz.model.User;

@Repository
public class SPopzDAO {

	private static final Logger logger = LoggerFactory.getLogger(SPopzDAO.class);

	@Autowired
	MongoTemplate mongoTemplate;
	MongoOperations mongoOperation = null;

	@PostConstruct
	public void init(){
		mongoOperation = (MongoOperations) mongoTemplate;
	}

	public void insertData(DataObject obj){
		try{
			if(!mongoOperation.collectionExists(obj.getClass()))
				mongoOperation.createCollection(obj.getClass());
			mongoOperation.save(obj);
		}catch(DataAccessResourceFailureException ex){
			throw new ApplicationException(ex.getLocalizedMessage());
		}
	}

	/**
	 * get access_token of a given ProfileId
	 * @param profileId
	 * @return
	 */
	public String getAccessToken(String profileId) {
		try{
			if(mongoOperation.collectionExists(User.class)){
				DBCursor dbCursor = mongoOperation.getCollection(User.class.getSimpleName().toLowerCase()).find(new BasicDBObject("profiles.facebook", profileId));
				try {
					while (dbCursor.hasNext()) {
						DBObject cur = dbCursor.next();
						logger.info("cur="+cur+"; "+cur.get("access_token"));
						return (String)cur.get("access_token");
					}
				} finally {
					dbCursor.close();
				}
			}
		}catch(DataAccessResourceFailureException ex){
			throw new ApplicationException(ex.getLocalizedMessage());
		}
		return "";
	}

	public boolean isPrefSet(String account) {
		long count = mongoOperation.getCollection(Preferences.class.getName()).count(new BasicDBObject("_id", account));
		logger.info("pref count="+count);
		return count > 0;
	}

	public void insertJsonNode(JsonNode jsonNode, String collectionName) {
		try{
			if(!mongoOperation.collectionExists(collectionName))
				mongoOperation.createCollection(collectionName);
			mongoOperation.save(jsonNode, collectionName);
		}catch(DataAccessResourceFailureException ex){
			throw new ApplicationException(ex.getLocalizedMessage());
		}
	}
}
