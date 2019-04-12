package com.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.dao.Address;
import com.dao.User;
import com.service.ServiceInterface;
import com.service.ServiceMain;

/**
 * Servlet implementation class Registration
 * 
 * 
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 10, // 10 MB
		maxRequestSize = 1024 * 1024 * 15 // 15 MB

)
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Registration() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// PrintWriter out = response.getWriter();

		// get all the values from registration form
		String action = request.getParameter("action");
		String uname = request.getParameter("uname");
		String role = request.getParameter("role");
		String bdate = request.getParameter("bdate");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		String cno = request.getParameter("cno");
		String rgen = request.getParameter("rgen");
		String terms = request.getParameter("terms");
		String language[] = request.getParameterValues("language");
		List<String> list1 = Arrays.asList(language);
		String langlist = String.join(",", list1);

		String[] country = request.getParameterValues("country");
		String[] state = request.getParameterValues("state");
		String[] city = request.getParameterValues("city");
		String[] street = request.getParameterValues("street");
		String[] pin = request.getParameterValues("pin");
		InputStream inputStream = null; // input stream of the upload file
		Part filePart = request.getPart("myimg");
		if (filePart != null) {

			// obtains input stream of the upload file
			inputStream = filePart.getInputStream();
		}

		if ("Registration".equals(action)) {

			ServiceInterface service = new ServiceMain();

			// created new array list and store address into it
			List<Address> listadd = new ArrayList<Address>();
			// for loop to add more than one address
			for (int i = 0; i < country.length; i++) {
				// it will add all the address into new array list named listadd
				listadd.add(new Address(country[i], state[i], city[i], street[i], pin[i]));

			}

			// store all the values into user object
			User user = new User();
			user.setUname(uname);
			user.setRole(role);
			user.setBdate(bdate);
			user.setEmail(email);
			user.setPassword(password);
			user.setRepassword(repassword);
			user.setCno(cno);
			user.setRgen(rgen);
			user.setLanglist(langlist);
			user.setListadd(listadd);
			user.setTerms(terms);

			if (inputStream != null) {
				// fetches input stream of the upload file for the blob column
				user.setB(inputStream);
				// statement.setBlob(3, inputStream);
			}

			// call a method pass from service interface
			service.pass(user);
			// forward it to login page
			response.sendRedirect("login.jsp");

		} else {

			// this loop will run when user has arrived on registration page and now he
			// wanted to update that data
			ServiceInterface serviceinterface = new ServiceMain();

			// get id of user
			String id = request.getParameter("id");
			// get all the id of user
			String[] id1 = request.getParameterValues("id");
			// get all the unique address ids
			String[] add_id = request.getParameterValues("add_id");

			// if user have updated image
			InputStream inputStream1 = null; // input stream of the upload file
			Part filePart1 = request.getPart("myimg");
			if (filePart1 != null) {

				// obtains input stream of the upload file
				inputStream1 = filePart1.getInputStream();

			}

			// new array list in which all the edited address will be stored
			List<Address> listnewadd = new ArrayList<Address>();
			// this loop will add all the address into new array list
			for (int i = 0; i < country.length; i++) {

				listnewadd.add(new Address(country[i], state[i], city[i], street[i], pin[i], id1[i], add_id[i]));

			}

			// store all that data into user objecct
			User user1 = new User();
			user1.setId(id);
			user1.setUname(uname);
			user1.setRole(role);
			user1.setBdate(bdate);
			user1.setEmail(email);
			user1.setPassword(password);
			user1.setRepassword(repassword);
			user1.setCno(cno);
			user1.setRgen(rgen);
			user1.setLanglist(langlist);
			user1.setListadd(listnewadd);

			if (inputStream1 != null) {
				// fetches input stream of the upload file for the blob column
				user1.setB(inputStream1);

			}

			// call a method update from service interface and store resultant output as a
			// string...it will return string update if it's updated
			String result = serviceinterface.update(user1);
			// call a method imageupdate from service interface and store resultant output
			// as a string...it will return string update if it's updated
			String imgresult = serviceinterface.imageupdate(user1);

			// call a method reshowadd from service interface to get old address
			ArrayList<Address> reshowList = serviceinterface.reshowAdd(user1);

			// operation if user have deleted any address

			// get all the ids from array list
			ArrayList<String> newIds = new ArrayList<String>();
			for (int i = 0; i < listnewadd.size(); i++) {
				// store all the address ids into string s1
				String s1 = listnewadd.get(i).getAdd_id();
				// add ids into array list
				newIds.add(s1);

			}

			// get address id which was removed from arraylist
			ArrayList<String> removed = new ArrayList<String>();
			for (Address add : reshowList) {
				// this loop will give all the deleted ids
				if (!newIds.contains(add.getAdd_id())) {
					// all the ids will be added into removed named array list
					removed.add(add.getAdd_id());
				}
			}

			for (String id11 : removed) {
				// it will call delete address from service interface and it will have all the
				// address ids which was removed
				serviceinterface.deleteAddress(id11);

			}

			// this operation is for user have added any new address from multiple address
			// field

			// new array list created to get all the ids
			ArrayList<String> newIdss = new ArrayList<String>();
			for (int i = 0; i < reshowList.size(); i++) {
				// all the ids from the old list will be added
				String s1 = reshowList.get(i).getAdd_id();
				newIdss.add(s1);

			}
			// new arraylist will be created
			ArrayList<String> newId = new ArrayList<String>();

			for (int i = 0; i < listnewadd.size(); i++) {
				// all the id from new array list will be added id...and new address's id will
				// be null
				String s1 = listnewadd.get(i).getAdd_id();
				newId.add(s1);

			}

			// new array list will be generead to add new address data
			ArrayList<Address> insertQuery = new ArrayList<Address>();
			for (int i = 0; i < listnewadd.size(); i++) {
				// whose address id will be null those data will be added into new arraylist
				if (listnewadd.get(i).getAdd_id().equals("null")) {
					insertQuery.add(new Address(country[i], state[i], city[i], street[i], pin[i], id, add_id[i]));
				}
			}

			// object of user will be generated
			User useradd = new User();

			useradd.setListadd(insertQuery);

			// new method will be called from service interface to add
			serviceinterface.newAdded(useradd);

			// this operation is for update oeration
			// all the old address ids will be present over here
			ArrayList<String> newIdsaa = new ArrayList<String>();
			for (int i = 0; i < reshowList.size(); i++) {

				String s1 = reshowList.get(i).getAdd_id();
				newIdsaa.add(s1);

			}
			// all the id of new array list will be present over here
			ArrayList<String> newIdi = new ArrayList<String>();
			for (int i = 0; i < listnewadd.size(); i++) {

				String s1 = listnewadd.get(i).getAdd_id();
				newIdi.add(s1);

			}

			// compare both of the old and new array list wheather it's updated or not
			// out.println(reshowList.toString().equals(listnewadd.toString()));

			// new array list for update query
			ArrayList<Address> updateQuery = new ArrayList<Address>();
			for (int i = 0; i < listnewadd.size(); i++) {

				// all the address data will be added into new array list
				updateQuery.add(new Address(country[i], state[i], city[i], street[i], pin[i], id, add_id[i]));

			}
			// new user object created to store address data and to pass it to servuce
			// interface
			User userupdate = new User();
			userupdate.setListadd(updateQuery);
			// new method will be called from service interface from service interface
			serviceinterface.updateAdd(userupdate);

			if (result.equalsIgnoreCase("update") || imgresult.equalsIgnoreCase("update")) {
				ServletContext servletContext = getServletContext();

				RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/ProfilePage");
				requestDispatcher.forward(request, response);

			} else {
				response.sendRedirect("error.jsp");
			}

		}

	}

}
