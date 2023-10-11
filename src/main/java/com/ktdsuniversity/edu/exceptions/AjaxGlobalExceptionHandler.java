package com.ktdsuniversity.edu.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AjaxGlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(AjaxGlobalExceptionHandler.class);
	
	@ExceptionHandler({AjaxPageNotFoundException.class, RuntimeException.class})
	public Map<String, Object> ajaxPageNotFoundExceptionHandler(
			RuntimeException exception) {
		
		logger.error(exception.getMessage(), exception);
		
		Map<String, Object> result = new HashMap<>();
		result.put("message", exception.getMessage());
		return result;
	}
}
