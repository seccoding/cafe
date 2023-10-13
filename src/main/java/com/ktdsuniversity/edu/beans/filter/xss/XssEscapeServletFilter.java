package com.ktdsuniversity.edu.beans.filter.xss;

import java.io.IOException;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class XssEscapeServletFilter implements Filter {

	private XssEscapeFilter xssEscapeFilter = XssEscapeFilter.getInstance();
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(new XssEscapeServletFilterWrapper(request, xssEscapeFilter), response);
	}

}
