package com.stylepopz.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.stylepopz.common.exception.ApplicationException;
import com.stylepopz.model.User;

public class AppDAO {

	MongoClient client = null;
	DB database = null;
	DBCollection collection = null;

	public AppDAO(String serverName, int portNo, String dbName, String dbCollection){
		try{
			client = new MongoClient(new ServerAddress(serverName, portNo));
			database = client.getDB(dbName);
			collection = database.getCollection(dbCollection);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new ApplicationException("DB unavailable!!");
		}
	}
	
	public void insertUser(User user){
		collection.update(new BasicDBObject("_id", user.getId()),
                new BasicDBObject("$set", new BasicDBObject("profiles", user.getProfiles())), true, false);
	}

}
