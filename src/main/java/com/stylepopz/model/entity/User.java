package com.stylepopz.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

	public User() {
		super();
	}
	
	public User(String id, String access_token, String socialProfile, String socialProfileId, String code){
		super();
		this.id = id;
		this.access_token = access_token;
		this.socialProfile = socialProfile;
		this.socialProfileId = socialProfileId;
		this.code = code;
	}
	
	// this id is the account id returned by singly
	@Id
	private String id;
	private String access_token;
	private String socialProfile;
	private String socialProfileId;
	private String code;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getSocialProfile() {
		return socialProfile;
	}
	public void setSocialProfile(String socialProfile) {
		this.socialProfile = socialProfile;
	}
	public String getSocialProfileId() {
		return socialProfileId;
	}
	public void setSocialProfileId(String socialProfileId) {
		this.socialProfileId = socialProfileId;
	}
}
