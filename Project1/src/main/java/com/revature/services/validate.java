package com.revature.services;

import javax.servlet.http.HttpServletRequest;

import com.revature.beans.Employee;
import com.revature.dao.EmpDao;

public class validate {
	public static boolean validate(HttpServletRequest request){
		Employee emp = new Employee(
					request.getParameter("fname"),
					request.getParameter("lname"),
					request.getParameter("email"),
					request.getParameter("username"),
					request.getParameter("password")
				);
		EmpDao empData = new EmpDao();
		
		if(empData.selectEmployeeByUsername(emp.getUsername())!=null){
			return false;
		}
		empData.insertNewEmployee(emp);

		return true;
	}
}
