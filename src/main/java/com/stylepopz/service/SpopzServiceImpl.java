package com.stylepopz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.stylepopz.common.AppUtils;
import com.stylepopz.model.IntermediatePreference;
import com.stylepopz.model.Preference;
import com.stylepopz.model.Sizes;
import com.stylepopz.model.entity.PreferenceAsJson;
import com.stylepopz.model.entity.Profile;
import com.stylepopz.model.entity.User;

import flexjson.JSONSerializer;

@Service
public class SpopzServiceImpl implements SpopzService {

	private static final Logger logger = LoggerFactory.getLogger(SpopzServiceImpl.class);

	@PersistenceContext
	EntityManager em;

	public void addUser(User user) {

		User _user = null;
		if(user != null){ 
			try{
				_user = em.find(User.class, user.getId());
			}catch(PersistenceException ex){
				ex.printStackTrace();
			}
		}
		if(_user == null) // insert
		em.persist(user);
		else{	// update
			em.merge(user);
		}
	}

	@Transactional
	public void upsertPreference(IntermediatePreference pref, String jsonString) {

		logger.info(pref.getId());

		PreferenceAsJson prefToBePersistedAsJSON = new PreferenceAsJson(pref.getId(), jsonString);
		PreferenceAsJson existingPrefAsJSON = em.find(PreferenceAsJson.class, pref.getId());
		Preference existingPref = null;
		/*try{
			if(existingPrefAsJSON == null){
				// new user
				
			}else{
				existingPref = (new Gson()).fromJson(existingPrefAsJSON.getPrefJson(), Preference.class);
			}
		}catch(Exception ex){
			logger.info(ex.toString());
		}*/

		if(pref != null && existingPrefAsJSON == null){
			
			// new user
			Preference preference = AppUtils.intermediateToPref(pref, existingPref);
			em.persist(new PreferenceAsJson(preference.getId(), new Gson().toJson(preference)));
			logger.info("done");
		}else{	
			// update : existing user
			
			existingPref = (new Gson()).fromJson(existingPrefAsJSON.getPrefJson(), Preference.class);
			Preference preference = AppUtils.intermediateToPref(pref, existingPref);
			
			/*logger.info("intermediate: "+new JSONSerializer().deepSerialize(pref));
			logger.info("existing: "+new JSONSerializer().deepSerialize(existingPrefAsJSON));
			logger.info("new: "+new JSONSerializer().deepSerialize(prefToBePersistedAsJSON));*/

			em.merge(new PreferenceAsJson(preference.getId(), new Gson().toJson(preference)));
			//em.merge(prefToBePersistedAsJSON);
			logger.info("done");
		}
	}

	@Transactional
	public void upsertPreferencehack(Preference pref, String jsonString) {

		PreferenceAsJson prefEntity = new PreferenceAsJson(pref.getId(), jsonString);

		if(pref != null && em.find(PreferenceAsJson.class, pref.getId()) == null){
			em.persist(prefEntity);
		}else{	// update
			em.merge(prefEntity);
		}
	}

	@Transactional
	public List<User> listUsers() {
		CriteriaQuery<User> c = em.getCriteriaBuilder().createQuery(User.class);
		c.from(User.class);
		return em.createQuery(c).getResultList();
	}

	@Transactional
	public List<Profile> listProfiles() {
		CriteriaQuery<Profile> c = em.getCriteriaBuilder().createQuery(Profile.class);
		c.from(Profile.class);
		return em.createQuery(c).getResultList();
	}

	@Transactional
	public List<PreferenceAsJson> listPreferences() {
		CriteriaQuery<PreferenceAsJson> c = em.getCriteriaBuilder().createQuery(PreferenceAsJson.class);
		c.from(PreferenceAsJson.class);
		return em.createQuery(c).getResultList();
	}

	@Transactional
	public PreferenceAsJson getPreference(String profileId) {
		CriteriaQuery<PreferenceAsJson> c = em.getCriteriaBuilder().createQuery(PreferenceAsJson.class);
		Root<PreferenceAsJson> pref = c.from(PreferenceAsJson.class);
		c.where(pref.get("id").in(profileId));


		List<PreferenceAsJson> prefList = em.createQuery(c).getResultList();
		if ( prefList != null && prefList.size() > 0 )
			return prefList.get(0);
		//return returnEmptyPreferenceAsJson(profileId);
		return null;
	}

	private PreferenceAsJson returnEmptyPreferenceAsJson(String profileId) {
		String preferenceJson = "{\"id\":\""+profileId+"\",\"sex\":\"M\",\"shirts\":[\"s\",\"m\",\"l\"],\"pants\":[\"s\",\"m\",\"l\"],\"shoes\":[\"7\",\"8\",\"9\"],\"colors\":[\"skyblue\",\"purple\"],\"prints\":[\"flowery\",\"floral\"],\"luxurybrands\":[\"Armani\",\"Gucci\"],\"hiStreetBrands\":[\"Jimmy Choo\",\"Chetlham\"],\"fastFashionBrands\":[\"Foreever 21\",\"ZARA\"],\"indieDesigners\":[\"sonas jeans\",\"sonas denim\"]}";
		return new PreferenceAsJson(profileId, preferenceJson);
	}

	@Transactional
	public void removeUser(Integer id) {
		User user = em.find(User.class, id);
		if (null != user) {
			em.remove(user);
		}
	}

	@Override
	public String getAccessToken(String profileId, String serviceName) {
		CriteriaQuery<User> cq = em.getCriteriaBuilder().createQuery(User.class);
		Root<User> user = cq.from(User.class);
		cq.where(user.get("socialProfileId").in(profileId));
		List<User> userList = em.createQuery(cq).getResultList();
		logger.debug("userList size="+userList.size());
		return (userList!=null && userList.size()>0) ? userList.get(0).getAccess_token() : null;
	}

	@Override
	public boolean isPrefSet(String account) {
		return false;
	}

	@Override
	public void insertProfile(JsonObject jsonNode, String collectionName) {
	}

	@Override
	public void addService(Profile services) {
		em.persist(services);
	}

}
