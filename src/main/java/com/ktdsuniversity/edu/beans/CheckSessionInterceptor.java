package com.ktdsuniversity.edu.beans;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ktdsuniversity.edu.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CheckSessionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("Controller가 실행되기 전 입니다.");
		System.out.println(handler.getClass().getName() + "가 실행됩니다.");
		System.out.println(request.getRequestURI());
		
		// 브라우저의 세션을 가져온다.
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("_LOGIN_USER_");
		// 브라우저 세션이 비어있다면 (로그인을 안했다면)
		if (memberVO == null) {
			// 로그인 페이지로 이동을 시켜야하고
			// 로그인을 완료하면 원래 가려했던 URL로 이동을 시켜주어야 한다.
			System.out.println("로그인이 필요합니다!!!");
		}
		
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("Controller 가 실행 된 후 입니다.");
		System.out.println(handler.getClass().getName() + "이 실행되었습니다.");
		System.out.println(modelAndView + "를 반환했습니다.");
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("View를 만들어 브라우저에게 반환하기 전 입니다.");
		System.out.println(handler.getClass().getName() + "이 실행되었습니다.");
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
