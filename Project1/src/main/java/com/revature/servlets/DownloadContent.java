package com.revature.servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Content;
import com.revature.dao.AppDao;

public class DownloadContent extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int appNum = Integer.parseInt(request.getParameter("appNum"));
		
		Content content = AppDao.getSomeContent(appNum, request);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=#" + content.getAppNum() + ".pdf");
		InputStream is = new ByteArrayInputStream(content.getContent());
		OutputStream os = response.getOutputStream();
		byte[] buffer = new byte[1024];
		while (is.read(buffer) != -1) {
			os.write(buffer);
		}
		os.flush();
		os.close();
		is.close();
	}
		
}
