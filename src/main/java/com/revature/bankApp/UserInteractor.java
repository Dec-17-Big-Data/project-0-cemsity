package com.revature.bankApp;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.bankModels.Account;
import com.revature.bankModels.User;
import com.revature.service.AccountService;
import com.revature.service.BalanceException;
import com.revature.service.InsufficientBalanceException;
import com.revature.service.NoSuchUserException;
import com.revature.service.UserNameExistsException;
import com.revature.service.UserService;

public class UserInteractor {
	private static UserInteractor usrInter; 
	private UserService usrSer = UserService.getUserService();
	private AccountService accSer = AccountService.getAccountService();
	private User user;
	private static Logger log = LogManager.getLogger(UserInteractor.class);
	
	private UserInteractor() {}
	
	public static UserInteractor getUserInteractor() {
		if (usrInter == null) {
			usrInter = new UserInteractor();
		}
		return usrInter;
	}
	public void login(Scanner in) {
		log.traceEntry();
    	String userName, password;
    	while(true) {
	    	System.out.print("Username: ");
	    	userName = in.nextLine();
	    	System.out.print("Password: ");
	    	password = in.nextLine();
	    	try {
	    		this.user = usrSer.loginUser(userName, password);
	    		break;
	    	} catch (NoSuchUserException e) {
	    		System.out.println("User Does Not Exist");
	    		continue;
	    	}
	    	
    	}
    	this.user.setAccounts(accSer.getAccountsbyUserID(user.getUserId()));
    	log.traceExit();
    }
	public void signOut() {
		this.user = null;
	}
	public void  signUp(Scanner in) {
		log.traceEntry();
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
    		user = usrSer.newUser(userName, password, firstName, lastName);
    	} catch (UserNameExistsException e) {
    		log.catching(e);
    		System.out.println("The Username already exists, please try again.");
    		log.traceExit();
    	} 
    	catch (Exception e){
    		System.out.println("Failed Please Try Again ");
    		log.catching(e);
    		log.error(e);
    		log.traceExit();
    	}
    	System.out.println("SIGNED UP!");
    	log.traceExit();
    }
	public User getUser() {
		return user;
	}
	
	public void printUserAccounts() {
		log.traceEntry();
    	List<Account> accs = getUsersAccount(this.user.getUserId());
    	
    	System.out.println("Your accounts:");
    	System.out.printf(" %-10s | %-12s | %-15s\n", "Account ID", "Type", "Current Amount");
    	for(Account acc:accs) {
    		System.out.printf("%-10d | %-12s | %-15d\n", acc.getAccountId(), acc.getType(), acc.getAmount());
    	}
    	log.traceExit();
    }
	public List<Account> getUsersAccount(Integer userId){
		log.traceEntry();
    	List<Account> listAcc = null;
    	try {
    		 listAcc = accSer.getAccountsbyUserID(userId);
    		 this.user.setAccounts(listAcc);
    	} catch (Exception e) {
    		log.catching(e);
    		System.out.println("Unable to recover users account");
    	}
    	log.traceExit(listAcc);
    	return listAcc;
    }
	public boolean deposit(Scanner in) {
		log.traceEntry();
    	Map<Integer, Account> mapAcc = accMap();
    	
    	Integer accNum = 0;
    	int amount = 0;
    	int input = 0;
    	Account acc = null;
    	
    	while(true) {
	    	System.out.println("Select an Acount");
	    	try {
	    		 input = in.nextInt();
	    	} catch (InputMismatchException e ) {
	    		log.catching(e);
	    		System.out.println("Please use only numbers");
	    		continue;
	    	}
	    	if (input > 0) {
	    		accNum = input;
	    	} else {
	    		System.out.println("Please use only positive numbers");
	    		continue;
	    	}
	    	acc = mapAcc.get(accNum);
    		if(acc != null) {
    			break;
    		} else {
    			System.out.println("No Account with that number.");
    		}
    	}
    	while(true) {
	    	System.out.println("Choose an Amount");
	    	try {
	    		input = in.nextInt();
	    	} catch(InputMismatchException e) {
	    		System.out.println("Please use only numbers");
	    		continue;
	    	}
	    	if (input > 0) {
	    		amount = input;
	    		break;
	    	}
	    	System.out.println("Please use only positive numbers");
	    }
    	 
    		return accSer.deposit(acc, amount);
    }
	public boolean withdraw(Scanner in) {
		log.traceEntry();
    	Map<Integer, Account> mapAcc = accMap();
    	
    	Integer accNum = 0;
    	int amount = 0;
    	int input = 0;
    	Account acc = null;
    	
    	while(true) {
	    	System.out.println("Select an Account");
	    	try {
	    		 input = in.nextInt();
	    	} catch (InputMismatchException e ) {
	    		log.catching(e);
	    		System.out.println("Please use only numbers");
	    		continue;
	    	}
	    	if (input > 0) {
	    		accNum = input;
	    		
	    	} else {
	    		System.out.println("Please use only positive numbers");
	    		continue;
	    	}
	    	acc = mapAcc.get(accNum);
    		if(acc != null) {
    			break;
    		} else {
    			System.out.println("No Account with that number.");
    		}
    	}
    	while(true) {
	    	System.out.println("Choose an Amount");
	    	try {
	    		input = in.nextInt();
	    	} catch(InputMismatchException e) {
	    		log.catching(e);
	    		System.out.println("Please use only numbers");
	    		continue;
	    	}
	    	if (input > 0) {
	    		amount = input;
	    		break;
	    	}
	    	System.out.println("Please use only positive numbers");
	    }
    	try {	
    		
    		boolean worked = accSer.withdraw(acc, amount);
    		if (worked) {
    			System.out.println("Successfully Withdrawn");
    		} else {
    			System.out.println("Withdraw Failed");
    		}
    		return log.traceExit(worked);
    	} catch (InsufficientBalanceException e) {
    		log.catching(e);
    		log.error("Insufficient Balance Exception", e );
    		System.out.println("The balance selected has insufficent balance");
    		log.traceExit(false);
    		return false;
    	}
    }
	
	public boolean transfer(Scanner in) {
		log.traceEntry();
    	Map<Integer, Account> mapAcc = accMap();
    	
    	Integer accNum1 = 0;
    	Integer accNum2 = 0;
    	int amount = 0;
    	int input = 0;
    	Account acc1 = null;
    	Account acc2 = null;
    	
    	while(true) {
	    	System.out.println("Select a Source Acount");
	    	try {
	    		 input = in.nextInt();
	    	} catch (InputMismatchException e ) {
	    		log.catching(e);
	    		System.out.println("Please use only numbers");
	    		continue;
	    	}
	    	if (input > 0) {
	    		accNum1 = input;
	    	} else {
	    		System.out.println("Please use only positive numbers");
	    		continue;
	    	}
	    	acc1 = mapAcc.get(accNum1);
    		if(acc1 != null) {
    			break;
    		} else {
    			System.out.println("No Account with that number.");
    		}
    	}
    	
    	while(true) {
	    	System.out.println("Select a Destination Acount");
	    	try {
	    		 input = in.nextInt();
	    	} catch (InputMismatchException e ) {
	    		log.catching(e);
	    		System.out.println("Please use only numbers");
	    		continue;
	    	}
	    	if (input > 0) {
	    		accNum2 = input;
	    	} else {
	    		System.out.println("Please use only positive numbers");
	    		continue;
	    	}
	    	acc2 = mapAcc.get(accNum2);
    		if(acc1 != null) {
    			break;
    		} else {
    			System.out.println("No Account with that number.");
    		}
    	}
    	
    	while(true) {
	    	System.out.println("Choose an Amount");
	    	try {
	    		 input = in.nextInt();
		   		
	    	} catch(InputMismatchException e) {
	    		log.catching(e);
	    		System.out.println("Please use only positive numbers");
	    		continue;
	    	}
	    	if (input > 0) {
	   			amount = input;
	   			break;
	   		}
	    	System.out.println("Please use only positive numbers");
    	}
    	try {	
    		boolean worked = accSer.transfer(acc1, acc2, amount);
    		if (worked) {
    			System.out.println("Transfer Successful");
    		} else {
    			System.out.println("Transfer Failed");
    		}
    		log.traceExit(worked);
    		return worked;
    	} catch (InsufficientBalanceException e) {
    		log.catching(e);
    		log.error("Insufficient Balance Exception" , e);
    		System.out.println("The balance selected has insufficent balance");
    		log.traceExit(false);
    		return false;
    	}	
    }
	public boolean newAccount(Scanner in){
		log.traceEntry();
		String type = null;
		
		System.out.println("New Account type");
		type = in.nextLine();
		
		boolean worked = accSer.newAccount(this.user.getUserId(), type);
		if (worked) {
			this.user.setAccounts(getUsersAccount(this.user.getUserId()));
			System.out.println("Account Created");
		} else {
			System.out.println("Account Creation Failed");
		}
		return log.traceExit(worked);
	}
	
	public boolean deleteAccount(Scanner in) {
		log.traceEntry();
		Map<Integer, Account> mapAcc = accMap();
		
		Integer accNum = 0;
    	int input = 0;
    	Account acc1 = null;
    	
    	while(true) {
	    	System.out.println("Select a Source Acount");
	    	try {
	    		 input = in.nextInt();
	    	} catch (InputMismatchException e ) {
	    		log.catching(e);
	    		System.out.println("Please use only numbers");
	    		continue;
	    	}
	    	if (input > 0) {
	    		accNum = input;
	    	} else {
	    		System.out.println("Please use only positive numbers");
	    		continue;
	    	}
	    	acc1 = mapAcc.get(accNum);
    		if(acc1 != null) {
    			break;
    		} else {
    			System.out.println("No Account with that number.");
    		}
    	}
    	try {
    		accSer.deleteAccount(acc1);
    		
    	} catch (BalanceException e) {
    		log.catching(e);
    		System.out.println("This acccount does not have a balance of 0");
    		return log.traceExit(false);
    	}
    	this.user.setAccounts(getUsersAccount(this.user.getUserId()));
    	System.out.println("Account Successfully Deleted");
		return log.traceExit(true);
	}
	
	
	
	
	
	
	
	
	
	public Map<Integer, Account> accMap() {
		log.traceEntry();
    	List<Account> accs = this.user.getAccounts();
    	Map<Integer, Account> mapAcc = new HashMap<Integer, Account>();
    	for(Account acc:accs) {
    		mapAcc.put(acc.getAccountId(), acc);
    	}
    	log.traceExit(mapAcc);
    	return mapAcc;
    }
	
}
