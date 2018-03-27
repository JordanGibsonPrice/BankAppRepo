package main.java;

import java.io.Serializable;
import java.util.Date;

public class Transactions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4984024502649029224L;
	private double amount;
	private Date timestamp;
	private Accounts account;



	/**
	 * create transaction
	 * 
	 * @param amount
	 * @param account
	 */
	public Transactions(double amount) {
		this.amount = amount;
		this.timestamp = new Date();
	}
	public Transactions() {
		this.amount = 0;
		this.timestamp = new Date();
	}
	public double getAmount() {
		return this.amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * Get transaction summary
	 * 
	 * @return
	 */
	public String getSummary() {
		if (this.amount >= 0) {
			return String.format("%s : $%.02f : %s", this.timestamp.toString(), this.amount);
		} else {
			return String.format("%s : $%.02f : %s", this.timestamp.toString(), -this.amount);

		}
	}
	/**
	 * Add another transaction
	 * 
	 * @param amount
	 */
	public void addAnother(Accounts account, double amount) {
		Transactions trans = new Transactions(amount);
		account.transactions.add(trans);
	}
	@Override
	public String toString() {
		return "Trnasaction [amount=" + amount + ", date=" + timestamp + ", Account Number=" + account.getAccountNumber() + "]";
	}
}