package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.beans.Account;
import com.revature.services.LoggingUtil;
import com.revature.util.ConnectionUtil;

public class AccountDao {
	static ConnectionUtil cu = ConnectionUtil.getInstance();
	static Connection conn = null;
	static PreparedStatement ps = null;
	private static List<Account> accounts = new ArrayList<Account>();
	
	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		AccountDao.accounts = accounts;
	}

	public boolean insertNewAccount(Account i, HttpServletRequest request){
		try {

			conn = ConnectionUtil.getConnection();
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("username");
			String generated_columns[] = { "accNum" };
			String sql = "INSERT INTO Accounts (username, atHand, applied, pending)" + "VALUES(?,?,?,?)";
			 ps = conn.prepareStatement(sql, generated_columns);
			ps.setString(1, username);
			ps.setInt(2, 1000);
			ps.setInt(3, i.getApplied());
			ps.setInt(4, i.getPending());
			if (ps.executeUpdate() != 0) {
				conn.commit();
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int account_id =  rs.getInt(1);
					i.setAccNum(account_id);
				}
				LoggingUtil.logTrace("We have inserted account " + i.getAccNum());
				return true;
			}
			else {
				LoggingUtil.logError("Whoops, something went wrong!!!");
				conn.rollback();
				return false;
			}
		} catch (SQLException e) {
			LoggingUtil.logError("Whoops, something went wrong!!!");
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	
	public static boolean updateUAs(int accNum, String username) {
		try {
			conn = ConnectionUtil.getConnection(); 
			String sql = "INSERT into Emp_Accounts VALUES(?, ?)";
			 ps = conn.prepareStatement(sql); 
			ps.setInt(1, accNum);
			ps.setString(2, username);
			if (ps.executeUpdate() != 0) {
				conn.commit();
				LoggingUtil.logWarn("This was never used, what was the point?");
				return true;
			}
			else {
				LoggingUtil.logError("Whoops, something went wrong!!!");
				conn.rollback();
				return false;
			}
		} catch (SQLException e) {
			LoggingUtil.logError("Whoops, something went wrong!!!");
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	
	public Account selectAccountByAccNum(int accNum){ 
		Account a = new Account();
		try {
			  conn = ConnectionUtil.getConnection();
			 ps = null;
			String sql = "SELECT * FROM Accounts WHERE accNum = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, accNum);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				a = new Account();
				a.setAccNum(rs.getInt("accNum"));
				a.setUsername(rs.getString("username"));
				a.setAtHand(rs.getInt("atHand"));
				a.setApplied(rs.getInt("applied"));
				a.setPending(rs.getInt("pending"));
			}
			LoggingUtil.logInfo("We have access to account number" + accNum);
			conn.commit();
			return a; // might need to be inside the loop
		}catch(SQLException e){
			try {
				LoggingUtil.logError("Whoops, something went wrong!!!");
				conn.rollback();
			} catch (SQLException e1) {
				LoggingUtil.logError("Whoops, something went wrong!!!");
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			closeResources();
		}
		return a;
		
	}
	
	public static boolean accountApproval(int atHand, int pending, int applied, int accNum) {
		boolean check = false;
		try {
					conn = ConnectionUtil.getConnection(); 
					String sql = "UPDATE ACCOUNTS SET ATHAND=?, PENDING=?, APPLIED=? WHERE ACCNUM=?"; //set the account info
					ps = conn.prepareStatement(sql);
					ps.setInt(1, atHand);
					ps.setInt(2, pending);
					ps.setInt(3, applied);
					ps.setInt(4, accNum);
					ps.executeUpdate();
					if (ps.executeUpdate() != 0) {
						conn.commit();
						LoggingUtil.logTrace("Account number " + accNum + " has been approved!");
						check = true;
						return check;
					}
					else {
						LoggingUtil.logError("Whoops, something went wrong!!!");
						conn.rollback();
						check = false;
						return check;
					}
				} catch (SQLException e) {
					LoggingUtil.logError("Whoops, something went wrong!!!");
					e.printStackTrace();
					check = false;
					return check;
				} finally {
					System.out.println(check);
					closeResources();
				}
	}
	
	public static boolean validateNums(int appID, int accNum){  
		boolean status = false;
		  conn = ConnectionUtil.getConnection();
		try {
			String sql = "select * from ACCOUNTS WHERE appID=? and accNum=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, appID);
			ps.setInt(2, accNum);
			ResultSet rs = ps.executeQuery();
			status = rs.next();
			ps.close();
			LoggingUtil.logTrace("Account number " + accNum + " is valid.");
		} catch (Exception e) {
			LoggingUtil.logError("Whoops, something went wrong!!!");
			System.out.println(e.getMessage());
			try {
				LoggingUtil.logError("Whoops, something went wrong!!!");
				conn.rollback();
			} catch (SQLException e1) {
				LoggingUtil.logError("Whoops, something went wrong!!!");
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return status;
	}
	
	public static boolean validateName(String name){
		boolean status=true;
		try{
		conn = cu.getConnection();
		PreparedStatement ps=conn.prepareStatement(
		"select * from ACCOUNTS WHERE username=?");
		ps.setString(1,name);
		ResultSet rs=ps.executeQuery();
		status=rs.next();
		ps.close();
		LoggingUtil.logTrace("Username " + name + " is valid.");
		}
		catch(Exception e){
			LoggingUtil.logError("Whoops, something went wrong!!!");
			System.out.println(e.getMessage());
			status=false;
			}
		return status;
		}
	
	public static Account selectAccountByUsername(String username){
		Account a = null;
		try{
			conn = cu.getConnection();
			String sql = "SELECT * FROM ACCOUNTS WHERE username = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				a = new Account();
				a.setAccNum(rs.getInt("accNum"));
				a.setUsername(rs.getString("username"));
				a.setAtHand(rs.getInt("atHand"));
				a.setApplied(rs.getInt("applied"));
				a.setPending(rs.getInt("pending"));
			}
			rs.close();
		}catch(SQLException s){
			LoggingUtil.logError("Whoops, something went wrong!!!");
			s.printStackTrace();
		}finally{
			closeResources();
		}
		return a;
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