package com.stylepopz.model;

import java.util.HashMap;
import java.util.Map;

public class User implements DataObject{

	// this id is the account id returned by singly
	String id;
	String access_token;
	Map<String, String> profiles = new HashMap<String, String>();
	String code;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String, String> getProfiles() {
		return profiles;
	}
	public void setProfiles(Map<String, String> profiles) {
		this.profiles = profiles;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
