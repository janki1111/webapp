package com.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.Address;

import com.service.ServiceInterface;
import com.service.ServiceMain;

/**
 * Servlet implementation class HelloServlet
 */
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HelloServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// this servlet is for popup of particular address

		HttpSession session = request.getSession();
		ServiceInterface service = new ServiceMain();

		// get id which user's id they want to watch
		String id = request.getParameter("id");

		// store that id into address object
		Address add = new Address();
		add.setId(id);

		// call a method addshow from service interface and than store that address data
		// into arraylist
		ArrayList<Address> result = service.addshow(add);

		// store arraylist into session variable
		session.setAttribute("result", result);
		// forward to jsp page to display
		response.sendRedirect("popup.jsp");

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
