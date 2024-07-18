package com.monocept.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.monocept.pojos.User;

public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/*
		 * HttpServletRequest req = (HttpServletRequest) request; HttpSession session =
		 * req.getSession();
		 * 
		 * String user=(String)session.getAttribute("user");
		 */
		chain.doFilter(request, response);

	}

}
