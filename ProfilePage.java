package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class ProfilePage
 */
public class ProfilePage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProfilePage() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		ServiceInterface service = new ServiceMain();
		// get username for update
		String uname = (String) session.getAttribute("uname");
		out.println(uname);

		// set username into user object
		User user = new User();
		user.setUname(uname);

		// interface for service
		ServiceInterface intser = new ServiceMain();
		String idno = intser.iddata(user);
		user.setId(idno);
		String role = intser.roledata(user);

		// set role and id into session variable
		session.setAttribute("role", role);
		session.setAttribute("idno", idno);

		// set id into address object to get all the values of address

		Address add = new Address();
		add.setId(idno);

		if (role.equals("admin") || uname.equals("admin")) {

			// data of user except address and profile pic
			ArrayList<User> dataList = service.showdata(user);
			// address data of user
			ArrayList<Address> addList = service.showadd(add);
			// profile pic data of all the user
			ArrayList<User> adminpic = service.showimgadmin();

			// store user data into session variable
			session.setAttribute("dataList", dataList);
			// store address data into session variable
			session.setAttribute("addList", addList);
			// store profile pic data into session variable
			session.setAttribute("adminpic", adminpic);

			response.sendRedirect("profile.jsp");

		} else

		{
			// data of user except address and profile pic
			ArrayList<User> userList = service.olddata(user);
			// address data of user
			ArrayList<Address> useraddList = service.addshow(add);
			// profile pic data of user
			User user5 = service.showimg(user);

			// store user data into session variable
			session.setAttribute("userList", userList);
			// store address data into session variable
			session.setAttribute("useraddList", useraddList);
			// store profile pic data into session variable
			session.setAttribute("user5", user5);

			response.sendRedirect("profile.jsp");

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
