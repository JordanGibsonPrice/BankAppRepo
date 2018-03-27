package main.java;

public class Reserve extends Accounts{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6311005128755785224L;
	public Reserve(double initDepo, String savings){
	super(initDepo, savings);
}
	public Reserve() {
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
