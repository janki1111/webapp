package com.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.Address;
import com.dao.User;
import com.service.ServiceInterface;
import com.service.ServiceMain;

/**
 * Servlet implementation class EditData
 */
public class EditData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditData() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session1 = request.getSession();
		ServiceInterface serviceinterface = new ServiceMain();

		// get id which user's data want to edit
		String id = request.getParameter("id");

		// store id into user object
		User user = new User();
		user.setId(id);
		// store is into address object
		Address add = new Address();
		add.setId(id);

		// call a method editdata from service interface and store it into user object
		User user4 = serviceinterface.editdata(user);

		// call method editaddress from service interface and store that data into
		// arraylist
		ArrayList<Address> addeditList = serviceinterface.editaddress(add);

		// store both into session object
		session1.setAttribute("user4", user4);
		session1.setAttribute("addeditList", addeditList);

		// send this data to main registration page to display
		response.sendRedirect("index.jsp");

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
