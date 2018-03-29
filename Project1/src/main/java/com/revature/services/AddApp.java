package com.revature.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.beans.Application;
import com.revature.beans.Employee;
import com.revature.dao.AppDao;
import com.revature.dao.EmpDao;

public class AddApp { //THIS IS NEVER USED?!??!?!?!?
//	public static void addapp(HttpServletRequest request, HttpServletResponse response) {
//		String eventDatestr = request.getParameter("eventDate");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		EmpDao ed = new EmpDao();
//		Date eventDate = null;
//		try {
//			eventDate = sdf.parse(eventDatestr);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		HttpSession session = request.getSession(true);
//		String employeeId = String.valueOf((session.getAttribute("employeeid")));
//		Employee emp = EmpDao.selectEmployeeByIDNum(Integer.parseInt(employeeId));
//		AppDao reimdb = new AppDao();
//		Application app = new Application(
//				Integer.parseInt(request.getParameter("empId")),
//				Integer.parseInt(request.getParameter("accNum")),
//				Integer.parseInt(request.getParameter("appNum")),
//				Integer.parseInt(request.getParameter("deptNum")),
//				request.getParameter("empFirst"),
//				request.getParameter("empLast"),
//				request.getParameter("email"),
//				request.getParameter("description"),
//				Integer.parseInt(request.getParameter("expense")),
//				request.getParameter("justification"),
//				Integer.parseInt(request.getParameter("grade")),
//				Integer.parseInt(request.getParameter("eventType")),
//				request.getParameter("eventLocus"),
//				request.getParameter("requestDuration"),
//				0
//				);
//		try {
//			PrintWriter out = response.getWriter();
//			if(AppDao.insertNewApplication(app, request)) {
//				out.println("<h1>You've successfully added a reimbursement request.");
//			}
//			else {
//				out.println("<h1>Whoops, something went wrong</h1>");
//			}
//			out.println("<hr>" + "<a href='make_request.html'>BACK</a>");
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
}


