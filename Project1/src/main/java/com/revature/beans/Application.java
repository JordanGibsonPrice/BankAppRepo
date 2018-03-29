package com.revature.beans;

import java.sql.Timestamp;
import java.util.Date;

public class Application {
	private int empId;
	private int accNum;
	private int appNum;
	private int deptNum;
	private String empFirst;
	private String empLast;
	private String email;
	private String description;
	private double expense;
	private String justification;
	Date eventDate;
	Timestamp requestTime;
	private int grade;
	private String eventLocus;
	private String requestDuration;
	private int eventType;
	private int standing;
	private int supervisorId;
	Date otherDate = new Date();
	public Application(int empId, int accNum, int appNum, int deptNum, String empFirst, String empLast, String email,
			String description, double expense, String justification, int grade, int eventType, String eventLocus,
			String requestDuration, int supervisorId, Date eventDate) {
		super();
		this.empId = empId;
		this.accNum = accNum;
		this.appNum = appNum;
		this.deptNum = deptNum;
		this.empFirst = empFirst;
		this.empLast = empLast;
		this.email = email;
		this.description = description;
		this.expense = expense;
		this.justification = justification;
		this.eventDate = eventDate;
		this.requestTime = new Timestamp(otherDate.getTime());
		this.grade = grade;
		this.eventType = eventType;
		this.eventLocus = eventLocus;
		this.requestDuration = requestDuration;
		this.setStanding(0);
		this.supervisorId = supervisorId;
	}
	public Application() {
		this.eventDate = new Date();
//		this.requestTime = new Timestamp(requestTime.getTime());
		}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getAppNum() {
		return appNum;
	}
	public void setAppNum(int appNum) {
		this.appNum = appNum;
	}
	public int getDeptNum() {
		return deptNum;
	}
	public void setDeptNum(int deptNum) {
		this.deptNum = deptNum;
	}
	public String getEmpFirst() {
		return empFirst;
	}
	public void setEmpFirst(String empFirst) {
		this.empFirst = empFirst;
	}
	public String getEmpLast() {
		return empLast;
	}
	public void setEmpLast(String empLast) {
		this.empLast = empLast;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getExpense() {
		return expense;
	}
	public void setExpense(double expense) {
		this.expense = expense;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Timestamp getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getEventLocus() {
		return eventLocus;
	}
	public void setEventLocus(String eventLocus) {
		this.eventLocus = eventLocus;
	}
	public String getRequestDuration() {
		return requestDuration;
	}
	public void setRequestDuration(String requestDuration) {
		this.requestDuration = requestDuration;
	}
	public int getAccNum() {
		return accNum;
	}
	public void setAccNum(int accNum) {
		this.accNum = accNum;
	}
	public int getStanding() {
		return standing;
	}
	public void setStanding(int standing) {
		this.standing = standing;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	public int getSupervisorId() {
		return supervisorId;
	}
	public void setSupervisorId(int supervisorId) {
		this.supervisorId = supervisorId;
	}
	
}
