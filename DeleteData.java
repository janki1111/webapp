package com.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dao.User;
import com.service.ServiceInterface;
import com.service.ServiceMain;

/**
 * Servlet implementation class DeleteData
 */
public class DeleteData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteData() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServiceInterface service = new ServiceMain();

		// get id which data admin want to delete
		String id = request.getParameter("id");

		// validate admin can not delete itself and it is admin's data
		if (id.equals("259")) {

			ServletContext servletContext = getServletContext();
			// forwarding it to watch profile pic
			RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/ProfilePage");
			requestDispatcher.forward(request, response);

		} else {
			// set that id into user object
			User user = new User();
			user.setId(id);

			// calling deletedata from service and then store that result into string
			String string = service.deletedata(user);

			if (string.equalsIgnoreCase("success")) {
				// check wheather it is successfull deleteted or not
				ServletContext servletContext = getServletContext();
				// forward to watch profile page
				RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/ProfilePage");
				requestDispatcher.forward(request, response);

			} else {
				// if it's not deleted than forward it to error page
				response.sendRedirect("error.jsp");
			}
		}
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
