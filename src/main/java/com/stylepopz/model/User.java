package com.stylepopz.model;

import java.util.HashMap;
import java.util.Map;

public class User implements DataObject{

	String id;
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
	public void setProfiles(Map<String, String> profiles) {
		this.profiles = profiles;
	}

}
