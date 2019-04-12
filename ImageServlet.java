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
 * Servlet implementation class ImageServlet
 */
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// this servlet is to show particular image into popup

		ServiceInterface service = new ServiceMain();
		HttpSession session = request.getSession();

		// get which user's profile pic they want to watch
		String id = request.getParameter("id");

		// store it into user object
		User user = new User();
		user.setId(id);

		// call a method showimg from service interface and store it into result named
		// object
		User result = service.showimg(user);

		// store that data into session object
		session.setAttribute("result", result);
		// forward it to popupimg jsp page
		response.sendRedirect("popupimg.jsp");
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
