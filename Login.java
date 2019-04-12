package com.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.dao.User;

import com.service.ServiceInterface;
//import com.service.ServiceInterfaceLogin;
import com.service.ServiceMain;
//import com.service.ServiceMainLogin;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);

		ServiceInterface service = new ServiceMain();
		HttpSession session = request.getSession();

		// get values of username and password from login page
		String uname = request.getParameter("uname");
		String password = request.getParameter("password");

		// set those values into user object
		User user = new User();
		user.setUname(uname);
		user.setPassword(password);

		// call a method from service interface and store it's result into string
		String result = service.login(user);
		// validating the user
		if (result.equalsIgnoreCase("success")) {
			// store username into session variable
			session.setAttribute("uname", uname);

			ServletContext servletContext = getServletContext();
			// forward that page to another servlet to show profile to user
			RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/ProfilePage");
			requestDispatcher.forward(request, response);

		} else {
			// if user has not been registerd than it will show error
			response.sendRedirect("error.jsp");
		}

	}
}
