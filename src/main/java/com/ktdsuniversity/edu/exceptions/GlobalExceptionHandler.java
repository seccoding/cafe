package com.ktdsuniversity.edu.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 애플리케이션에서 발생하는 모든 예외들을 여기 클래스에서 처리한다.
 * 동작하는 방식은 @Controller와 매우 유사하다.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	/**
	 * PageNotFoundException이 발생하면
	 * viewPageNotFoundErrorPage 메소드가 처리한다.
	 */
//	@ExceptionHandler(PageNotFoundException.class)
	@ExceptionHandler(PageNotFoundException.class)
	public ModelAndView viewPageNotFoundErrorPage(
			           PageNotFoundException exception) {
		
		logger.error(exception.getMessage(), exception);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error/404");
		modelAndView.addObject("message", exception.getMessage());
		return modelAndView;
	}
	
	@ExceptionHandler({ FileNotExistsException.class, 
		                MakeXlsxFileException.class,
		                RuntimeException.class})
	public ModelAndView viewFileErrorPage(RuntimeException exception) {
		logger.error(exception.getMessage(), exception);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error/500");
		modelAndView.addObject("message", exception.getMessage());
		return modelAndView;
	}
	
	@ExceptionHandler(AlreadyUseException.class)
	public ModelAndView viewMemberRegistErrorPage(AlreadyUseException exception) {
		
		logger.error(exception.getMessage(), exception);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("member/memberregist");
		modelAndView.addObject("memberVO", exception.getMemberVO());
		modelAndView.addObject("message", exception.getMessage());
		return modelAndView;
	}
	
	@ExceptionHandler(UserIdentifyNotMatchException.class)
	public ModelAndView viewUserIdentifyNotMatchErrorPage(
						UserIdentifyNotMatchException exception) {
		logger.error(exception.getMessage(), exception);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("member/memberlogin");
		modelAndView.addObject("memberVO", exception.getMemberVO());
		modelAndView.addObject("message", exception.getMessage());
		return modelAndView;
	}
	
}
