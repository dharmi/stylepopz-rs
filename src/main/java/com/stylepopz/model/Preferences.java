package com.stylepopz.model;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="preferences")
public class Preferences {

	// this id is the account (user.id)
	String id;
	Map<String, String> size;
	List<String> colors;
	List<String> print;
	List<String> luxury_brands;
	List<String> hiStreet_brands;
	List<String> fast_fashion_brands;
	List<String> indie_designers;
	List<String> blogger_preferences;
	
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
	public List<String> getColors() {
		return colors;
	}
	public void setColors(List<String> colors) {
		this.colors = colors;
	}
	public List<String> getPrint() {
		return print;
	}
	public void setPrint(List<String> print) {
		this.print = print;
	}
	public List<String> getLuxury_brands() {
		return luxury_brands;
	}
	public void setLuxury_brands(List<String> luxury_brands) {
		this.luxury_brands = luxury_brands;
	}
	public List<String> getHiStreet_brands() {
		return hiStreet_brands;
	}
	public void setHiStreet_brands(List<String> hiStreet_brands) {
		this.hiStreet_brands = hiStreet_brands;
	}
	public List<String> getFast_fashion_brands() {
		return fast_fashion_brands;
	}
	public void setFast_fashion_brands(List<String> fast_fashion_brands) {
		this.fast_fashion_brands = fast_fashion_brands;
	}
	public List<String> getIndie_designers() {
		return indie_designers;
	}
	public void setIndie_designers(List<String> indie_designers) {
		this.indie_designers = indie_designers;
	}
	public List<String> getBlogger_preferences() {
		return blogger_preferences;
	}
	public void setBlogger_preferences(List<String> blogger_preferences) {
		this.blogger_preferences = blogger_preferences;
	}
	
}
