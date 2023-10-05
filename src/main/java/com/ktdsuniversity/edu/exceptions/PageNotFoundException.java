package com.ktdsuniversity.edu.exceptions;

public class PageNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -487988232936340988L;

	public PageNotFoundException(String message) {
		super(message);
	}
	
}
