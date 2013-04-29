package com.stylepopz.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.stylepopz.model.Profile;
import com.stylepopz.model.User;

@Service
public class SpopzServiceImpl implements SpopzService {
	
	private static final Logger logger = LoggerFactory.getLogger(SpopzServiceImpl.class);

    @PersistenceContext
    EntityManager em;
        
    @Transactional
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
