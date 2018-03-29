package com.revature.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.revature.beans.Account;
import com.revature.beans.Application;
import com.revature.beans.Content;
import com.revature.dao.AccountDao;
import com.revature.dao.AppDao;

public class DeliverContent extends HttpServlet {
	protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Content content = new Content();
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");
		Account a = AccountDao.selectAccountByUsername(username);
		session.setAttribute("accNum", a.getAccNum());
		List<Application> listApps = AppDao.getAppsByUser(username, req);
//		int reqNum = Integer.parseInt(req.getParameter("appNum"));
		session.setAttribute("appNum", listApps.get(0).getAppNum());
		int appNum = (int) session.getAttribute("appNum");
		content.setAppNum(appNum);
		Part contents = req.getPart("content");
		InputStream is = null;
		ByteArrayOutputStream os = null;

		try {
			is = contents.getInputStream();
			os = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];

			while (is.read(buffer) != -1) {
				os.write(buffer);
			}
		content.setContent(os.toByteArray());
		} catch (IOException e) {
			System.out.println("Could not upload file!");
			e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		}
		boolean isSuccess = AppDao.insertContent(content, req);
		if (isSuccess) {
			resp.sendRedirect(req.getContextPath() + "/index.html");
		} else {
			req.getRequestDispatcher("index.html").forward(req, resp);
		}
	}

}
