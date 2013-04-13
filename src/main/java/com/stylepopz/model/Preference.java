package com.stylepopz.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Preference {
  private String id;
  
  public Preference(){
    
  }
  public Preference (String id){
    this.id = id;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  
} 