package com.stylepopz.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.stylepopz.model.DataObject;

@Entity
public class Profile implements DataObject{

	@Id
	String serviceId;
	String serviceName;
	
	public Profile(String serviceId, String serviceName) {
		this.serviceId = serviceId;
		this.serviceName = serviceName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
}
