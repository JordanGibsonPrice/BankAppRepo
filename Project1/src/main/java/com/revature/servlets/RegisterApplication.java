package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.beans.Account;
import com.revature.beans.Application;
import com.revature.beans.Employee;
import com.revature.dao.AccountDao;
import com.revature.dao.AppDao;
import com.revature.dao.EmpDao;

  
  
public class RegisterApplication extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		int empId = (int) session.getAttribute("empId");
		String username = (String) session.getAttribute("username");
		String fname = (String) session.getAttribute("fname");
		String lname = (String) session.getAttribute("lname");
		String email = (String) session.getAttribute("email");
		Employee e = EmpDao.selectEmployeeByUsername(username);
		Random rand = new Random();
		int max = 5;
		int min = 1;
		int dept = rand.nextInt((max - min) + 1) + min;
		int superId = e.getSupervisorId();
		if (AccountDao.selectAccountByUsername(username) == null) {
			out.print("Silly billy, you forgot to register an account first! Stop messing around.");
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
		}
		 else {
			 session.setAttribute("accNum", AccountDao.selectAccountByUsername(e.getUsername()).getAccNum());
			 Account ac = AccountDao.selectAccountByUsername(username);
			 String stringDate = request.getParameter("eventDate");
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 Date eventDate = null;
				try {
					eventDate = sdf.parse(stringDate);
				} catch (ParseException pe) {
					// TODO Auto-generated catch block
					pe.printStackTrace();
				}
			 Application a = new Application(empId, ac.getAccNum(), 0, dept, fname, lname, email, request.getParameter("description"),
					 Double.parseDouble(request.getParameter("expense")), request.getParameter("justification"), 0, 
					 Integer.parseInt(request.getParameter("eventType")), request.getParameter("eventLocus"), 
					 request.getParameter("requestDuration"), superId, eventDate);
			 long timeInMills = a.getRequestTime().getTime();
			 Timestamp ts_now = new Timestamp(eventDate.getTime());
			 long timeInMills1 = ts_now.getTime();
			 long diff = timeInMills1 - timeInMills;
			 if (diff < 775363440L) {
				 out.println("You've applied too late, you will not be reimbursed.");
				 RequestDispatcher rd = request.getRequestDispatcher("index.html");
				 rd.include(request, response);
			 }
			AppDao.insertNewApplication(a, request);
			session.setAttribute("accNum", ac.getAccNum());
			out.print("Application successfully added!");
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
		}
		
		
	}
}

