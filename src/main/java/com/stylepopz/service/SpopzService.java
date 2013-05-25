package com.stylepopz.service;


import java.util.List;

import com.google.gson.JsonObject;
import com.stylepopz.model.IntermediatePreference;
import com.stylepopz.model.Preference;
import com.stylepopz.model.entity.PreferenceAsJson;
import com.stylepopz.model.entity.Profile;
import com.stylepopz.model.entity.User;

public interface SpopzService {
    
    public void addUser(User user);
    public void removeUser(Integer id);
    public List<User> listUsers();
    public List<Profile> listProfiles();
    public List<PreferenceAsJson> listPreferences();
    public PreferenceAsJson getPreference(String profileId);
	public String getAccessToken(String profileId, String serviceName);
	public boolean isPrefSet(String account);
	public void insertProfile(JsonObject jsonNode, String collectionName);
	public void addService(Profile services);
	public void upsertPreference(IntermediatePreference pref, String jsonString);
	public void upsertPreferencehack(Preference pref, String jsonString);
	
}
