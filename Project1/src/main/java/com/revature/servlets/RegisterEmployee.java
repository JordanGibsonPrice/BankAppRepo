package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Employee;
import com.revature.dao.EmpDao;  
  
  
public class RegisterEmployee extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		EmpDao dao = new EmpDao();
		Employee e = new Employee(request.getParameter("fname"), request.getParameter("lname"),
				request.getParameter("email"), request.getParameter("uname"), request.getParameter("password"));
		if (EmpDao.selectEmployeeByUsername(e.getUsername()) != null) {
			out.print("Username has already been taken.");
			RequestDispatcher rd = request.getRequestDispatcher("Registration.html");
			rd.include(request, response);
		} else {
			dao.insertNewEmployee(e);
			out.print("Employee successfully added!");
			RequestDispatcher rd = request.getRequestDispatcher("UserLogin.html");
			rd.include(request, response);
		}
	}
}