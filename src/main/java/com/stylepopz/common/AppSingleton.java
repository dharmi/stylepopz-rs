package com.stylepopz.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.singly.client.InMemorySinglyAccountStorage;
import com.singly.client.SinglyAccountStorage;
import com.singly.client.SinglyService;
import com.singly.client.SinglyServiceImpl;
import com.singly.util.HttpClientService;
import com.singly.util.HttpClientServiceImpl;
import com.stylepopz.dao.AppDAO;

public enum AppSingleton {
	INSTANCE;    
	
	private static final Logger logger = LoggerFactory.getLogger(AppSingleton.class); 

	private SinglyAccountStorage accountStorage;
	private SinglyService singlyService;
	private HttpClientService singlyHttpClient;
	private AppDAO dao;

	public AppDAO getDao() {
		if(dao == null){
			logger.debug("AuthenticationResource :: creating dao");
			this.dao = new AppDAO("localhost", 27017, "stylepopz", "users");
		}
		return dao;
	}
	public void setDao(AppDAO dao) {
		this.dao = dao;
	}
	public SinglyAccountStorage getAccountStorage() {
		if(accountStorage == null){
			logger.debug("AuthenticationResource :: creating accountStorage");
			this.accountStorage = new InMemorySinglyAccountStorage();
		}
		return this.accountStorage;
	}
	public void setAccountStorage(SinglyAccountStorage accountStorage) {
		this.accountStorage = accountStorage;
	}
	public SinglyService getSinglyService() {
		//if(singlyService == null){
			logger.debug("AuthenticationResource :: creating singlyService");
			this.singlyService = new SinglyServiceImpl("86fbb379774f3410c039d1b648026d97", "53a26f2f46f69ce37ab13846e6b87199", getAccountStorage(), getSinglyHttpClient());
		///}
		return this.singlyService;
	}
	public void setSinglyService(SinglyService singlyService) {
		this.singlyService = singlyService;
	}
	public HttpClientService getSinglyHttpClient() {
		//if(singlyHttpClient == null){
			logger.debug("AuthenticationResource :: creating singlyHttpClient");
			this.singlyHttpClient = new HttpClientServiceImpl();
		//}
		return this.singlyHttpClient;
	}
	public void setSinglyHttpClient(HttpClientService singlyHttpClient) {
		this.singlyHttpClient = singlyHttpClient;
	}

}
