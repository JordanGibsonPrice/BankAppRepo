package com.revature.beans;

import java.util.ArrayList;

public class Account {
	private int accNum;
	private String username;
	private int atHand;
	private int applied;
	private int pending;
	private ArrayList<Application> apps;
	public Account(int accNum, String username, int grade, int applied, int pending){
		super();
		this.accNum = accNum;
		this.username = username;
		this.atHand = 1000-this.pending-this.applied;
		this.applied = applied;
		this.pending = pending;
		this.setApps(new ArrayList<Application>());
	}
	public Account(String username) {
		super();
		this.username = username;
		this.atHand = 1000;
		this.applied = 0;
		this.pending = 0;
		this.setApps(new ArrayList<Application>());
	}

	public Account() {
		super();
	}


	public int getAccNum() {
		return accNum;
	}

	public void setAccNum(int accNum) {
		this.accNum = accNum;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAtHand() {
		return atHand;
	}

	public void setAtHand(int atHand) {
		this.atHand = atHand;
	}

	public int getApplied() {
		return applied;
	}

	public void setApplied(int applied) {
		this.applied = applied;
	}

	public int getPending() {
		return pending;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	@Override
	public String toString() {
		return "Account [accNum=" + accNum + ", username=" + username + ", atHand=" + atHand
				+ ", applied=" + applied + ", pending=" + pending + "]";
	}
	public ArrayList<Application> getApps() {
		return apps;
	}
	public void setApps(ArrayList<Application> apps) {
		this.apps = apps;
	}
	

}