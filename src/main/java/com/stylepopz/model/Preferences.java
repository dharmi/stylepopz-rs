package com.stylepopz.model;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="preferences")
public class Preferences implements DataObject {

	// this id is the account (user.id)
	String id;
	Map<String, String> size;
	Map<String, String> colors;
	Map<String, String> print;
	Map<String, String> luxury_brands;
	Map<String, String> hi_street_brands;
	Map<String, String> fast_fashion_brands;
	Map<String, String> indie_designers;
	Map<String, String> blogger_preferences;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String, String> getSize() {
		return size;
	}
	public void setSize(Map<String, String> size) {
		this.size = size;
	}
	public Map<String, String> getColors() {
		return colors;
	}
	public void setColors(Map<String, String> colors) {
		this.colors = colors;
	}
	public Map<String, String> getPrint() {
		return print;
	}
	public void setPrint(Map<String, String> print) {
		this.print = print;
	}
	public Map<String, String> getLuxury_brands() {
		return luxury_brands;
	}
	public void setLuxury_brands(Map<String, String> luxury_brands) {
		this.luxury_brands = luxury_brands;
	}

	public Map<String, String> getHi_street_brands() {
		return hi_street_brands;
	}
	public void setHi_street_brands(Map<String, String> hi_street_brands) {
		this.hi_street_brands = hi_street_brands;
	}
	public Map<String, String> getFast_fashion_brands() {
		return fast_fashion_brands;
	}
	public void setFast_fashion_brands(Map<String, String> fast_fashion_brands) {
		this.fast_fashion_brands = fast_fashion_brands;
	}
	public Map<String, String> getIndie_designers() {
		return indie_designers;
	}
	public void setIndie_designers(Map<String, String> indie_designers) {
		this.indie_designers = indie_designers;
	}
	public Map<String, String> getBlogger_preferences() {
		return blogger_preferences;
	}
	public void setBlogger_preferences(Map<String, String> blogger_preferences) {
		this.blogger_preferences = blogger_preferences;
	}
	
}
