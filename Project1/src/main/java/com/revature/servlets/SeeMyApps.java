package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.beans.Account;
import com.revature.dao.AccountDao;
import com.revature.services.getApps;

/**
 * Servlet implementation class RequestReimbursement
 */
public class SeeMyApps extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		Account a = AccountDao.selectAccountByUsername(username);
		session.setAttribute("accNum", a.getAccNum());
		getApps.getUserApps(request, response);
	}

}