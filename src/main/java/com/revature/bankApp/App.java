package com.revature.bankApp;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.revature.bankModels.Account;
import com.revature.bankModels.User;
import com.revature.service.AccountService;
import com.revature.service.BalanceException;
import com.revature.service.InsufficientBalanceException;
import com.revature.service.UserService;


public class App 
{
	UserService usrSer = UserService.getUserService();
	AccountService accSer = AccountService.getAccountService();
	User user;
	
	
	private App () {}
    public static void main( String[] args )
    {
    	
    	App app = new App();
    	Scanner in = new Scanner(System.in);
//    	app.greetingManager(in);
//    	app.userManager(in);
//    	
    	SuperUserManager sum = SuperUserManager.getSuperUser();
    	sum.printUsers();
    	sum.createUser(in);
    	sum.printUsers();
    	   	
    	in.close();

    }
    public String greeting(Scanner in) {
    	System.out.println("Wecome to JBDC Bank.");
    	System.out.println("\n\nSelect your options:");
    	System.out.println("(Please choose either the number, first letter or whole command");
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
    	
    	this.user = usrSer.loginUser(userName, password);
    	this.user.setAccounts(accSer.getAccountsbyUserID(user.getUserId()));
    	
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
    		user = usrSer.newUser(userName, password, firstName, lastName);
    	} //catch (AlreadyExistingUser e) {
    	//	
    	//} 
    	catch (Exception e){
    		System.out.println("Failed Please Try Again ");
    	}
    	System.out.println("SIGNED UP!");	
    }

    
    public void greetingManager(Scanner in) {
    	boolean notMade = true;
    	while(notMade) {
    		
    		String choice = greeting(in).toLowerCase().trim();
    	
    		switch(choice) {
    		case "1":
    		case "1.":
    		case "l": 
    		case "login":
    			notMade = false;
    			lineBreak(5);
    			login(in);
    			break;
    		case "2":
    		case "2.":	
    		case "s":
    		case "sign":
    		case "signup":
    		case "sign up":
    			notMade = false;
    			lineBreak(5);
    			signUp(in);
    			break;
    		default:
    			notValidRequest();
    			break;
    		}
    	}
    }
    
    public void userManager(Scanner in) {
    	boolean quitFlag = true;
    	while(quitFlag) {
    		String choice = displayAccountOptions(in).toLowerCase().trim();
    		switch (choice) {
	    		case "1":
	    		case "1.":
	    		case "v":
	    		case "view":
	    		case "view accounts":
	    		case "1. view accounts":
	    			printUserAccounts();
	    			enter(in);
	    			break;
	    		case "2":
	    		case "2.":
	    		case "m":
	    		case "manage":
	    		case "manage accounts":
	    		case "2. manage accounts":
	    			accountManager(in);
	    			
	    			break;
	    		case "3":
	    		case "3.":
	    		case "h":
	    		case "history":
	    		case "history of transactions":
	    		case "3. history of transactions":
	    			// display transaction history
	    			System.out.println("3");
	    			break;
	    		case "4":
	    		case "4.":
	    		case "u":
	    		case "update":
	    		case "update information":
	    		case "4. update information":
	    			//userUpdateManager 
	    			System.out.println("4");
	    			break;
	    		case "5":
	    		case "5.":
	    		case "s":
	    		case "sign":
	    		case "sign out":
	    		case "5. sign out":
	    			//sign user out
	    			System.out.println("5");
	    			quitFlag = true;
	    			break;
	    		default: 
	    			notValidRequest();
	    			break;
    		}
    	}
    }
    public void printUserAccounts() {
    	List<Account> accs = getUsersAccount(this.user.getUserId());
    	this.user.setAccounts(accs);
    	System.out.println("Your accounts:");
    	System.out.println(" Account Id |    1Type    |   Value");
    	for(Account acc:accs) {
    		System.out.print(whitespaceMaker(acc.getAccountId().toString(), 12));
    		System.out.print("|");
    		System.out.print(whitespaceMaker(acc.getType(), 12));
    		System.out.print("|");
    		System.out.print(whitespaceMaker(acc.getAmount().toString(), 12));
    		System.out.print("\n");
    	}
    }
    	
    	
    
    public String displayAccountOptions(Scanner in) {
    	lineBreak(5);
    	System.out.println("Welcome " + this.user.getUserFirstName() + " " + this.user.getUserLastName());
    	lineBreak(2);
		System.out.println("Select yout options:");
		System.out.println("(Please Please choose either the number, first letter or word of the command)");
		lineBreak(1);
		System.out.println("1. View Accounts");
		System.out.println("2. Manage Accounts");
		System.out.println("3. History of Transactions");
		System.out.println("4. Update Information");
		System.out.println("5. Sign Out");
		return in.nextLine();
    }
    public void accountManager(Scanner in) {
    	boolean quitFlag = true;
    	while(quitFlag) {
    		lineBreak(5);
    		String choice = accountOptions(in).toLowerCase().trim();
    		
    		switch(choice) {
    		case "1":
    		case "1.":
    		case "v":
    		case "view":
    		case "view accounts":
    		case "1. view accounts":
    			printUserAccounts();
    			enter(in);
    			break;
    		case "2":
    		case "2.":
    		case "d":
    		case "deposit":
    		case "2. deposit":
    			deposit(in);
    			enter(in);
    			break;
    		case "3":
    		case "3.":
    		case "w":
    		case "withdraw":
    		case "3. withdraw":
    			withdraw(in);
    			enter(in);
    			break;
    		case "4":
    		case "4.":
    		case "t":
    		case "transfer":
    		case "4. transfer":
    			 transfer(in);
    			 enter(in);
    			 break;
    		case "5":
    		case "5.":
    		case "n":
    		case "new":
    		case "new account":
    		case "5. new account":
    			newAccount(in);
    			enter(in);
    			break;
    		case "6":
    		case "6.":
    		case "delete":
    		case "delete account":
    		case "6. delete":
    			deleteAccount(in);
    			enter(in);
    			break;
    		case "7":
    		case "7.":
    		case "e":
    		case "exit":
    		case "7. exit":
    			quitFlag = false;
    			break;
    		default:
    			notValidRequest();
    			
    		
    		}
    		
    	}
    }
    public String accountOptions(Scanner in) {
    	lineBreak(5);
    	System.out.println("Select yout options:");
		System.out.println("(Please Please choose either the number, first letter or word of the command)");
		lineBreak(1);
		System.out.println("1. View Accounts");
		System.out.println("2. Deposit");
		System.out.println("3. Withdraw");
		System.out.println("4. Transfer");
		System.out.println("5. New Account");
		System.out.println("6. Delete Account");
		System.out.println("7. Exit ");
    	return in.nextLine();
    }

    public Map<Integer, Account> accMap() {
    	List<Account> accs = this.user.getAccounts();
    	Map<Integer, Account> mapAcc = new HashMap<Integer, Account>();
    	for(Account acc:accs) {
    		mapAcc.put(acc.getAccountId(), acc);
    	}
    	return mapAcc;
    }
    public boolean deposit(Scanner in) {
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
    	try {	
    		return accSer.withdraw(acc, amount);
    	} catch (InsufficientBalanceException e) {
    		System.out.println("The balance selected has insufficent balance");
    		return false;
    	}
    }
    
    	
	public boolean transfer(Scanner in) {
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
	    		System.out.println("Please use only numbers");
	    		continue;
	    	}
	    	if (input > 0) {
	    		accNum1 = input;
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
    		return accSer.transfer(acc1, acc2, amount);
    	} catch (InsufficientBalanceException e) {
    		System.out.println("The balance selected has insufficent balance");
    		return false;
    	}	
    }
    	
    
	public void newAccount(Scanner in){
		String type = null;
		
		System.out.println("New Account type");
		type = in.nextLine();
		
		boolean worked = accSer.newAccount(this.user.getUserId(), type);
		if (worked) {
			this.user.setAccounts(getUsersAccount(this.user.getUserId()));
		}
	}
	
	public void deleteAccount(Scanner in) {
		Map<Integer, Account> mapAcc = accMap();
		
		Integer accNum = 0;
    	int input = 0;
    	Account acc1 = null;
    	
    	while(true) {
	    	System.out.println("Select a Source Acount");
	    	try {
	    		 input = in.nextInt();
	    	} catch (InputMismatchException e ) {
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
    		System.out.println("This acccount does not have a balance of 0");
    	}
    	this.user.setAccounts(getUsersAccount(this.user.getUserId()));
		
	}
    	
    public List<Account> getUsersAccount(Integer userId){
    	List<Account> listAcc = null;
    	try {
    		 listAcc = accSer.getAccountsbyUserID(userId);
    	} catch (Exception e) {
    		System.out.println("Unable to recover users account");
    	}
    	return listAcc;
    }
    
    public String whitespaceMaker(String string, int toSize) {
    	int sizeStr = string.length();
    	int sizeRemain = toSize - sizeStr;
    	int front = sizeRemain/2;
    	int back = sizeRemain - front;
    	
    	StringBuffer sb = new StringBuffer();
    	for (int i = 0; i < front; ++i) {
    		sb.append(' ');
    	}
    	
    	sb.append(string);
    	
    	for (int i = 0; i < back; ++i) {
    		sb.append(' ');
    	}
    	
    	return sb.toString();
    	
    }
    
    public void lineBreak(int numBreak) {
    	for(int i = 0; i < numBreak; ++i) {
    		System.out.print("\n");
    	}
    }
    public void notValidRequest() {
    	System.out.println("I am sorry, I did not understand the request.");
    }
    public void enter(Scanner in) {
    	System.out.println("Press ENTER to continue.");
    	in.nextLine();
    }
}
