package com.stylepopz.model;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="preferences")
public class Preference implements DataObject {

	// this id is the account (user.id)
	String id;
	List<Map<String, String>> size;
	List<Map<String, String>> colors;
	List<Map<String, String>> prints;
	List<Map<String, String>> luxurybrands;
	List<Map<String, String>> hi_street_brands;
	List<Map<String, String>> fast_fashion_brands;
	List<Map<String, String>> indie_designers;
	List<Map<String, String>> blogger_preferences;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Map<String, String>> getSize() {
		return size;
	}
	public void setSize(List<Map<String, String>> size) {
		this.size = size;
	}
	public List<Map<String, String>> getColors() {
		return colors;
	}
	public void setColors(List<Map<String, String>> colors) {
		this.colors = colors;
	}
	public List<Map<String, String>> getPrints() {
		return prints;
	}
	public void setPrints(List<Map<String, String>> prints) {
		this.prints = prints;
	}
	public List<Map<String, String>> getLuxurybrands() {
		return luxurybrands;
	}
	public void setLuxurybrands(List<Map<String, String>> luxury_brands) {
		this.luxurybrands = luxury_brands;
	}

	public List<Map<String, String>> getHi_street_brands() {
		return hi_street_brands;
	}
	public void setHi_street_brands(List<Map<String, String>> hi_street_brands) {
		this.hi_street_brands = hi_street_brands;
	}
	public List<Map<String, String>> getFast_fashion_brands() {
		return fast_fashion_brands;
	}
	public void setFast_fashion_brands(List<Map<String, String>> fast_fashion_brands) {
		this.fast_fashion_brands = fast_fashion_brands;
	}
	public List<Map<String, String>> getIndie_designers() {
		return indie_designers;
	}
	public void setIndie_designers(List<Map<String, String>> indie_designers) {
		this.indie_designers = indie_designers;
	}
	public List<Map<String, String>> getBlogger_preferences() {
		return blogger_preferences;
	}
	public void setBlogger_preferences(List<Map<String, String>> blogger_preferences) {
		this.blogger_preferences = blogger_preferences;
	}

}
