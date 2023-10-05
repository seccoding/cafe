package com.ktdsuniversity.edu.beans;

import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ktdsuniversity.edu.member.vo.MemberVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CheckSessionInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(CheckSessionInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("Controller가 실행되기 전 입니다.");
		logger.info(handler.getClass().getName() + "가 실행됩니다.");
		logger.warn(request.getRequestURI());
		
		// 브라우저의 세션을 가져온다.
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("_LOGIN_USER_");
		// 브라우저 세션이 비어있다면 (로그인을 안했다면)
		if (memberVO == null) {
			// 요청 방식을 얻어온다. (GET, POST, PUT, DELETE)
			String method = request.getMethod().toLowerCase();
			// GET http method일 때만 원래 가려했던 URL(next)을 보내준다.
			// POST http method일 때는 무시한다.
			if (method.equals("get")) {
				// 원래 가려했던 URL 정보 가져오기.
				String requestURI = request.getRequestURI();
				String queryString = getQueryString(request);
				// 로그인 후 URL을 login.jsp의 모델로 보내준다.
				request.setAttribute("next", requestURI + queryString);
			}
			// 로그인 화면을 보여주기 위한 RequestDispatcher 객체를 만든다.
			RequestDispatcher rd = 
					request.getRequestDispatcher("/WEB-INF/views/member/memberlogin.jsp");
			// 로그인 화면으로 이동시킨다.
			// URL은 바뀌지 않는다.
			rd.forward(request, response);
			// 컨트롤러 실행을 하지 않는다.
			return false;
			// 로그인 페이지로 이동을 시켜야하고
			// 로그인을 완료하면 원래 가려했던 URL로 이동을 시켜주어야 한다.
		}
		
		// 컨트롤러 실행을 계속한다.
		return true;
	}
	
	private String getQueryString(HttpServletRequest request) {
		String queryString = "";
		
		Enumeration<String> parameterNames = request.getParameterNames();
		
		String parameterName = null;
		while (parameterNames.hasMoreElements()) {
			parameterName = parameterNames.nextElement();
			
			if (queryString.equals("")) {
				queryString = "?";
			}
			else {
				queryString += "&";
			}
			queryString += parameterName + "=" 
			             + request.getParameter(parameterName);
		}
		
		return queryString;
	}
	
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		System.out.println("Controller 가 실행 된 후 입니다.");
//		System.out.println(handler.getClass().getName() + "이 실행되었습니다.");
//		System.out.println(modelAndView + "를 반환했습니다.");
//		
//		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//	}
//	
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		System.out.println("View를 만들어 브라우저에게 반환하기 전 입니다.");
//		System.out.println(handler.getClass().getName() + "이 실행되었습니다.");
//		
//		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//	}
}
