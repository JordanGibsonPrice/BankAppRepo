package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.beans.Employee;
import com.revature.dao.AccountDao;
import com.revature.dao.AppDao;
import com.revature.dao.EmpDao;
import com.revature.util.ConnectionUtil;

public class Updating extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//Edit!
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		int a = (int) session.getAttribute("accNum");
		int s = (int) session.getAttribute("status");
		int e = Integer.parseInt(request.getParameter("appID"));
		int d = Integer.parseInt(request.getParameter("decision"));
		Employee em = EmpDao.selectEmployeeByUsername((String) session.getAttribute("username"));//what was I going to do with this?
		if (s > 1) {
			try {
				AppDao.update(a,e,d, request);
				Connection conn;
				conn = ConnectionUtil.getConnection();
				conn.commit();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				out.print("Update proccessed!");
				RequestDispatcher rd = request.getRequestDispatcher("Display.html");
				rd.include(request, response);
			}
		} else {
			out.write("Improper permissions, you are probably a regular employee");
			RequestDispatcher rd = request.getRequestDispatcher("Display.html");
			rd.include(request, response);
		}
	}
}
