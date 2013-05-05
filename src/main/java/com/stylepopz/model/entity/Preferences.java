package com.stylepopz.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Preferences {

	public Preferences() {
		super();
	}
	
	public Preferences(String id, String prefJson) {
		super();
		this.id = id;
		this.prefJson = prefJson;
	}

	@Id
	private String id;
	
	@Column(name = "prefJson", nullable = false, length = 2000)
	private String prefJson;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPrefJson() {
		return prefJson;
	}
	public void setPrefJson(String prefJson) {
		this.prefJson = prefJson;
	}

}
