package com.stylepopz.model;

import java.util.List;
import java.util.Map;

public class Sizes {

	List<Map<String, String>> shirts;
	List<Map<String, String>> pants;
	List<Map<String, String>> shoes;
	public List<Map<String, String>> getShirts() {
		return shirts;
	}
	public void setShirts(List<Map<String, String>> shirts) {
		this.shirts = shirts;
	}
	public List<Map<String, String>> getPants() {
		return pants;
	}
	public void setPants(List<Map<String, String>> pants) {
		this.pants = pants;
	}
	public List<Map<String, String>> getShoes() {
		return shoes;
	}
	public void setShoes(List<Map<String, String>> shoes) {
		this.shoes = shoes;
	}

}