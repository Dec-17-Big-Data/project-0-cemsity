package com.revature.bankApp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.bankModels.User;
import com.revature.service.NoSuchUserException;
import com.revature.service.UserNameExistsException;
import com.revature.service.UserService;

public class SuperUserManager {
	List<User> users;
	static UserService usrSer = UserService.getUserService();
	static SuperUserManager sum;
	private static Logger log = LogManager.getLogger(SuperUserManager.class);
	
	private SuperUserManager() {
		this.users = this.getAllUsers();
	}

	public static SuperUserManager getSuperUser() {
		if (sum == null) {
			sum = new SuperUserManager();
		}
		return sum;
	}
	
	public void endSuperUser() {
		sum = null;
	}
	public boolean login(Scanner in) {
		log.traceEntry();
		System.out.println("SuperUser login");
		System.out.print("Username: ");
		String username = in.nextLine();
		System.out.print("Password:");
		String password = in.nextLine();
		Properties props = new Properties();
	    try (InputStream inSt = new FileInputStream("src\\main\\resources\\connections.properties");) {
	    	props.load(inSt);
	    } catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
	    String propUsername = props.getProperty("superuser.username");
		String propPassword = props.getProperty("superuser.password");
		
		return log.traceExit(username.equals(propUsername) && password.equals(propPassword));
	}
	public List<User> getAllUsers(){
		log.traceEntry();
		return log.traceExit(usrSer.getAllUsers());
	}
	
	public void printUsers() {
		log.traceEntry();
		users = getAllUsers();
		System.out.printf("%-10s|%-15s|%-20s|%-15s|%-15s\n", "User ID", "User Name","Password", "First Name", "Last Name");
		for(User user : users) {
			System.out.printf("%-10d|%-15s|%-20s|%-15s|%-15s\n", user.getUserId(), user.getUserName(), user.getUserPassword(), user.getUserFirstName(), user.getUserLastName());
		}
		log.traceExit();
	}
	public void printUser(User user) {
		log.traceEntry();
		System.out.printf("%-10s|%-15s|%-20s|%-15s|%-15s\n", "User ID", "User Name","Password", "First Name", "Last Name");
		System.out.printf("%-10d|%-15s|%-20s|%-15s|%-15s\n", user.getUserId(), user.getUserName(), user.getUserPassword(), user.getUserFirstName(), user.getUserLastName());
		log.traceExit();
	}
	
	public void updateUser(Scanner in) {
		log.traceEntry();
		Map<Integer, User> mapUsr = listToMap(users);
		
		User user = null;
		this.printUsers();
		user: while(true) {
			System.out.println("Plese choose a User to Edit. (Choose by User ID)");
			int choice = 0;
			try {
				choice = in.nextInt();
			} catch (Exception e) {
				log.error(e);
				System.out.println("Please select a vaild User ID");
				continue;
			}
			user = mapUsr.get(choice);
			if (user == null) {
				System.out.println("Please select a vaild User ID");
				continue;
			}		
			printUser(user);
			question: while(true) {
				System.out.println("Is this the right user? ([Y]es or [N]o)");
				String rightUsr = in.nextLine().toLowerCase().trim();
				if (rightUsr.equals("y")){
					break user;
				} else if (rightUsr.equals("n")){
					break question;
				} else {
					System.out.println("Please answer with a Yes or No.");
				}
			}
			
		}
		edit: while(true) {
			System.out.println("Please edit the fields you want. Leave blank to not change");
			System.out.println("Username: " + user.getUserName());
			String usrName = in.nextLine();
			if (usrName.equals("")) {
				usrName = user.getUserName();
			}
			System.out.println("Password: " + user.getUserPassword());
			String password = in.nextLine();
			if (password.equals("")) {
				password = user.getUserPassword();
			}
			System.out.println("First Name: " + user.getUserFirstName());
			String firstName = in.nextLine();
			if (firstName.equals("")) {
				firstName = user.getUserFirstName();
			}
			System.out.println("Last Name: " + user.getUserLastName());
			String lastName = in.nextLine();
			if (lastName.equals("")) {
				lastName = user.getUserLastName();
			}
			
			System.out.println("Here are the new User Fields");
			System.out.println("Username: " + usrName);
			System.out.println("Password: " + password);
			System.out.println("First Name: " + firstName);
			System.out.println("Last Name: " + lastName);
			question: while(true) {
				System.out.println("Are these changes correct? (Yes or No)");
				String rightUsr = in.nextLine().toLowerCase().trim().substring(0, 1);
				if (rightUsr.equals("y")){
					user.setUserName(usrName);
					user.setUserPassword(password);
					user.setUserFirstName(firstName);
					user.setUserLastName(lastName);
					try {
						usrSer.updateUser(user);
						System.out.println("User has been updated succesfully");
						break edit;
					} catch(NoSuchUserException e) {
						System.out.println("No Such User Exists");
					}
				} else if (rightUsr.equals("n")){
					break question;
				} else {
					System.out.println("Please answer with a Yes or No.");
				}
			}
			this.users = this.getAllUsers();
		}
		log.traceExit();
	}
	
