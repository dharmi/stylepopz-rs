package com.stylepopz.common.listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class AppListener implements ApplicationContextInitializer{

	private static int totalActiveSessions;

	public static int getTotalActiveSession(){
		return totalActiveSessions;
	}

	@Override
	public void initialize(ConfigurableApplicationContext arg0) {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		totalActiveSessions++;
		System.out.println("****************sessionCreated - add one session into counter");	
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		totalActiveSessions--;
		System.out.println("*****************sessionDestroyed - deduct one session from counter");	
	}	*/
}
