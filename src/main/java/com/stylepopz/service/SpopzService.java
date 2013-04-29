package com.stylepopz.service;


import java.util.List;

import com.google.gson.JsonObject;
import com.stylepopz.model.Profile;
import com.stylepopz.model.User;

public interface SpopzService {
    
    public void addUser(User user);
    public void removeUser(Integer id);
    public List<User> listUsers();
    public List<Profile> listProfiles();
	public String getAccessToken(String profileId, String serviceName);
	public boolean isPrefSet(String account);
	public void insertProfile(JsonObject jsonNode, String collectionName);
	public void addService(Profile services);
}
