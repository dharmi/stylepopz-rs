package com.stylepopz.model;

import java.util.List;
import java.util.Map;

public class IntermediatePreference implements DataObject {

	// this id is the account (user.id)
	String id;
	String sex;
	List<String> shirts;
	List<String> pants;
	List<String> shoes;
	List<String> colors;
	List<String> prints;
	List<String> luxurybrands;
	List<String> hiStreetBrands;
	List<String> fastFashionBrands;
	List<String> indieDesigners;
	List<String> bloggerPreferences;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public List<String> getShirts() {
		return shirts;
	}
	public void setShirts(List<String> shirts) {
		this.shirts = shirts;
	}
	public List<String> getPants() {
		return pants;
	}
	public void setPants(List<String> pants) {
		this.pants = pants;
	}
	public List<String> getShoes() {
		return shoes;
	}
	public void setShoes(List<String> shoes) {
		this.shoes = shoes;
	}
	public List<String> getColors() {
		return colors;
	}
	public void setColors(List<String> colors) {
		this.colors = colors;
	}
	public List<String> getPrints() {
		return prints;
	}
	public void setPrints(List<String> prints) {
		this.prints = prints;
	}
	public List<String> getLuxurybrands() {
		return luxurybrands;
	}
	public void setLuxurybrands(List<String> luxurybrands) {
		this.luxurybrands = luxurybrands;
	}
	public List<String> getHiStreetBrands() {
		return hiStreetBrands;
	}
	public void setHiStreetBrands(List<String> hiStreetBrands) {
		this.hiStreetBrands = hiStreetBrands;
	}
	public List<String> getFastFashionBrands() {
		return fastFashionBrands;
	}
	public void setFastFashionBrands(List<String> fastFashionBrands) {
		this.fastFashionBrands = fastFashionBrands;
	}
	public List<String> getIndieDesigners() {
		return indieDesigners;
	}
	public void setIndieDesigners(List<String> indieDesigners) {
		this.indieDesigners = indieDesigners;
	}
	public List<String> getBloggerPreferences() {
		return bloggerPreferences;
	}
	public void setBloggerPreferences(List<String> bloggerPreferences) {
		this.bloggerPreferences = bloggerPreferences;
	}
	
}
