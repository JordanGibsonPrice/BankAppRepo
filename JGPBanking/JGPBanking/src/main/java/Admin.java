package main.java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import org.apache.log4j.Logger;

import main.java.com.revature.driver.Welcome;

public class Admin extends Users {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8254726581772839470L;
	transient Scanner scan = new Scanner(System.in);
	public final static transient Logger logger = Logger.getLogger(Bank.class);

	public Admin(String firstname, String lastname, int SSN, Accounts account, String username, String password) {
		super(firstname, lastname, SSN, account, username, password);
		this.setStanding("admin");
	}
	public Admin() {
		super();
		this.setStanding("admin");
	}
	/**
	 * Show the admin control screen
	 */
	public void adminMenu() {
		logger.trace("Logging in as admin user");
		System.out.println("Checking verdicts? Yes/No? \n");
		String verdict = scan.next().toLowerCase();
		if (verdict.equalsIgnoreCase("yes")) {
			for (Users u : Bank.getBank().getAllUsers() ) {
				System.out.println(u.getFirstName() + "\t" + u.getLastName() + "\t" + u.getStanding() + "\t" + u.getAccount().getAccountNumber());
			} 
			for (People p : Bank.getBank().getAllPersons()) {
				System.out.println(p.getFirstName() + "\t" + p.getLastName() +  "\t" + p.getStanding() + "\t" + p.getAccount().getAccountNumber());
			}
			System.out.println("1. Promoting or Demoting?");
			System.out.println("2. (Un)lock accounts?");
			System.out.println("3. Checking waiting list?");
			System.out.println("4. Exit");
			int choice;
			choice = scan.nextInt();
			while (choice != 4 && choice >= 1 && choice <= 3) {
				System.out.println("Enter in the account number for the person of interest.");
			
				try {
					int selection = scan.nextInt();
					Users u = Bank.getBank().getUser(selection);
					People p = Bank.getBank().getPerson(selection);
					switch (choice) {
					case 1:
						promote(u);
						break;
					case 2:
						 locking(u);
						break;
					case 3:
						 joinTheClub(p);
						break;
					case 4:
						counterbalanceBalance(u.getAccount().getAccountNumber());
					case 5:
						Welcome w = new Welcome(); 
						w.getInput(); //check to see if this is what you really wanna do
						break;
					default:
						System.out.println("Invalid input, please enter a number between 1 and 5");
						break;
					}
				} catch (NumberFormatException e) {
					System.err.println("Err: please enter a number");
					adminMenu();
				} 
				
			}
		} else {
			System.out.println("Hmmmm... does not compute. Let's start from the beginning again.");
			new Welcome().logIn();;
		}
	}
	protected boolean promote(Users user) {
		System.out.println("Enter the username of the user you want to promote: \n");
		String promo = scan.nextLine();
		Boolean check = false;
		for (int i = 0; i < Bank.getBank().getAllUsers().size(); i++) {
			if(Bank.getBank().getAllUsers().get(i).getUsername().equals(promo)) {
				Employee e = new Employee(Bank.getBank().getAllUsers().get(i).getFirstName(), Bank.getBank().getAllUsers().get(i).getLastName(),
						Bank.getBank().getAllUsers().get(i).getSSN(), Bank.getBank().getAllUsers().get(i).getAccount(), 
								Bank.getBank().getAllUsers().get(i).getUsername(), Bank.getBank().getAllUsers().get(i).getPassword());
				logger.info("User " + e.getFirstName() + " is now an employee.");
				Bank.getBank();
				Bank.getEmpl().add(e);
				if (!check) {
					System.out.println("User found.");
				}
				try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.dat"))){
					
					Users human = (Users) ois.readObject();
					
					System.out.println("Found: " + human);
				} catch (FileNotFoundException a) {
					a.printStackTrace();
				} catch (IOException a) {
					a.printStackTrace();
				} catch (ClassNotFoundException a) {
					a.printStackTrace();
				}
				try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("employees.dat"))){
					oos.writeObject(e);
					System.out.println("Welcome to the club, we got jackets.");
				} catch (FileNotFoundException a) {
					a.printStackTrace();
				} catch (IOException a) {
					logger.warn("Congrats, you're our first REAL employee!");
					a.printStackTrace();
				} finally {
					System.out.println("Make this person an admin: Yes/No? \n");
					String s = scan.nextLine().toLowerCase();
					if (s.equals("yes")) {
						Admin a = new Admin(Bank.getBank().getAllUsers().get(i).getFirstName(), Bank.getBank().getAllUsers().get(i).getLastName(),
						Bank.getBank().getAllUsers().get(i).getSSN(), Bank.getBank().getAllUsers().get(i).getAccount(), 
								Bank.getBank().getAllUsers().get(i).getUsername(), Bank.getBank().getAllUsers().get(i).getPassword());
						logger.info("Employee " + a.getFirstName() + " is now an admin.");
						Bank.getBank();
						Bank.getA().add(a);
						try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("employees.dat"))){
							
							Employee human = (Employee) ois.readObject();
							
							System.out.println("Found: " + human);
						} catch (FileNotFoundException o) {
							o.printStackTrace();
						} catch (IOException o) {
							o.printStackTrace();
						} catch (ClassNotFoundException o) {
							o.printStackTrace();
						}
						try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("admin.dat"))){
							oos.writeObject(e);
							System.out.println("You're a big kid now.");
						} catch (FileNotFoundException o) {
							o.printStackTrace();
						} catch (IOException o) {
							o.printStackTrace();
						}
						check =  true;
					} else if (s.equals("no")) {
						check = true;
						adminMenu();
					} else {
						System.out.println("Please enter in a proper response next time. \n");
						adminMenu();
					}
				}
				logger.trace("User employed.");
				check = true;
				break;
			}
		}
		
		return check;
	}
	protected boolean locking(Users user) {
		System.out.println("Enter the username of the user you want to lock: \n");
		String punished = scan.nextLine();
		Boolean check = false;
		for (int i = 0; i < Bank.getBank().getAllUsers().size(); i++) {
			if(Bank.getBank().getAllUsers().get(i).getUsername().equals(punished)) {
				People person = new People(Bank.getBank().getAllUsers().get(i).getFirstName(), Bank.getBank().getAllUsers().get(i).getLastName(),
						Bank.getBank().getAllUsers().get(i).getSSN(), Bank.getBank().getAllUsers().get(i).getAccount(), 
								Bank.getBank().getAllUsers().get(i).getUsername(), Bank.getBank().getAllUsers().get(i).getPassword());
				if (!check) {
					System.out.println("Person locked.");
				}
				try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("people.dat"))){
					
					People human = (People) ois.readObject();
					
					System.out.println("Found: " + human);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} 
				logger.info("User " + person.getFirstName() + " has been Locked ");
				logger.trace("Account locked.");
				check = true;
				break;
			}
		}
		return check;
	}
	
	protected boolean joinTheClub(People person) { 
		System.out.println("Enter the username of the person you want to upgrade: \n");
		String promo = scan.next();
		Boolean check = false;
		for (int i = 0; i < Bank.getBank().getAllPersons().size(); i++) {
			if(Bank.getBank().getAllPersons().get(i).getUsername().equals(promo)) {
				Users u = new Users(Bank.getBank().getAllPersons().get(i).getFirstName(), Bank.getBank().getAllPersons().get(i).getLastName(),
						Bank.getBank().getAllPersons().get(i).getSSN(), Bank.getBank().getAllPersons().get(i).getAccount(), 
								Bank.getBank().getAllPersons().get(i).getUsername(), Bank.getBank().getAllPersons().get(i).getPassword());
				Bank.getBank().getAllUsers().add(u);
				if (!check) {
					System.out.println("Person found.");
				}
				try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("people.dat"))){
					
					People human = (People) ois.readObject();
					
					System.out.println("Found: " + human);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))){
					oos.writeObject(u);
					System.out.println("Visit you local branch to talk about why we are so much better than bitcoin.");
					adminMenu();
					break; //needs to break somewhere else
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					logger.warn("Congrats, you're our first REAL customer!");
					e.printStackTrace();
				}
				logger.trace("Account created.");
				check = true;
				break;
			}
		}
		return check;
	}
	/**
	 * list the accounts in the bank system
	 */
	void listAccounts() {
		int account = selectAccounts(); // need to create a selectAccount method
		while (account > 0) {
			System.out.println(Bank.getBank().getUser(account).getAccount());
		} if (account == 0 ) {
			System.out.println("No accounts in bank");
		}
	}
	public int selectAccounts() {
		int number = Bank.getBank().getAcc().size();
		return number;
	}
	public Admin getSpecificAdmin(String username) {
		Bank.getBank();
		for (Admin a : Bank.getA()) {
			if (a.getUsername().equals(username)) {
				return a;
			} else {
				System.err.println("User not found in system");
			}
		}
		return null;
	}
	public void counterbalanceBalance(int accountNum) { // method for dropping an account
		System.out.println("Are you sure this person deserves to be terminated? Yes/No");
		String judgement = scan.nextLine();
		if (judgement.equalsIgnoreCase("yes")) {
			if (Bank.getBank().getUsers(accountNum).getAccount().equals(null)) {
				System.out.println("There is no account of this number.");
				adminMenu();
			} else {
			Bank.getBank().getAllUsers().remove(Bank.getBank().getUser(accountNum));
			}
		}
	}
}