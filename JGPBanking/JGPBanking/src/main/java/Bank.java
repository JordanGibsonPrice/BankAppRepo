package main.java;


import java.util.ArrayList;

import main.java.com.revature.driver.Welcome;
import main.java.com.revature.driver.SerializingFile;

public class Bank {
	public static Bank bank;
	private static ArrayList<Users> users = new ArrayList<Users>();
	private static ArrayList<People> persons = new ArrayList<People>();
	private static ArrayList<Admin> a = new ArrayList<Admin>();
	private static ArrayList<Transactions> t = new ArrayList<Transactions>();
	private static ArrayList<Accounts> acc = new ArrayList<Accounts>();
	private static ArrayList<Employee> empl = new ArrayList<Employee>();
	Accounts ac = new Accounts("checking", 10000000);
	Accounts ac2 = new Accounts(5000, "savings");
	Accounts ac3 = new Accounts();
	Admin ad = new Admin("Admin", "Admin", 123456789, ac, "admin", "istrator");
	Employee e = new Employee("Emp", "Imp", 657483901, ac2, "em", "ployee");
	Users u = new Users("U", "sir", 4991, ac3, "us", "ers");

	public static ArrayList<Employee> getEmpl() {
		if (empl.equals(null)) {

		}
		return empl;
	}

	public ArrayList<Accounts> getAcc() {
		return acc;
	}
	

	public static ArrayList<Admin> getA() {
		return a;
	}

	public Transactions getTrans(int account) {
		return Bank.t.get(account);
	}

	public static ArrayList<Users> getUsers() {
		return users;
	}

	public static ArrayList<People> getPersons() {
		return persons;
	}

	ArrayList<People> getPeople() {
		return persons;
	}

	Bank() {
		users.add(ad);
		users.add(e);
		users.add(u);
		a.add(ad);
		empl.add(e);
	}

	public static Bank getBank() { 
		if (bank == null) {
			bank = new Bank();
			SerializingFile sf = new SerializingFile(); //do I need a new file
			sf.loadAccounts();
			sf.loadAdmins();
			sf.loadEmployees();
			sf.loadPeople();
			sf.loadTransactions();
			sf.loadUsers();
		}
		return bank;
	}

	public void addUser(Users user) {
		users.add(user);
		
	}
	
	public Users getUser(int account) {
		for (Users u : users) {
			if (u.getAccount().getAccountNumber() == account) {
				return u;
			}
		} return null; //FIX
	}

	public Users getUsers(int accountNum) {
		return Bank.users.get(accountNum);
	}
	
	public ArrayList<Users> getAllUsers() {
		return users;
	}

	public ArrayList<People> getAllPersons() {
		return persons;
	}

	public void addPerson(People person) {
		if (persons.isEmpty()) {
			System.err.println("Nothing is there");
		}
		persons.add(person);
		new Welcome().getInput();
	}

	public People getPerson(int account) {
		for ( People p : persons) {
			if (p.getAccount().getAccountNumber() == account) {
				return p;
			} 
		} return null; //FIX
	}



	public ArrayList<Transactions> getT() {
		return t;
	}

	public static void setT(ArrayList<Transactions> t) {
		Bank.t = t;
	}
	
	public void addTransaction(Transactions trans) {
		t.add(trans);
	}
	
	public static void setBank(Bank bank) {
		Bank.bank = bank;
	}
	
	
	public static void setEmpl(ArrayList<Employee> empl) {
		Bank.empl = empl;
	}

	public static void setAcc(ArrayList<Accounts> acc) {
		Bank.acc = acc;
	}
	
	public static void setA(ArrayList<Admin> a) {
		Bank.a = a;
	}
	
	public static void setPersons(ArrayList<People> persons) {
		Bank.persons = persons;
	}
	
	public static void setUsers(ArrayList<Users> users) {
		Bank.users = users;
	}
	
	public void addAccount(Accounts count) {
		acc.add(count);
	}
	
}
