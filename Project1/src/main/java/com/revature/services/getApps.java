package com.revature.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revature.beans.Application;
import com.revature.beans.Employee;
import com.revature.dao.AppDao;
import com.revature.dao.EmpDao;

public class getApps {
	public static void getAllApps(HttpServletResponse response) {
		AppDao ad = new AppDao();
		List<Application> apps = ad.getAllApps();
		response.setContentType("json/application");

		try {
			PrintWriter out = response.getWriter();
			Gson gson = new Gson(); // install gson
			Type type = new TypeToken<List<Application>>() {
			}.getType();
			String json = gson.toJson(apps, type);
			out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getUserApps(HttpServletRequest request, HttpServletResponse response) {
		AppDao ad = new AppDao();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		List<Application> apps = ad.getAppsByUser(username, request);
//		response.setContentType("json/application");
		try {
			PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			Type type = new TypeToken<List<Application>>() {
			}.getType();
			String json = gson.toJson(apps, type);
			out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void getAppsByStatus(HttpServletResponse response, String username) {
		AppDao ap = new AppDao();
		List<Application> apps = ap.getAppsByStanding(username); // GOTTA WORK ON THIS FUNCTION
		response.setContentType("json/application");
		try {
			PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			Type type = new TypeToken<List<Application>>() {
			}.getType();
			String json = gson.toJson(apps, type);
			System.out.println(json);
			out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void getAppsBySup(HttpServletRequest request, HttpServletResponse response){ // MAYBE IMPLEMENT THIS
		AppDao ap = new AppDao();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		EmpDao ed = new EmpDao();
		System.out.println("im here");
		Employee emp = ed.selectEmployeeByUsername(username);
		
		List<Application> apps = ap.getAllCaseBySupId(emp.getSupervisorId());
		response.setContentType("json/application");
		try {
			PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			Type type = new TypeToken<List<Application>>() {
			}.getType();
			String json = gson.toJson(apps, type);
			System.out.println(json);
			out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
