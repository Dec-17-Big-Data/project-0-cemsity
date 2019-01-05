package com.revature.bankApp;

import java.util.Scanner;

import com.revature.bankModels.User;
import com.revature.service.UserService;


public class App 
{
	UserService us = UserService.getUserService();
	User user;
	//AccountService as = AccoutnService.getAccountService();
	private App () {}
    public static void main( String[] args )
    {
    	
    	App app = new App();
    	Scanner in = new Scanner(System.in);
    	
    	
    	//app.signUp(in);
    	   	
      

    }
    public String greeting(Scanner in) {
    	System.out.println("Wecome to JBDC Bank.");
    	System.out.println("\n\nSelect your options:");
    	System.out.println("1. Login");
    	System.out.println("2. Sign Up");
    	System.out.print(":");
    	return in.nextLine();
    } 
    public void login(Scanner in) {
    	String userName, password;
    	
    	System.out.print("Username: ");
    	userName = in.nextLine();
    	System.out.print("Password: ");
    	password = in.nextLine();
    	
    	user = us.loginUser(userName, password);
    	
    	
    }
    
    public void  signUp(Scanner in) {
    	String userName, password, passVer, firstName, lastName;
    	boolean passNotEql;
    	System.out.print("First Name: ");
    	firstName = in.nextLine();
    	System.out.print("Last Name: ");
    	lastName = in.nextLine();
    	System.out.print("Username: ");
    	userName = in.nextLine();
    	do {
    		System.out.print("Password: ");
    		password = in.nextLine();
    		System.out.print("Verify Password: ");
    		passVer = in.nextLine();
    		passNotEql = !password.equals(passVer);
    	} while(passNotEql);
    	
    	try {
    		user = us.newUser(userName, password, firstName, lastName);
    	} catch (Exception e){
    		System.out.println("FAILED");
    	}
    	System.out.println("SIGNED UP!");
    	
    	
    		
    	  	
    	
    }
}
