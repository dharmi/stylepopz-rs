package com.stylepopz.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="preferences")
public class Preferences implements DataObject {

	// this id is the account (user.id)
	String id;
	List<KeyValuePair> size;
	List<KeyValuePair> colors;
	List<KeyValuePair> print;
	List<KeyValuePair> luxury_brands;
	List<KeyValuePair> hi_street_brands;
	List<KeyValuePair> fast_fashion_brands;
	List<KeyValuePair> indie_designers;
	List<KeyValuePair> blogger_preferences;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<KeyValuePair> getSize() {
		return size;
	}
	public void setSize(List<KeyValuePair> size) {
		this.size = size;
	}
	public List<KeyValuePair> getColors() {
		return colors;
	}
	public void setColors(List<KeyValuePair> colors) {
		this.colors = colors;
	}
	public List<KeyValuePair> getPrint() {
		return print;
	}
	public void setPrint(List<KeyValuePair> print) {
		this.print = print;
	}
	public List<KeyValuePair> getLuxury_brands() {
		return luxury_brands;
	}
	public void setLuxury_brands(List<KeyValuePair> luxury_brands) {
		this.luxury_brands = luxury_brands;
	}

	public List<KeyValuePair> getHi_street_brands() {
		return hi_street_brands;
	}
	public void setHi_street_brands(List<KeyValuePair> hi_street_brands) {
		this.hi_street_brands = hi_street_brands;
	}
	public List<KeyValuePair> getFast_fashion_brands() {
		return fast_fashion_brands;
	}
	public void setFast_fashion_brands(List<KeyValuePair> fast_fashion_brands) {
		this.fast_fashion_brands = fast_fashion_brands;
	}
	public List<KeyValuePair> getIndie_designers() {
		return indie_designers;
	}
	public void setIndie_designers(List<KeyValuePair> indie_designers) {
		this.indie_designers = indie_designers;
	}
	public List<KeyValuePair> getBlogger_preferences() {
		return blogger_preferences;
	}
	public void setBlogger_preferences(List<KeyValuePair> blogger_preferences) {
		this.blogger_preferences = blogger_preferences;
	}
}
