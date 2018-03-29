package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Employee;
import com.revature.services.LoggingUtil;
import com.revature.util.ConnectionUtil;

public class EmpDao {
	static ConnectionUtil cu = ConnectionUtil.getInstance();
	static Connection conn = null;
	static PreparedStatement ps = null;

	private static List<Employee> employees = new ArrayList<Employee>();

	public static List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		EmpDao.employees = employees;
	}

	public void insertNewEmployee(Employee e){
		conn = ConnectionUtil.getConnection();
		String sql = "INSERT INTO Employees (fname, lname, email, username, passwords, Emp_Type, Status, superId)" +
					"VALUES(?,?,?,?,?,?,?,?)";
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1, e.getFname());
			ps.setString(2, e.getLname());
			ps.setString(3, e.getEmail());
			ps.setString(4, e.getUsername());
			ps.setString(5, e.getPassword());
			ps.setInt(6, 1);
			ps.setInt(7, 1);
			ps.setInt(8, e.getSupervisorId());

			int affected = ps.executeUpdate();
			System.out.println(affected + " ROWS INSERTED");
			conn.commit();
			LoggingUtil.logInfo("Employee " + e.getFname() + " " + e.getLname() + " has been inserted");
		}catch(SQLException a){
			a.printStackTrace();
			try {
				LoggingUtil.logError("Whoops, something went wrong!!!");
				conn.rollback();
			} catch (SQLException e1) {
				LoggingUtil.logError("Whoops, something went wrong!!!");
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			closeResources();
		}
	}

	public static Employee selectEmployeeByUsername(String username){
		Employee e = null;
		try{
			System.out.println("im finally here");
			conn = cu.getConnection();
			System.out.println("connected");
			String sql = "SELECT * FROM Employees WHERE username = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			System.out.println("so this happened");
			while(rs.next()){
				e = new Employee();
				e.setEmpId(rs.getInt("empId"));
				e.setFname(rs.getString("fname"));
				e.setLname(rs.getString("lname"));
				e.setEmail(rs.getString("email"));
				e.setUsername(rs.getString("username"));
				e.setPassword(rs.getString("passwords"));
				e.setStatus(rs.getInt("status"));
				e.setSupervisorId(rs.getInt("superId"));
			}
			System.out.println("and that happened");
			rs.close();
			LoggingUtil.logTrace("Employee " + username + " has been selected.");
			System.out.println("we're going out now");
		}catch(SQLException a){
			LoggingUtil.logError("Whoops, something went wrong!!!");
			a.printStackTrace();
		}finally{
			System.out.println("love me");
			closeResources();
		}
		return e;
	}

	public static Employee selectEmployeeByIDNum(int empID){
		Employee e = new Employee();
		conn = ConnectionUtil.getConnection();
		ResultSet rs = null;
		try{
			String sql = "SELECT * FROM Employees WHERE empId = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, empID);
			rs = ps.executeQuery();
			while(rs.next()){
				e = new Employee();
				e.setFname(rs.getString("fname"));
				e.setLname(rs.getString("lname"));
				e.setEmail(rs.getString("email"));
				e.setUsername(rs.getString("username"));
				e.setPassword(rs.getString("passwords"));
				e.setStatus(rs.getInt("status"));
				e.setSupervisorId(rs.getInt("superId"));
			}
			LoggingUtil.logTrace("Employee #" + empID + " has been selected.");
			return e;
		}catch(SQLException a){
			a.printStackTrace();
			try {
				LoggingUtil.logError("Whoops, something went wrong!!!");
				conn.rollback();
			} catch (SQLException e1) {
				LoggingUtil.logError("Whoops, something went wrong!!!");
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			closeResources();
		}
		return e;
		
	}

	public static boolean validate(String name,String pass){
		boolean status=true;
		try{
		conn = cu.getConnection();
		PreparedStatement ps=conn.prepareStatement(
		"select * from EMPLOYEES WHERE username=? and passwords=?");
		ps.setString(1,name);
		ps.setString(2,pass);
		ResultSet rs=ps.executeQuery();
		status=rs.next();
		ps.close();
		LoggingUtil.logTrace("Username/ password combination: " + name + " " + pass + " is valid.");
		}
		catch(Exception e){
			LoggingUtil.logError("Whoops, nobody is there to match the query.");
			System.out.println(e.getMessage());
			status=false;
			}
		return status;
		}
		
	private static void closeResources() {
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}
}