	public void createUser(Scanner in) {
		log.traceEntry();
		System.out.println("Creating a New User:");
		System.out.print("Username   : ");
		String userName = in.nextLine();
		System.out.print("Password   : ");
		String password = in.nextLine();
		System.out.print("First Name : ");
		String firstName = in.nextLine();
		System.out.print("Last Name  : ");
		String lastName = in.nextLine();
		
		try {
			usrSer.newUser(userName, password, firstName, lastName);
			System.out.println("New User Created Successfully");
		} catch (UserNameExistsException e) {
			System.out.println("A user with that username already exists");
		} catch (NoSuchUserException e) {
			System.out.println("No such user exists");
		}
		this.users = this.getAllUsers();
		log.traceExit();
	}
	
	public void deleteUser(Scanner in) {
		log.traceEntry();
		Map<Integer, User> mapUsr = listToMap(users);
		
		User user = null;
		this.printUsers();
		user: while(true) {
			System.out.println("Plese choose a User to Delete. (Choose by User ID)");
			int choice = 0;
			try {
				choice = in.nextInt();
			} catch (Exception e) {
				log.error(e);
				System.out.println("Please select a vaild User ID");
				continue;
			}
			user = mapUsr.get(choice);
			if (user == null) {
				System.out.println("Please select a vaild User ID");
				continue;
			}		
			printUser(user);
			question: while(true) {
				System.out.println("Is this the right user? ([Y]es or [N]o)");
				String rightUsr = in.nextLine().toLowerCase().trim();
				if (rightUsr.equals("y")){
					break user;
				} else if (rightUsr.equals("n")){
					break question;
				} else {
					System.out.println("Please answer with a Yes or No.");
				}
			}
		}
		while (true) {
			System.out.println("To confirm the deletion of this user please type: DELETE USER or CANCEL to cancel");
			String confirm = in.nextLine();
			if (confirm.equals("DELETE USER")) {
				boolean worked = usrSer.deleteUser(user);
				if (worked) {
					System.out.println("Succesfully delete user.");
					break;
				} else {
					System.out.println("Delete user Failed.");
					break;
				}
			} else if (confirm.equals("CANCEL")) {
				System.out.println("Canceling");
				break;
			} else {
				continue;
			}
		}
		this.users = this.getAllUsers();
		log.traceExit();
	}
	
	
	
	public Map<Integer, User> listToMap(List<User> users) {
    	log.traceEntry();
    	Map<Integer, User> mapUsr = new HashMap<Integer, User>();
    	for(User user:users) {
    		mapUsr.put(user.getUserId(), user);
    	}
    	return log.traceExit(mapUsr);
	}
	
}
