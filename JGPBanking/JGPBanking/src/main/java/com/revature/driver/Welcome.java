package main.java.com.revature.driver;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

import main.java.Accounts;
import main.java.Admin;
import main.java.Bank;
import main.java.Employee;
import main.java.People;
import main.java.Reserve;
import main.java.Spending;
import main.java.Users;

public class Welcome {
	Scanner scan = new Scanner(System.in);
	boolean exit;
	static ObjectInputStream ois;
	public static final String FILE = "accounts.ser";
	public static ArrayList<Accounts> accounts;
	public final static Logger logger = Logger.getLogger(Bank.class); 
	
	
	public static void main(String args[]) {
		Welcome w = new Welcome();
		w.mainMenu();
	}
	
	/**
	 * Getting started! We have to know what the user wants to do, so we get 
	 * that input and basically put that in a switch statement
	 * 
	 */
	public void mainMenu() {
		while(!exit) {
			printHeader();
			int choice = getInput();
			switchScreen(choice); 
		}
	}
	
	/**
	 * run a different method depending on choice
	 * @param choice
	 */
	private void switchScreen(int choice) {
		switch (choice) {
		case 1: newAccount(); break;
		case 2: logIn(); break;
		case 3: 	System.out.println("Thank you for banking with us."); 
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Accounts.txt"))){
			oos.writeObject(Bank.getBank().getAcc());
			System.out.println("Please wait for your application to be to be processed.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException i) {
			logger.warn("Congrats, you're our first customer!");
			i.printStackTrace();
		}
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Admin.txt"))){
			Bank.getBank();
			oos.writeObject(Bank.getA());
			System.out.println("Please wait for your application to be to be processed.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException i) {
			logger.warn("Congrats, you're our first customer!");
			i.printStackTrace();
		}
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Employee.txt"))){
			Bank.getBank();
			oos.writeObject(Bank.getEmpl());
			System.out.println("Please wait for your application to be to be processed.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException i) {
			logger.warn("Congrats, you're our first customer!");
			i.printStackTrace();
		}
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("People.txt"))){
			oos.writeObject(Bank.getBank().getAllPersons());
			System.out.println("Please wait for your application to be to be processed.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException i) {
			logger.warn("Congrats, you're our first customer!");
			i.printStackTrace();
		}
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Trans.txt"))){
			oos.writeObject(Bank.getBank().getT());
			System.out.println("Please wait for your application to be to be processed.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException i) {
			logger.warn("Congrats, you're our first customer!");
			i.printStackTrace();
		}
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Users.txt"))){
			oos.writeObject(Bank.getBank().getAllUsers());
			System.out.println("Please wait for your application to be to be processed.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException i) {
			logger.warn("Congrats, you're our first customer!");
			i.printStackTrace();
		}
		System.exit(0); 
		default: System.out.println("Invalid input, please enter a number between 1-3.");
		getInput();
		
		}
	}
	
	/**
	 * sexy header
	 */
	private void printHeader() {
		System.out.println("+-------------------------------------------+");
		System.out.println("|               Welcome to                  |");
		System.out.println("|               JGP Bank!                   |");
		System.out.println("+-------------------------------------------+");
	}
	
	/**
	 * Initial direction for the customer. They are either new or revisiting,
	 * so this is simple. Validation check to make sure that the input is valid.
	 * @return
	 */
	public int getInput() {
		int choice = -1;
		do {
			System.out.println("Please make a selection: ");
			System.out.println("1. Make a new account");
			System.out.println("2. Login");
			System.out.println("3. Exit");

			try {
				choice = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid choice, please try again");
				mainMenu();
			}
			if (choice < 1 | choice > 4) {
				System.out.println("Invalid input, choose a number between 1-3");
				mainMenu();
			}
		} while (choice < 1 | choice > 4);
		return choice;
	}
	
	/**
	 * create a new account
	 */
	private void newAccount() {
		String firstName, lastName, accountType = "";
		int ssn = 0;
		Double initDepo = 0.0;
		boolean valid = false;
		while (!valid) {
			System.out.println("Please enter a type of account? Checking or savings?");
			accountType = scan.nextLine();
			if (accountType.equalsIgnoreCase("checking") || accountType.equalsIgnoreCase("savings")) {
				valid = true;
			} else {
				System.out.println("Invalid choice, please enter checking or savings.");
				newAccount();
			}
		}
		System.out.println("Please enter first name: ");
		firstName = scan.nextLine();
		System.out.println("Please enter last name: ");
		lastName = scan.nextLine();
		System.out.println("Please enter SSN: ");
		try {
			ssn = Integer.parseInt(scan.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Please enter in a number.");
			newAccount();
		} 
		System.out.println("Please enter a username: ");
		String username = scan.nextLine();
		if(nameTaken(username)) {
			do { System.out.println("Username has been taken, please enter in another name.");
			username = scan.nextLine();
			} while(nameTaken(username));
		}
		System.out.println("Please enter a password: ");
		String password = scan.nextLine();
		valid = false;
		while (!valid) {
			System.out.println("Please enter an initial deposit: ");
			try {
				initDepo = Double.parseDouble(scan.nextLine());
			} catch (NumberFormatException e) {
				System.err.println("Err: deposit must be a number");
			}
			if (accountType.equalsIgnoreCase("checking")) {
				if (initDepo < 100) {
					System.err.println("Checking accounts require a minimum of $100 to open");
				} else {
					valid = true;
					Spending spend = new Spending(accountType.toLowerCase(), initDepo);
					try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("accounts.dat"))){
						oos.writeObject(spend);
						System.out.println("Spending account application recieved.");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException i) {
						logger.warn("Looks like there's no previous accounts.");
								
						i.printStackTrace();
					}
					Bank.getBank().addAccount(spend);
					People person = new People(firstName, lastName, ssn, spend, username, password);
					try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("people.dat"))){
						oos.writeObject(person);
						System.out.println("Please wait for your application to be to be processed.");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException i) {
						logger.warn("Congrats, you're our first customer!");
						i.printStackTrace();
					}
					logger.trace("Pending account created.");
					Bank.getBank().addPerson(person);
				}
			} else if (accountType.equalsIgnoreCase("savings")) {
				if (initDepo < 100) {
					System.err.println("Savings accounts require a minimum of $100 to open");
				} else {
					valid = true;
					Reserve rere = new Reserve(initDepo, accountType.toLowerCase());
					try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("accounts.dat"))){
						oos.writeObject(rere);
						System.out.println("Reserve account application recieved. \n Please wait for your application to be to be processed.");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException i) {
						logger.warn("Looks like there's no previous accounts.");
						i.printStackTrace();
					}
					Bank.getBank().addAccount(rere);
					People person = new People(firstName, lastName, ssn, rere, username, password);
					try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("people.dat"))){
						oos.writeObject(person);
						System.out.println("Please wait for your application to be to be processed.");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException i) {
						logger.warn("Congrats, you're our first customer!");
						i.printStackTrace();
					}
					logger.trace("Pending account created.");
					Bank.getBank().addPerson(person);
				}
			}
		}
	}
	
	public boolean nameTaken(String username) {
		for (Users u : Bank.getBank().getAllUsers()) {
			if (u.getUsername().equalsIgnoreCase(username)) {
				System.out.println("Username has been taken.");
				return true;
			}
		}
		return false;
	}
	
	public void logIn() {
		System.out.println("What is your username?");
		String username = scan.nextLine();
		System.out.println("What is your passward?");
		String password = scan.nextLine();
		for (Users u : Bank.getBank().getAllUsers()) {
			if (u.getUsername().equals(username)) {
				if (u.getPassword().equals(password)) {
					if (u.getStanding().equals("standby") || u.getStanding().equals("locked")) {
						System.out.println("No available account details. Please try again later.");
						getInput();
					} else {
						Users us = new Users();
						u = us.getSpecificUser(username); 
						int choice = transactions();
						switchScreen2(choice, u.getAccount());
					}
				} else {
					System.out.println("Invalid login combination, please try again.");
					logIn();
				}
			}
		}
		System.out.println("User does not exist!");
	}
	
	public int transactions() {
		int choice = -1;
		do {
			System.out.println("Please make a selection: ");
			System.out.println("1. Deposit");
			System.out.println("2. Withdraw");
			System.out.println("3. Employee Login");
			System.out.println("4. Admin Login");
			System.out.println("5. Exit");

			try {
				choice = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid choice, please try again");
			}
			if (choice < 1 | choice > 6) {
				System.out.println("Invalid input, choose a number: " + choice);
			}
		} while (choice < 1 | choice > 6);
		return choice;
	}
	
	private void switchScreen2(int choice, Accounts account) {
		switch (choice) {
		case 1: System.out.println("How much would you like to deposit?");
		double amount = scan.nextInt();
		try {
			Bank.getBank().getUser(account.getAccountNumber()).deposit(amount, account);
		} catch (NumberFormatException e) {
			System.err.println("Please enter a number.");
			switchScreen2(choice, account);
		} break;
		case 2: System.out.println("How much would you like to withdraw?");
		double amount2 = scan.nextInt();
		try {
			Bank.getBank().getUser(account.getAccountNumber()).withdraw(amount2, account);
		} catch (NumberFormatException e) {
			System.err.println("Please enter a number.");
			switchScreen2(choice, account);
		} break;
		case 3: 	EmpLogin();
			break;
		case 4: AdminLogin(account.getAccountNumber());
			break;
		case 5: getInput();
		default: System.out.println("Invalid input, please enter a number between 1-3.");
		transactions();
		
		}
	}
	
	public void AdminLogin(int accountNum) { 
		Bank.getBank();
		for (Admin a : Bank.getA()) {
			if (a.getAccount().getAccountNumber() == accountNum) {
					if (a.getStanding().equals("active") || a.getStanding().equals("emp")) {
						System.out.println("No admin access for this account.");
						transactions(); //takes them back to transactions menu
					} else { 
						a.adminMenu();
					}
				} else {
					System.out.println("Invalid login combination, please try again.");
					logIn();
				}
			} 		
		System.out.println("User does not exist!");
		}
	
	public void EmpLogin() {
		System.out.println("What is your username?");
		String username = scan.nextLine();
		System.out.println("What is your passward?");
		String password = scan.nextLine();
		Bank.getBank();
		for (Employee e : Bank.getEmpl()) {
			if (e.getUsername().equals(username)) {
				if (e.getPassword().equals(password)) {
					if (e.getStanding().equals("active") || e.getStanding().equals("admin")) {
						System.out.println("This account is not for a regular employee.");
						transactions(); // takes them back to transactions menu
					} else {
						Employee emp = new Employee();
						e = emp.getSpecificEmp(username); 
						e.empMenu();
					}
				} else {
					System.out.println("Invalid login combination, please try again.");
					logIn();
				}
			}
		}
		System.out.println("User does not exist!");
	}
	
}
