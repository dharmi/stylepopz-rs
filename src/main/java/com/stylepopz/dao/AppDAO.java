package com.stylepopz.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.stylepopz.common.exception.ApplicationException;
import com.stylepopz.model.Preferences;
import com.stylepopz.model.User;

public class AppDAO {

	private static final Logger logger = LoggerFactory.getLogger(AppDAO.class);

	MongoClient client = null;
	DB database = null;

	public AppDAO(String serverName, int portNo, String dbName){
		try{
			logger.debug("creating AppDAO");
			client = new MongoClient(new ServerAddress(serverName, portNo));
			database = client.getDB(dbName);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new ApplicationException("DB unavailable!!");
		}
	}

	public void insertUser(User user){

		BasicDBObject userSets = new BasicDBObject(); 
		BasicDBObject fieldSets = new BasicDBObject(); 
		fieldSets.put("profiles", user.getProfiles()); 
		fieldSets.put("accesstoken", user.getAccessToken()); 
		userSets.put( "$set", fieldSets); 


		database.getCollection("users").update(new BasicDBObject("_id", user.getId()), userSets, true, false);
	}

	public void insertPreferences(Preferences pref){
		database.getCollection("preferences").update(new BasicDBObject("_id", pref.getId()), 
				new BasicDBObject("$set", new BasicDBObject("size", pref.getSize())), true, false);
		logger.info("preferences inserted");
	}

	public boolean isExistingUser(String account) {
		long count = database.getCollection("users").count(new BasicDBObject("_id", account));
		logger.info("user count="+count);
		return count > 0;
	}

	public boolean isPrefSet(String account) {
		long count = database.getCollection("preferences").count(new BasicDBObject("_id", account));
		logger.info("pref count="+count);
		return count > 0;
	}

	public String getAccessToken(String profileId) {
		DBCursor dbCursor = database.getCollection("users").find(new BasicDBObject("profiles.facebook", profileId));
		String access_token = "";
		try {
			while (dbCursor.hasNext()) {
				DBObject cur = dbCursor.next();
				logger.info("cur="+cur+"; "+cur.get("access_token"));
				access_token = cur.toString();
			}
		} finally {
			dbCursor.close();
		}
		return "";
	}
}
