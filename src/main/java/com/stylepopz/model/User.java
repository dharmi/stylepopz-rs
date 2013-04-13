package com.stylepopz.model;

import java.util.HashMap;
import java.util.Map;

public class User implements DataObject{

	// this id is the account id returned by singly
	String id;
	String accessToken;
	Map<String, String> profiles = new HashMap<String, String>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String, String> getProfiles() {
		return profiles;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public void setProfiles(Map<String, String> profiles) {
		this.profiles = profiles;
	}
	
}
