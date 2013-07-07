package com.stylepopz.model;

import java.util.ArrayList;
import java.util.List;

/*
 * "rank": 1, 
    "id": 1, 
    "blog": "karinala", 
    "imageUrl": "img/karinala.jpg", 
    "name": "Design by Chona Pike", 
    "snippet": "ICU Fashion Show - San Francisco HAS FASHION 2013- http://www.karinala.com/apps/blog"
 */
public class BloggerList implements DataObject {
	List<Blogger> bloggerList = new ArrayList<Blogger>();

	public List<Blogger> getBloggerList() {
		return bloggerList;
	}

	public void setBlogger(Blogger blogger) {
		bloggerList.add(blogger);
	}
}

