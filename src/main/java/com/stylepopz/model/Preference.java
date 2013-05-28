package com.stylepopz.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Preference implements DataObject {

	// this id is the account (user.id)
	String id;
	String sex;
	Sizes sizes = new Sizes();
	List<Map<String, String>> colors = new ArrayList<Map<String, String>>();
	List<Map<String, String>> prints = new ArrayList<Map<String, String>>();
	List<Map<String, String>> luxuryBrands = new ArrayList<Map<String, String>>();
	List<Map<String, String>> hiStreetBrands = new ArrayList<Map<String, String>>();
	List<Map<String, String>> fastFashionBrands = new ArrayList<Map<String, String>>();
	List<Map<String, String>> indieDesigners = new ArrayList<Map<String, String>>();
	List<Map<String, String>> bloggerPreferences = new ArrayList<Map<String, String>>();
	
	public Preference(){
		Map<String, String> selected = new HashMap<String, String>();
		selected.put("selected", "N");
		List<Map<String, String>> map = new ArrayList<Map<String,String>>();
		map.add(selected);
		
		sizes.setShirts(map);
		sizes.setPants(map);
		sizes.setShoes(map);
		
		colors.add(selected);
		prints.add(selected);
		luxuryBrands.add(selected);
		hiStreetBrands.add(selected);
		fastFashionBrands.add(selected);
		indieDesigners.add(selected);
		bloggerPreferences.add(selected);
	}
	
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
	public List<Map<String, String>> getLuxuryBrands() {
		return luxuryBrands;
	}
	public void setLuxuryBrands(List<Map<String, String>> luxuryBrands) {
		this.luxuryBrands = luxuryBrands;
	}
	public List<Map<String, String>> getHiStreetBrands() {
		return hiStreetBrands;
	}
	public void setHiStreetBrands(List<Map<String, String>> hiStreetBrands) {
		this.hiStreetBrands = hiStreetBrands;
	}
	public List<Map<String, String>> getFastFashionBrands() {
		return fastFashionBrands;
	}
	public void setFastFashionBrands(List<Map<String, String>> fastFashionBrands) {
		this.fastFashionBrands = fastFashionBrands;
	}
	public List<Map<String, String>> getIndieDesigners() {
		return indieDesigners;
	}
	public void setIndieDesigners(List<Map<String, String>> indieDesigners) {
		this.indieDesigners = indieDesigners;
	}
	public List<Map<String, String>> getBloggerPreferences() {
		return bloggerPreferences;
	}
	public void setBloggerPreferences(List<Map<String, String>> bloggerPreferences) {
		this.bloggerPreferences = bloggerPreferences;
	}
	public Sizes getSizes() {
		return sizes;
	}
	public void setSizes(Sizes sizes) {
		this.sizes = sizes;
	}

}


