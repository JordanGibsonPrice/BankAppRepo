package main.java.com.revature.driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import main.java.Accounts;
import main.java.Admin;
import main.java.Bank;
import main.java.Employee;
import main.java.People;
import main.java.Transactions;
import main.java.Users;

public class SerializingFile {
	public void loadAccounts() {
		if (main.java.Bank.bank.getAcc().isEmpty()) {
			ArrayList<Accounts> acco = new ArrayList<Accounts>();
			try {
				FileInputStream file = new FileInputStream("Accounts.txt");
				ObjectInputStream ois = new ObjectInputStream(file);
				acco = (ArrayList<Accounts>)ois.readObject();
				ois.close(); 
				file.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Bank.setAcc(acco);
		}
	}

	public void loadAdmins() {
		if (main.java.Bank.bank.getA().isEmpty()) {
			ArrayList<Admin> adm = new ArrayList<Admin>();
			try {
				FileInputStream file = new FileInputStream("Admin.txt");
				ObjectInputStream ois = new ObjectInputStream(file);
				adm = (ArrayList<Admin>)ois.readObject();
				ois.close();
				file.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Bank.setA(adm);
		}
	}

	public void loadEmployees() {
		if (main.java.Bank.bank.getEmpl().isEmpty()) {
			ArrayList<Employee> emplo = new ArrayList<Employee>();
			try {
				FileInputStream file = new FileInputStream("Employee.txt");
				ObjectInputStream ois = new ObjectInputStream(file);
				emplo = (ArrayList<Employee>)ois.readObject();
				ois.close();
				file.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Bank.setEmpl(emplo);
		}
	}

	public void loadPeople() {
		if (main.java.Bank.bank.getAllPersons().isEmpty()) {
			ArrayList<People> ppl = new ArrayList<People>();
			try {
				FileInputStream file = new FileInputStream("People.txt");
				ObjectInputStream ois = new ObjectInputStream(file);
				ppl = (ArrayList<People>)ois.readObject();
				ois.close();
				file.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Bank.setPersons(ppl);
		}
	}

	public void loadTransactions() {
		if (main.java.Bank.bank.getT().isEmpty()) {
			ArrayList<Transactions> tr = new ArrayList<Transactions>();
			try {
				FileInputStream file = new FileInputStream("Trans.txt");
				ObjectInputStream ois = new ObjectInputStream(file);
				tr = (ArrayList<Transactions>)ois.readObject();
				ois.close();
				file.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Bank.setT(tr);
		}
	}

	public void loadUsers() {
		if (main.java.Bank.bank.getAllUsers().isEmpty()) {
			ArrayList<Users> use = new ArrayList<Users>();
			try {
				FileInputStream file = new FileInputStream("Users.txt");
				ObjectInputStream ois = new ObjectInputStream(file);
				use = (ArrayList<Users>)ois.readObject();
				ois.close();
				file.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Bank.setUsers(use);
		}
	}
}
 