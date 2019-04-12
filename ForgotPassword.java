package com.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.User;
import com.service.ServiceInterface;
import com.service.ServiceMain;

/**
 * Servlet implementation class ForgotPassword
 */
public class ForgotPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ForgotPassword() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServiceInterface service = new ServiceMain();
		HttpSession session = request.getSession();

		// get email from form
		String emailforget = request.getParameter("emailforget");

		// store that email into user object
		User user = new User();
		user.setEmail(emailforget);

		// call method forgetpassword from service interface and than store data into
		// user object
		User password = service.forgetpassword(user);

		// store that object into session
		session.setAttribute("password", password);
		// than it will be redirected to login page to display username and password
		response.sendRedirect("getpassword.jsp");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
