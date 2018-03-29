package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.services.LoggingUtil;

/**
 * Servlet implementation class LogoutServlet
 */
public class LogOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if(session!=null) {
			LoggingUtil.logInfo("Session created by user: " + request.getParameter("username") + " has been destroyed.");
			session.invalidate();
		}
		out.println("<div>Logged out of TRMS</div>");
		RequestDispatcher rd = request.getRequestDispatcher("UserLogin.html");
		rd.forward(request, response);
	}

}