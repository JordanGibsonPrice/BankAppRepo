package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.services.getApps;

/**
 * Servlet implementation class ReimbursementTable
 */
public class AppManipulationClearance extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		PrintWriter out = response.getWriter();
		int status = (int) session.getAttribute("status");
		System.out.println(status);
		if (status == 4) {
			System.out.println("Im here");
			getApps.getAllApps(response);
		} else if (status == 3) {
			System.out.println("Im there");
			getApps.getAppsByStatus(response, username);
		} else if (status == 2) {
			System.out.println("im nowhere");
			getApps.getAppsBySup(request, response);
		} else {
			System.out.println("im everywhere");
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
			out.write("Improper permissions.");
		}
	}
}