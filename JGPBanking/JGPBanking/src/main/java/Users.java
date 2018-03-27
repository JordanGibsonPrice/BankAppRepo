package main.java;


import java.io.Serializable;

import org.apache.log4j.Logger;

import main.java.com.revature.driver.LoggingUtil;

public class Users implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3851902425148245257L;
	public final static Logger logger = Logger.getLogger(Bank.class);

	private String firstName;
	private String lastName;
	private int SSN;
	private Accounts account;
	private String standing;
	private String username;
	private String password;


	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public int getSSN() {
		return SSN;
	}
	public String getStanding() {
		return standing;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setStanding(String string) {
		this.standing = string;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Accounts getAccount() {
		return account;
	}
	public void setAccount(Accounts account) {
		this.account = account;
	}
	
	public Users(String firstName, String lastName, int SSN, Accounts account, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.SSN = SSN;
		this.account = account;
		this.setStanding("active");
		this.username = username;
		this.password = password;
	}
	public Users(){
		this.firstName = "";
		this.lastName = "";
		this.SSN = 0;
		this.username = "";
		this.password = "";		
	}
	public Users getSpecificUser(String username) {
		for (Users u : Bank.getBank().getAllUsers()) {
			if (u.getUsername().equals(username)) {
				return u;
			} else {
				System.err.println("User not found in system");
			}
		}
		return null;
	}
	
	/**
	 * beauty behind a withdrawal
	 * 
	 * @param amount
	 */
	public void withdraw(double amount, Accounts account) {
		if (amount + 5 > this.account.getBalance()) {
			System.out.println("You have insufficient funds.");
			logger.error("Amount failed to withdraw");
			account.setBalance(account.getBalance() - (amount + 5));
		}
		account.setBalance(-amount);
		Transactions t = new Transactions(-amount);
		account.transactions.add(t);
		System.out.println("you have withdrawn $" + amount + " and incurred a fee of $5");
		logger.info("$" + amount + " has been withdrawn from account number " + account.getAccountNumber());
		System.out.println("you now have a balance of $" + account.getBalance() + ".00");
	}

	/**
	 * beauty behind a deposit
	 * 
	 *Run
Run Configurations
Classpath (tab)
User Entries
Advanced (button on the right)
Add Folders
then navigate to the folder that contains your log4j.properties file
Apply
Run
The error message should no longer appear.
	 * @param amount
	 */
	public void deposit(double amount, Accounts account) { //change math here in interest formula
		if (amount <= 0) { 
			System.out.println("You cannot deposit negative money");
		} 
		account.checkInterest(amount, account);
		account.setBalance(account.getBalance() + amount);
		Transactions t = new Transactions(amount);
		account.transactions.add(t);
		System.out.println("you have deposited $" + amount + " and have an interest rate of " + (this.account.getInterest()) + "%");
		LoggingUtil.logInfo("$" + amount + " has been deposited to account number " + account.getAccountNumber());
		System.out.println("you have a balance of $" + account.getBalance() + ".00");
	}
	public void tranfer(double amount, Accounts acc, Accounts account) {
		if (amount <= 0) {
			System.out.println("Transfers must be of valid funds greater thab $0");
		}
		else {
			Bank.getBank().getUser(acc.getAccountNumber()).getAccount().setBalance(acc.getBalance() + amount);
			Bank.getBank().getUser(account.getAccountNumber()).getAccount().setBalance(account.getBalance() - amount);
			System.out.println("Transfer made: \n Account " + acc.getAccountNumber() + " has a balance of " +
			acc.getBalance() + " and account " + account.getAccountNumber() + " has an balance of " + account.getBalance());
		}
	}
	
}
