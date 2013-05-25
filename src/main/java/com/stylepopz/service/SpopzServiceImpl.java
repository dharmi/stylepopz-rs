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
		try{
			existingPref = (new Gson()).fromJson(existingPrefAsJSON.getPrefJson(), Preference.class);
		}catch(Exception ex){
			logger.info(ex.toString());
		}

		if(pref != null && existingPrefAsJSON == null){
			Preference preference = intermediateToPref(pref, existingPref);

			em.persist(new PreferenceAsJson(preference.getId(), new Gson().toJson(preference)));
			logger.info("done");
		}else{	// update

			Preference preference = intermediateToPref(pref, existingPref);
			
			logger.info("intermediate: "+new JSONSerializer().deepSerialize(pref));
			logger.info("existing: "+new JSONSerializer().deepSerialize(existingPrefAsJSON));
			logger.info("new: "+new JSONSerializer().deepSerialize(prefToBePersistedAsJSON));

			em.merge(new PreferenceAsJson(preference.getId(), new Gson().toJson(preference)));
			//em.merge(prefToBePersistedAsJSON);
			logger.info("done");
		}
	}

	private Preference intermediateToPref(IntermediatePreference pref, Preference existingPref) {

		if(existingPref != null){
			// existing preference, so update
			Sizes sizes = existingPref.getSizes();
			
			List<String> nShirts = pref.getShirts();
			Sizes oSizes = existingPref.getSizes();
			List<Map<String, String>> oShirts = oSizes.getShirts();
			Iterator<Map<String, String>> oIterator = oShirts.iterator();
			
			while(oIterator.hasNext()){
				Map<String, String> oMap = oIterator.next();
				if(nShirts.contains(oMap.get("shirt"))){
					logger.info(oMap.get("shirt")+" present with selected="+oMap.get("selected"));
					String selected = oMap.get("selected")+"";
					if(selected.equals("N")) 
						oMap.put("selected", "Y");
					else
						oMap.put("selected", "N");
					//(oShirts.get(oShirts.indexOf("shirt"))).put("shirt", "");
					
					logger.info("after : "+oMap.get("shirt")+" present with selected="+oMap.get("selected"));
					sizes.setShirts(oShirts);
				}else{
					logger.info(oMap.get("shirt")+" not present");
				}
			}
			//existingPref.setSizes(sizes);
			
			return existingPref;
		}else{
			// its a new preference, create one.
			Preference newPref = new Preference();
			newPref.setId(pref.getId());

			Sizes oSizes = new Sizes();

			// shirts
			List<Map<String, String>> oLst = new ArrayList<Map<String, String>>();
			List<String> iCategory = pref.getShirts();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "N"); 
					oMap.put("url", "");
					oMap.put("shirt", item);
					oLst.add(oMap);
				}
			}
			oSizes.setShirts(oLst);

			// pants
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getPants();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "N"); 
					oMap.put("url", "");
					oMap.put("pant", item);
					oLst.add(oMap);
				}
			}
			oSizes.setPants(oLst);

			// shoes
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getShoes();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "N"); 
					oMap.put("url", "");
					oMap.put("shoe", item);
					oLst.add(oMap);
				}
			}
			oSizes.setShoes(oLst);

			// set sizes
			newPref.setSizes(oSizes);

			// colors
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getColors();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "N"); 
					oMap.put("url", "");
					oMap.put("color", item);
					oLst.add(oMap);
				}
			}
			newPref.setColors(oLst);

			// prints
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getPrints();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "N"); 
					oMap.put("url", "");
					oMap.put("print", item);
					oLst.add(oMap);
				}
			}
			newPref.setPrints(oLst);

			// luxuryBrands
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getLuxurybrands();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "N"); 
					oMap.put("url", "");
					oMap.put("luxbrand", item);
					oLst.add(oMap);
				}
			}
			newPref.setLuxuryBrands(oLst);

			// hiStreetBrands
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getHiStreetBrands();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "N"); 
					oMap.put("url", "");
					oMap.put("hibrand", item);
					oLst.add(oMap);
				}
			}
			newPref.setHiStreetBrands(oLst);

			// fastFashionBrands
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getFastFashionBrands();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "N"); 
					oMap.put("url", "");
					oMap.put("fastfash", item);
					oLst.add(oMap);
				}
			}
			newPref.setFastFashionBrands(oLst);

			// indieDesigners
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getIndieDesigners();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "N"); 
					oMap.put("url", "");
					oMap.put("indie", item);
					oLst.add(oMap);
				}
			}
			newPref.setIndieDesigners(oLst);

			// bloggerPreferences
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getBloggerPreferences();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "N"); 
					oMap.put("url", "");
					oMap.put("blogger", item);
					oLst.add(oMap);
				}
			}
			newPref.setBloggerPreferences(oLst);

			return newPref;
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
		return null;
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
