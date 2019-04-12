package com.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class FilterValidation
 */
public class FilterValidation implements Filter {

	/**
	 * Default constructor.
	 */
	public FilterValidation() {

	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		response.setContentType("text/html");

		String uname = request.getParameter("uname");
		String password = request.getParameter("password");

		if (uname.equals("")) {

			String msg = "please fill username...u can not leave it empty";
			request.setAttribute("msg", msg);

		} else if (uname.length() < 3) {
			String msg9 = "length of your name must be greater than 2";
			request.setAttribute("msg9", msg9);

		}

		if (password.equals("")) {

			String msg3 = "please enter password";
			request.setAttribute("msg3", msg3);

		}

		if (uname.equals("") || password.equals("") || uname.length() < 3) {
			request.getRequestDispatcher("login.jsp").forward(request, response);

		}

		else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
