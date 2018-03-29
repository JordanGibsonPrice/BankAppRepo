package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.beans.Account;
import com.revature.beans.Employee;
import com.revature.dao.AccountDao;
import com.revature.dao.EmpDao;
import com.revature.services.LoggingUtil;  

public class Existence extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String n = request.getParameter("username");
		String p = request.getParameter("userpass");

		if (EmpDao.validate(n, p)) {
			HttpSession session = request.getSession();
			Employee e = EmpDao.selectEmployeeByUsername(n);
			Account a = AccountDao.selectAccountByUsername(e.getUsername());
			session.setAttribute("username", e.getUsername());
			session.setAttribute("empId", e.getEmpId());
			session.setAttribute("status", e.getStatus());
			session.setAttribute("fname", e.getFname());
			session.setAttribute("lname", e.getLname());
			session.setAttribute("email", e.getEmail());
			LoggingUtil.logInfo("new session has been created by user: " + request.getParameter("username"));
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.forward(request, response);
		} else {
			out.print("Sorry, wrong username or password");
			RequestDispatcher rd = request.getRequestDispatcher("UserLogin.html");
			rd.include(request, response);
		}

		out.close();
	}
}

