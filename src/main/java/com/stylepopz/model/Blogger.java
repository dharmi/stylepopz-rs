package com.stylepopz.model;


/*
 * "rank": 1, 
    "id": 1, 
    "blog": "karinala", 
    "imageUrl": "img/karinala.jpg", 
    "name": "Design by Chona Pike", 
    "snippet": "ICU Fashion Show - San Francisco HAS FASHION 2013- http://www.karinala.com/apps/blog"
 */
public class Blogger{

	// this id is the account (user.id)
	String id;
	String rank;
	String blog;
	String imageUrl;
	String name;
	String snippet;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getBlog() {
		return blog;
	}
	public void setBlog(String blog) {
		this.blog = blog;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	
}
