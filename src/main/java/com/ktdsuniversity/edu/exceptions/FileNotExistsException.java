package com.ktdsuniversity.edu.exceptions;

public class FileNotExistsException extends RuntimeException {

	private static final long serialVersionUID = 3592331898318964334L;

	public FileNotExistsException(String message) {
		super(message);
	}
	
}
