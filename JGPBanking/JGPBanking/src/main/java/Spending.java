package main.java;

public class Spending extends Accounts{
/**
	 * 
	 */
private static final long serialVersionUID = 7342376143859503248L;
public Spending(String checking, double initDepo){
	super(checking, initDepo);	
	}
public Spending() {
	super();
}
/**
 * format the account info
 */
@Override
public String toString() {
	return "Account Type: " + this.getAccountNumber() + "\n" + "Balance: "
			+ this.getBalance() + "\n" + "Interest Rate: " 
			+ this.getBalance() + "%\n";
	}
}