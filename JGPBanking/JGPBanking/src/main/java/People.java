package main.java;

public class People extends Users {
	/**
	 * 
	 */
	private static final long serialVersionUID = -110847395774127826L;
	String fName;
	String lName;
	int ssn;
	Accounts ac;
	String standing;
	String username;
	String password;
	public People(String firstName, String lastName, int SSN, Accounts account, String username, String password) {
		super(firstName, lastName, SSN, account, username, password);
		this.fName = firstName;
		this.lName = lastName;
		this.ssn = SSN;
		this.ac = account;
		this.standing = "standby";
		this.username = username;
		this.password = password;
	}
public People() {
		// TODO Auto-generated constructor stub
	super();
	}
	//	public Accounts getAccounts() { //tf
//		return account;
//	}
	public String toString() {
		return "People [firstname=" + fName + ", lastname=" + lName + ", SSN=" + ssn + ", account=" + ac + ", standing" + standing + ", username" + username + ", password" + password + "]";
	}
}