package com.revature.services;

import java.io.File;
import java.util.Random;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.revature.util.ConnectionUtil;

public class tester {
	public static void main(String[] args) throws FileNotFoundException {    
		int max = 15;
		int min = 10;

		Random rand = new Random();

		int n = rand.nextInt((max - min) + 1) + min;
		System.out.println(n);
//		Connection conn = null;
//		String sql = "INSERT INTO Media_Files (attachmentID, attachment) VALUES (?,?)";
//		PreparedStatement stmt = null;
//		InputStream is = null;
//		ConnectionUtil cu = ConnectionUtil.getInstance();
//	
//		File tempFile = new File("ws.pdf");
//		System.out.println(tempFile.exists()?"The sample file exists":"The sample file does not exist! Did you delete \"ws.pdf\"?");
//		
//		try {
//			is = new FileInputStream(tempFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			conn = cu.getConnection();
//			stmt = conn.prepareStatement(sql);
//			int x = 12;
//			stmt.setInt(1, x);
//			stmt.setBinaryStream(2, is, tempFile.length());
//			System.out.println("Executing SQL Statement: " + sql);
//			System.out.println("Connection is valid: " + conn.isValid(5));
//			System.out.println("Rows updated: " + stmt.executeUpdate());
//			System.out.println("Success!");
//		} catch (SQLException e) {
//			System.out.println("Failure!");
//			e.printStackTrace();
//		}
	}
}
