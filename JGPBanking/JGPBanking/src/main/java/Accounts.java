package main.java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ListIterator;

public class Accounts implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2821513904830034627L;
	private double balance = 0;
	private double interest;
	private int accountNumber;
	private static int numberOfAccounts = 1000000;
	private String type;
	public ArrayList<Transactions> transactions; //figure this out

	public Accounts(String checking, double amount) {
		this.setType(checking);
		this.balance += amount;
		accountNumber = numberOfAccounts++;
		transactions = new ArrayList<Transactions>();
	}
	public Accounts(double amount, String savings) { 
		this.setType(savings);
		this.balance += amount;
		accountNumber = numberOfAccounts++;
		transactions = new ArrayList<Transactions>();
	}
	public Accounts() {
		this.balance = 0;
		this.accountNumber = 0;
		this.interest = 0;
		this.type = "";
		
	}

	public int getBalance() {
		return (int) balance;
	}
	public void setBalance(double balance) {
		this.balance += balance;
	}
	public double getInterest() {
		return interest * 100;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAccountNumber() {
		return accountNumber;
	}

	/**
	 * How much interest does the account accrue
	 * 
	 * @param amount
	 */
	public void checkInterest(double amount, Accounts account) {
		if (account.getBalance() + amount > 10000) {
			account.interest = 0.05;
		} else {
			account.interest = 0.02;
		} 
	}
	public double getBalances() {
		double balance = 0;
		for (Transactions t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	} 
	public void printTransaction() {
		// TODO Auto-generated method stub
		int t;
		System.out.printf("\n Transaction history for account %s \n", this.accountNumber);
		{
			for (t = this.transactions.size() - 1; t >= 0; t--)
			{
				System.out.println(this.transactions.get(t).getSummary());
			}
		}
	}
	public void printTransactions() {
		ListIterator<Transactions> LI = transactions.listIterator();
		while (LI.hasNext()) {
			try {
				System.out.println(LI.next().toString());
			} catch (NullPointerException n) {
				System.out.println(n.getMessage());
			}
		}
	}

	@Override
	public String toString() {
		return "Account [balance=" + balance + ", interest=" + interest + ", Account Number=" + accountNumber + "]";
	}
}

