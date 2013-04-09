package com.stylepopz.common.exception;

public class ApplicationException extends RuntimeException{
	 
	private static final long serialVersionUID = 7961139226238620018L;
	
	private String customMsg;
 
	public ApplicationException(String customMsg) {
		this.customMsg = customMsg;
	}

	public String getCustomMsg() {
		return customMsg;
	}

	public void setCustomMsg(String customMsg) {
		this.customMsg = customMsg;
	}
	
}
