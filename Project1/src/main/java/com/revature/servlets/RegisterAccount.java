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
  
  
  
public class RegisterAccount extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		AccountDao dao = new AccountDao();
		Account a = AccountDao.selectAccountByUsername(username);
		Employee e = EmpDao.selectEmployeeByUsername(username);
		if (AccountDao.validateName(username) == false) {
			a = new Account();
			dao.insertNewAccount(a, request);
			a = AccountDao.selectAccountByUsername(username);
			e.setAccount(a);
			EmpDao.getEmployees().add(e);
			out.print("Account successfully added!");
			RequestDispatcher rd = request.getRequestDispatcher("Accounting.html");
			rd.include(request, response);
		} else {
			out.print("User all ready has an account.");
			RequestDispatcher rd = request.getRequestDispatcher("Accounting.html");
			rd.include(request, response);
		}
	}
}
