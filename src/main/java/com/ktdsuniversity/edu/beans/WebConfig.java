package com.ktdsuniversity.edu.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.ktdsuniversity.edu.beans.filter.xss.XssEscapeServletFilter;
import com.ktdsuniversity.edu.beans.websocket.CafeWebSocketHandler;

@Configuration // 인터셉터를 등록하기 위한 애노테이션. Spring Boot Web 에 관한 설정.
@Configurable // Spring Boot 설정을 해주는 클래스로 변경하겠다.
@EnableWebMvc // WebMVC Module을 사용하겠다. (Validator 사용하기 위해서)
@EnableWebSocket
public class WebConfig implements WebMvcConfigurer, WebSocketConfigurer {

	/**
	 * 채팅 핸들러
	 */
	@Autowired
	private CafeWebSocketHandler handler;
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// JSP 파일의 위치를 설정한다.
		registry.jsp("/WEB-INF/views/", ".jsp");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 정적자원의 URL과 위치를 설정한다.
		registry.addResourceHandler("/js/**")
		        .addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/css/**")
		        .addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
	}
	
	/**
	 * 인터셉터를 등록한다.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		// 세션 체크를 하지 않을 URL 패턴들을 정의.
		List<String> excludePatterns = new ArrayList<>();
		excludePatterns.add("/member/regist/**");
		excludePatterns.add("/member/login");
		excludePatterns.add("/board/list");
		excludePatterns.add("/js/**");
		excludePatterns.add("/css/**");
		excludePatterns.add("/images/**");
		excludePatterns.add("/error/**");
		
		// 인터셉터 등록.
		registry.addInterceptor( new CheckSessionInterceptor() )
				// CheckSessionInterceptor가 모든 URL을 대상으로
				// 요청과 응답을 가로채도록 한다.
		        .addPathPatterns("/**")
		        // Interceptor가 개입하지 않을 URL 지정.
		        .excludePathPatterns(excludePatterns);
		
	}

	/**
	 * 웹소켓 엔드포인트 생성.
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(handler, "/cafe-chat").setAllowedOriginPatterns("*").withSockJS();
	}
	
	@Bean
	public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {
		FilterRegistrationBean<XssEscapeServletFilter> filterRegistration = new FilterRegistrationBean<>();
		filterRegistration.setFilter(new XssEscapeServletFilter());
		filterRegistration.setOrder(1);
		filterRegistration.addUrlPatterns("/*");
		return filterRegistration;
	}
}

