package com.revature.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.beans.Account;
import com.revature.beans.Application;
import com.revature.beans.Content;
import com.revature.beans.Employee;
import com.revature.services.Calculation;
import com.revature.services.LoggingUtil;
import com.revature.util.ConnectionUtil;

public class AppDao {
	static ConnectionUtil cu = ConnectionUtil.getInstance();
	static Connection conn = null;
	static PreparedStatement ps = null;
	private static List<Application> applications = new ArrayList<Application>();
	EmpDao dao = new EmpDao();
	
	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		AppDao.applications = applications;
	}
	
	public static boolean insertNewApplication(Application a, HttpServletRequest request){
		PreparedStatement ps = null;
		try {
			conn = ConnectionUtil.getConnection();
			String generated_columns[] = { "appNum" };
			java.util.Date utilDate = a.getEventDate();
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			String sql = "INSERT INTO Applications (empID, accNum, deptNum, empFirst, empLast, email, description, expense, justification, eventDate, requestTime, grade, eventType, requestLocus, requestDuration, App_Type, superId)"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getEmpId());
			ps.setInt(2, a.getAccNum());
			ps.setInt(3, a.getDeptNum());
			ps.setString(4, a.getEmpFirst());
			ps.setString(5, a.getEmpLast());
			ps.setString(6, a.getEmail());
			ps.setString(7, a.getDescription());
			ps.setDouble(8, a.getExpense());
			ps.setString(9, a.getJustification());
			ps.setDate(10, sqlDate);
			ps.setTimestamp(11, a.getRequestTime());
			ps.setInt(12, a.getGrade());
			ps.setInt(13, a.getEventType());
			ps.setString(14, a.getEventLocus());
			ps.setString(15, a.getRequestDuration());
			ps.setInt(16, 2);
			ps.setInt(17, a.getSupervisorId());
			int affected = ps.executeUpdate();
			System.out.println(affected + " ROWS INSERTED");
			if (affected != 0) {
				conn.commit();
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int app_id =  rs.getInt(3);
					HttpSession session = request.getSession();
					session.setAttribute("appNum", app_id);
					System.out.println(app_id);
					System.out.println((int) session.getAttribute("appNum"));
					a.setAppNum(app_id);
					AccountDao dao = new AccountDao();
					Account ac = dao.selectAccountByAccNum(a.getAccNum());
					ac.setPending(ac.getPending() + (int)a.getExpense());
					AccountDao.accountApproval(ac.getAtHand(), ac.getPending(), (int)a.getExpense(), a.getAccNum());
				}
				LoggingUtil.logInfo("Application inserted into db by employee #" + a.getEmpId());
				return true;
			} else {
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
	
	public static boolean insertContent(Content content, HttpServletRequest request) {
		String generated_columns[] = { "attachmentID" };
		HttpSession session = request.getSession();
		int appNum = (int) session.getAttribute("appNum");
		try {
			conn = ConnectionUtil.getConnection();
			String sql = "INSERT INTO Media_Files VALUES (?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, appNum);
			ps.setBlob(2, (Blob) content);
			if (ps.executeUpdate() != 0) {
				conn.commit();
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
	
	public static Content getSomeContent(int appNum, HttpServletRequest request) {
		Content content = null;
		
		try {
			conn = ConnectionUtil.getConnection();
			String sql = "SELECT * FROM Media_Files WHERE attachmentID = ?";
			ps = conn.prepareStatement(sql);
			HttpSession session = request.getSession();
			int appNumber = (int) session.getAttribute("appNum");
			ps.setInt(1, appNumber);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				content = new Content();
				content.setAppNum(appNumber);
				content.setContent(rs.getBytes("attachment"));
			}
		} catch (SQLException e) {
			LoggingUtil.logError("Whoops, something went wrong!!!");
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return content;
	}
	
	public List<Content> getAllFiles(){
		List<Content> contents = new ArrayList<>();
		try {
			conn = ConnectionUtil.getConnection();
			String sql = "SELECT * FROM Media_Files";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Content content = new Content();
				content.setAppNum(rs.getInt("attachmentID"));
				content.setContent(rs.getBytes("attachment"));
				contents.add(content);
			}
			rs.close();
		} catch (SQLException e) {
			LoggingUtil.logError("Whoops, something went wrong!!!");
			e.printStackTrace();
		} finally {
			// We need to make sure our statements and connections are closed, 
			// or else we could wind up with a memory leak
			closeResources();
		}
		return contents;
	}
	
	public static Application selectApplicationByAppID(int appID){
		Application a = null;
		String sql = "SELECT * FROM Applications WHERE appNum = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = ConnectionUtil.getConnection();
		try{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, appID);
			rs = ps.executeQuery();
			while(rs.next()){
				a = new Application();
				a.setEmpId(rs.getInt("empId"));
				a.setAccNum(rs.getInt("accNum"));
				a.setAppNum(rs.getInt("appNum"));
				a.setDeptNum(rs.getInt("deptNum"));
				a.setEmpFirst(rs.getString("empFirst"));
				a.setEmpLast(rs.getString("empLast"));
				a.setEmail(rs.getString("email"));
				a.setDescription(rs.getString("description"));
				a.setExpense(rs.getDouble("expense"));
				a.setJustification(rs.getString("justification"));
				a.setGrade(rs.getInt("grade"));
				a.setEventType(rs.getInt("eventType"));
				a.setEventLocus(rs.getString("requestLocus"));
				a.setRequestDuration(rs.getString("requestDuration"));
				a.setStanding(rs.getInt("App_Type")); 
				a.setSupervisorId(rs.getInt("superId"));
				a.setEventDate(rs.getDate("eventDate"));
				applications.add(a);
			}
			LoggingUtil.logTrace("Application number " + appID + " retrieved.");
			conn.commit();
		}catch(SQLException e){
			LoggingUtil.logError("Whoops, something went wrong!!!");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			closeResources();
		}
		
		return a;
	}
	
	public List<Application> getAllApps() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		applications.clear();
		try {
			conn = ConnectionUtil.getConnection();
			String sql = "SELECT * FROM APPLICATIONS";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				// get employee object from userID
				Application application = new Application();
				application.setAccNum(rs.getInt("accNum"));
				application.setAppNum(rs.getInt("appNum"));
				application.setDeptNum(rs.getInt("deptNum"));
				application.setDescription(rs.getString("description"));
				application.setEmail(rs.getString("email"));
				application.setEmpFirst(rs.getString("empFirst"));
				application.setEmpId(rs.getInt("empId"));
				application.setEmpLast(rs.getString("empLast"));
				application.setEventDate(rs.getDate("eventDate"));
				application.setEventLocus(rs.getString("requestLocus"));
				application.setEventType(rs.getInt("eventType"));
				application.setExpense(rs.getInt("expense"));
				application.setGrade(rs.getInt("grade"));
				application.setJustification(rs.getString("justification"));
				application.setRequestDuration(rs.getString("requestDuration"));
				application.setRequestTime(rs.getTimestamp("requestTime"));
				application.setStanding(rs.getInt("App_Type")); 
				application.setSupervisorId(rs.getInt("superId"));
				applications.add(application);
				LoggingUtil.logTrace("Applications retrieved.");
			}
		} catch (SQLException e) {
			LoggingUtil.logError("Whoops, something went wrong!!!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return applications;
	}
	
	public static List<Application> getAppsByUser(String username, HttpServletRequest request) {
		List<Application> apps = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		HttpSession session = request.getSession();
		int accNum = (int) session.getAttribute("accNum");
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM APPLICATIONS WHERE accNum=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, accNum);
			rs = ps.executeQuery();
			while (rs.next()) {
				// get employee object from userID
				Application application = new Application();
				application.setAccNum(rs.getInt("accNum"));
				application.setAppNum(rs.getInt("appNum"));
				application.setDeptNum(rs.getInt("deptNum"));
				application.setDescription(rs.getString("description"));
				application.setEmail(rs.getString("email"));
				application.setEmpFirst(rs.getString("empFirst"));
				application.setEmpId(rs.getInt("empId"));
				application.setEmpLast(rs.getString("empLast"));
				application.setEventDate(rs.getDate("eventDate"));
				application.setEventLocus(rs.getString("requestLocus"));
				application.setEventType(rs.getInt("eventType"));
				application.setExpense(rs.getInt("expense"));
				application.setGrade(rs.getInt("grade"));
				application.setJustification(rs.getString("justification"));
				application.setRequestDuration(rs.getString("requestDuration"));
				application.setRequestTime(rs.getTimestamp("requestTime"));
				application.setStanding(rs.getInt("App_Type"));
				application.setSupervisorId(rs.getInt("superId"));

				apps.add(application);
			}
			LoggingUtil.logTrace("Applications for " + username + " retrieved.");
		} catch (SQLException S) {
			// TODO Auto-generated catch block
			LoggingUtil.logError("Whoops, something went wrong!!!");
			S.printStackTrace();
		} finally {
			closeResources();
		}
		return apps;
	}
		
	public static List<Application> getAppsByStanding(String username) {
		List<Application> apps = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM APPLICATIONS WHERE standing < (SELECT STATUS FROM EMPLOYEES WHERE USERNAME = ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();

			while (rs.next()) {
				// get employee object from userID
				Application application = new Application();
				application.setAccNum(rs.getInt("accNum"));
				application.setAppNum(rs.getInt("appNum"));
				application.setDeptNum(rs.getInt("deptNum"));
				application.setDescription(rs.getString("description"));
				application.setEmail(rs.getString("email"));
				application.setEmpFirst(rs.getString("empFirst"));
				application.setEmpId(rs.getInt("getEmpId"));
				application.setEmpLast(rs.getString("empLast"));
				application.setEventDate(rs.getDate("eventDate"));
				application.setEventLocus(rs.getString("eventLocus"));
				application.setEventType(rs.getInt("eventType"));
				application.setExpense(rs.getInt("expense"));
				application.setGrade(rs.getInt("grade"));
				application.setJustification(rs.getString("justification"));
				application.setRequestDuration(rs.getString("requestDuration"));
				application.setRequestTime(rs.getTimestamp("requestTime"));
				application.setStanding(rs.getInt("App_Type"));
				application.setSupervisorId(rs.getInt("superId"));
				apps.add(application);
			}
			LoggingUtil.logTrace("Applications for " + username + " retrieved.");
		} catch (SQLException S) {
			// TODO Auto-generated catch block
			LoggingUtil.logError("Whoops, something went wrong!!!");
			S.printStackTrace();
		} finally {
			closeResources();
		}
		return apps;
	}
	
	
	public static List<Application> getAllCaseBySupId(int supId) {
		List<Application> apps = new ArrayList<>();
		ps = null;
		ResultSet rs = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM APPLICATIONS WHERE superId = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, supId);
			rs = ps.executeQuery();

			while (rs.next()) {
				// get employee object from userID
				Application application = new Application();
				application.setAccNum(rs.getInt("accNum"));
				application.setAppNum(rs.getInt("appNum"));
				application.setDeptNum(rs.getInt("deptNum"));
				application.setDescription(rs.getString("description"));
				application.setEmail(rs.getString("email"));
				application.setEmpFirst(rs.getString("empFirst"));
				application.setEmpId(rs.getInt("getEmpId"));
				application.setEmpLast(rs.getString("empLast"));
				application.setEventDate(rs.getDate("eventDate"));
				application.setEventLocus(rs.getString("eventLocus"));
				application.setEventType(rs.getInt("eventType"));
				application.setExpense(rs.getInt("expense"));
				application.setGrade(rs.getInt("grade"));
				application.setJustification(rs.getString("justification"));
				application.setRequestDuration(rs.getString("requestDuration"));
				application.setRequestTime(rs.getTimestamp("requestTime"));
				application.setStanding(rs.getInt("standing"));
				application.setSupervisorId(rs.getInt("superId"));

				apps.add(application);
			}
			System.out.println(apps);
			LoggingUtil.logTrace("Applications under the userID " + supId + " retrieved.");
		} catch (SQLException e) {
			LoggingUtil.logError("Whoops, something went wrong!!!");
			e.printStackTrace();
		} finally {
			System.out.println(apps);
			closeResources();
		}
		System.out.println(apps);
		return apps;
	}
	
	public static void update(int appNum, int empNum, int decision, HttpServletRequest request) throws SQLException {
		// this function will return the grade that someone gets for a presentation
		// check to see if user status is above application's level
		// if so, display information to the user
		// update their information and pass it back to user, whether good or bad score
		HttpSession session = request.getSession();
		int status = (int) session.getAttribute("status");
		Application app = selectApplicationByAppID(appNum);
		Employee emp = EmpDao.selectEmployeeByIDNum(empNum);
		PreparedStatement ps = null;
		int acc = app.getAccNum(); //get their account so that we can update their info
		AccountDao dao = new AccountDao();
		Account a = dao.selectAccountByAccNum(acc); //get the specific account
		int stance = app.getStanding();
		int grade = app.getGrade();
		int id = app.getAppNum();
		while (app.getStanding() <= emp.getStatus()) { //first establish the employee is that proper level to approve the app
			if (app.getStanding() >= 5) {// level 5 is the state of being totally approved
				LoggingUtil.logInfo("Application " + app.getAppNum() + " has been totally approved.");
				Calculation.refunds(app.getExpense(), app.getGrade(), app.getEventType()); //calculate their refunds
				app.setJustification(app.getJustification() + "...Approved at level " + emp.getStatus());// give some feedback for account owner
				a.setAtHand(a.getAtHand() - (int)Calculation.refunds(app.getExpense(), app.getGrade(), app.getEventType())); // atHand is the amount of funding they can still apply for out of the maximum 1000
				a.setPending(a.getPending() - (int)app.getExpense()); //pending are the funds that haven't met full approval
				a.setApplied(a.getApplied() + (int)Calculation.refunds(app.getExpense(), app.getGrade(), app.getEventType())); //applied is just the aggregate amount they've applied for over time
				approvedApp(acc, app.getJustification());
				AccountDao.accountApproval(a.getAtHand(), a.getPending(), a.getApplied(), acc);
			}
			else {
				// make sure they dont get a chance to ask if the department numbers match up
				if (decision >= 3) {
					LoggingUtil.logInfo("Application " + app.getAppNum() + " has percolated to the next level of approval.");
					// return to user a message saying that their application has been approved
					app.setStanding(app.getStanding() + 1);
					app.setGrade(app.getGrade() + decision);
					raiseTheApp(app.getStanding(), app.getGrade(), appNum);
				}
				else if (decision < 3) {
					app.setStanding(1);
					app.setGrade(app.getGrade() + decision);
					app.setJustification(app.getJustification() + "...we are sorry to inform you that your application did not meet requirements and/or was unsatisfactory.");
					stance = app.getStanding();
					grade = app.getGrade();
					String just = app.getJustification();
					LoggingUtil.logInfo("Application " + app.getAppNum() + " denied."); 
					appDenied(stance, grade, just, appNum);
				}
			}
		}
	}
				
	public static boolean approvedApp(int acc, String justification) {
		boolean check = false;
		try {
					conn = ConnectionUtil.getConnection(); 
					String sql = "UPDATE APPLICATIONS SET JUSTIFICATION=? WHERE ACCNUM=?"; //lazy way of giving feedback
					ps = conn.prepareStatement(sql); // ^^^ I know I could have just made another field in the constructor
					ps.setString(1, justification);
					ps.setInt(2, acc); 
					ps.executeUpdate();
					if (ps.executeUpdate() != 0) {
						conn.commit();
						LoggingUtil.logTrace("An app under the account number " + acc + " has been raised");
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

	public static boolean raiseTheApp(int stance, int grade, int id) {
		boolean check = false;
		try {
						conn = ConnectionUtil.getConnection();
						String sql = "UPDATE APPLICATIONS SET APP_TYPE=?, GRADE=? WHERE APPNUM=?";
						ps = conn.prepareStatement(sql); //set application info
						ps.setInt(1, stance);
						ps.setInt(2, grade);
						ps.setInt(3, id);
						ps.executeUpdate();
						if (ps.executeUpdate() != 0) {
							conn.commit();
							LoggingUtil.logTrace("App #" + id + " has been raised");
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
					// create a button in the view to return the grade back to the user where their empid and username match up
					// alert those at the level of the appStanding
				}
	
	public static boolean appDenied(int stance, int grade, String just, int id) {
		boolean check = false;
					try {
						conn = ConnectionUtil.getConnection();
						String sql = "UPDATE APPLICATIONS SET APP_TYPE=?, GRADE=?, JUSTIFICATION=? WHERE APPNUM=?";
						ps = conn.prepareStatement(sql);
						ps.setInt(1, 1);
						ps.setInt(2, grade);
						ps.setString(3, just);
						ps.setInt(4, id);
						ps.executeUpdate();
						if (ps.executeUpdate() != 0) {
							conn.commit();
							LoggingUtil.logTrace("App #" + id + " has been denied");
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

	public boolean gradeCheck(int id, int stated, int recorded) {
		//recorded = someApplication's overall score
		Application a = selectApplicationByAppID(id);
		recorded = a.getGrade();
		if(stated == recorded) {
			return true;
		}
		else if (stated > recorded) {
			//return to the user that they need to stop messing around and put in their correct score, in those exact words
			return false;
		}
		else {
			//tell user that they will be fired by next week if they keep on lying like that
			return false;
		}
	}

//create a function for checking the grade versus the input they put in the form
//updates level to +1
	
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