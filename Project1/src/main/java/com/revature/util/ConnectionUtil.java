package com.revature.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class ConnectionUtil {
	
	private static ConnectionUtil cf = null;
	static Properties prop = new Properties();
	
	private ConnectionUtil(){
		InputStream dbProps = ConnectionUtil.class.getClassLoader().getResourceAsStream("database.properties");
		try {
			prop.load(dbProps);
		}
		catch (IOException i) {
			i.printStackTrace();
		}
		
	}
	
	public static synchronized ConnectionUtil getInstance(){
		
		if (cf==null){
			
			cf = new ConnectionUtil();
			
		}
		
		return cf;
		
	}
	
	public static Connection getConnection(){
		
		Connection conn = null;
		
		try {
			
//		    File file=new File("src/main/resources/database.properties");
//		    System.out.println(file.exists());
//		    Scanner scan=new Scanner(file);
//			prop.load(new FileReader(file));
			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(
					prop.getProperty("url"), 
					prop.getProperty("usr"), 
					prop.getProperty("psw"));
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
		
	}

}
