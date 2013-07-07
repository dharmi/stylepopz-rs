package com.stylepopz.model;

import java.util.Iterator;
import java.util.List;

public class Frontpage {
	private String id;
	private String tagName;
	private List<ChildNode> childNodes;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public List<ChildNode> getChildNodes() {
		return childNodes;
	}
	public void setChildNodes(List<ChildNode> childNodes) {
		this.childNodes = childNodes;
	}


	public class ChildNode{
		private String id;
		private String sp;
		private String fresh;
		private String sr;
		private String tagName;
		private String img;
		private String cluster;
		List<ChildNodeNode> childNodes;
		
		public String getTagByName(String tagName){
			Iterator<ChildNodeNode> iterator = childNodes.iterator();
			while(iterator.hasNext()){
				ChildNodeNode node = iterator.next();
				if(tagName.equalsIgnoreCase(node.getTagName()))
					return node.getChildNodes().get(0);
			}
			return "";
		}

		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getSp() {
			return sp;
		}
		public void setSp(String sp) {
			this.sp = sp;
		}
		public String getFresh() {
			return fresh;
		}
		public void setFresh(String fresh) {
			this.fresh = fresh;
		}
		public String getSr() {
			return sr;
		}
		public void setSr(String sr) {
			this.sr = sr;
		}
		public String getTagName() {
			return tagName;
		}
		public void setTagName(String tagName) {
			this.tagName = tagName;
		}
		public String getImg() {
			return img;
		}
		public void setImg(String img) {
			this.img = img;
		}
		public String getCluster() {
			return cluster;
		}
		public void setCluster(String cluster) {
			this.cluster = cluster;
		}
		public List<ChildNodeNode> getChildNodes() {
			return childNodes;
		}
		public void setChildNodes(List<ChildNodeNode> childNodes) {
			this.childNodes = childNodes;
		}

	}

	public class ChildNodeNode{
		String tagName;
		List<String> childNodes;

		public String getTagName() {
			return tagName;
		}
		public void setTagName(String tagName) {
			this.tagName = tagName;
		}
		public List<String> getChildNodes() {
			return childNodes;
		}
		public void setChildNodes(List<String> childNodes) {
			this.childNodes = childNodes;
		}
	}

}