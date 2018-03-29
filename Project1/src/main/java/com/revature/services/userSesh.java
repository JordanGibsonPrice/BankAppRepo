package com.revature.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.beans.Employee;
import com.revature.dao.EmpDao;

public class userSesh extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		EmpDao empData = new EmpDao();
		Employee emp = empData.selectEmployeeByUsername("username");

		if (session.isNew()) {
			out.println("Session Created for " + request.getParameter("username"));
			session.setAttribute("username", request.getParameter("username"));
			session.setAttribute("employeeId", emp.getEmpId());
			session.setAttribute("visit", 0);
		} else {
			String username = ((String) session.getAttribute("username")).toUpperCase();
			out.println("Cannot create Session");
			out.println("Currently logged in as " + username);

		}
	}
}
