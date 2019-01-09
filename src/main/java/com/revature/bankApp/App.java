package com.revature.bankApp;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
	private static Logger log = LogManager.getLogger(App.class);
	UserInteractor userInt;
	SuperUserManager sum;

	public App() {
	}

	public static void main(String[] args) {
		log.traceEntry();
		App app = new App();
		Scanner in = new Scanner(System.in);
		app.greetingManager(in);
		app.userManager(in);

		in.close();
		log.traceExit();
	}

	public String greeting(Scanner in) {
		log.traceEntry();
		System.out.println("Wecome to JBDC Bank.");
		System.out.println("\n\nSelect your options:");
		System.out.println("(Please choose either the number, first letter or whole command");
		System.out.println("1. Login");
		System.out.println("2. Sign Up");
		System.out.print(":");
		return in.nextLine();
	}

	public void greetingManager(Scanner in) {
		log.traceEntry();
		boolean notMade = true;
		userInt = UserInteractor.getUserInteractor();
		while (notMade) {

			String choice = greeting(in).toLowerCase().trim();

			switch (choice) {
			case "1":
			case "1.":
			case "l":
			case "login":
				notMade = false;
				lineBreak(5);
				userInt.login(in);
				break;
			case "2":
			case "2.":
			case "s":
			case "sign":
			case "signup":
			case "sign up":
				notMade = false;
				lineBreak(5);
				userInt.signUp(in);
				break;
			case "superuser":
				superUserHandler(in);
				break;
			default:
				notValidRequest();
				break;
			}
		}
		log.traceExit();
	}

	public String displayUserOptions(Scanner in) {
		log.traceEntry();
		lineBreak(5);
		System.out
				.println("Welcome " + userInt.getUser().getUserFirstName() + " " + userInt.getUser().getUserLastName());
		lineBreak(2);
		System.out.println("Select yout options:");
		System.out.println("(Please Please choose either the number, first letter or word of the command)");
		lineBreak(1);
		System.out.println("1. View Accounts");
		System.out.println("2. Manage Accounts");
		System.out.println("3. History of Transactions");
		System.out.println("4. Update Information");
		System.out.println("5. Sign Out");

		return log.traceExit(in.nextLine());

	}

	public void userManager(Scanner in) {
		log.traceEntry();
		boolean quitFlag = true;

		while (quitFlag) {
			String choice = displayUserOptions(in).toLowerCase().trim();
			switch (choice) {
			case "1":
			case "1.":
			case "v":
			case "view":
			case "view accounts":
			case "1. view accounts":
				userInt.printUserAccounts();
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
			case "5":
			case "5.":
			case "s":
			case "sign":
			case "sign out":
			case "5. sign out":
				userInt.signOut();
				System.out.println("Successfully Signed Out.");
				quitFlag = false;
				break;
			default:
				notValidRequest();
				break;
			}
		}
		log.traceExit();
	}

	public void superUserHandler(Scanner in) {
		log.traceEntry();
		sum = SuperUserManager.getSuperUser();
		boolean login = sum.login(in);
		if (login) {
			superUserOptions(in);
		} else {
			sum.endSuperUser();
		}
		log.traceExit();
	}

	public String superUserOptions(Scanner in) {
		log.traceEntry();
		lineBreak(5);
		System.out.println("Welcome SUPERUSER");
		lineBreak(2);
		System.out.println("Select yout options:");
		System.out.println("(Please Please choose either the number, first letter or word of the command)");
		lineBreak(1);
		System.out.println("1. View Users");
		System.out.println("2. Update a User");
		System.out.println("3. Create a User");
		System.out.println("4. Delete a User");
		System.out.println("5. Logout");
		return log.traceExit(in.nextLine());
	}

	public void superUserManager(Scanner in) {
		log.traceEntry();
		boolean quitFlag = true;

		while (quitFlag) {
			String choice = superUserOptions(in).trim().toLowerCase();
			switch (choice) {
			case "1":
			case "1.":
			case "v":
			case "view":
			case "view users":
			case "1. view users":
				sum.printUsers();
				enter(in);
				break;
			case "2":
			case "2.":
			case "u":
			case "update":
			case "update a user":
			case "2. update a user":
				sum.updateUser(in);
				enter(in);
				break;
			case "3":
			case "3.":
			case "c":
			case "create":
			case "create a user":
			case "3. create a user":
				sum.createUser(in);
				enter(in);
				break;
			case "4":
			case "4.":
			case "d":
			case "delete":
			case "delete a user":
			case "4. delete a user":
				sum.deleteUser(in);
				enter(in);
				break;
			case "5":
			case "5.":
			case "l":
			case "logout":
			case "5. logout":
				sum.endSuperUser();
				System.out.println("Superuser logged out ");
				quitFlag = false;
				break;
			default:
				notValidRequest();
				break;
			}
		}
		log.traceExit();
	}

	public String accountOptions(Scanner in) {
		log.traceEntry();
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
		return log.traceExit(in.nextLine());
	}

	public void accountManager(Scanner in) {
		log.traceEntry();
		boolean quitFlag = true;
		while (quitFlag) {
			lineBreak(5);
			String choice = accountOptions(in).toLowerCase().trim();

			switch (choice) {
			case "1":
			case "1.":
			case "v":
			case "view":
			case "view accounts":
			case "1. view accounts":
				userInt.printUserAccounts();
				enter(in);
				break;
			case "2":
			case "2.":
			case "d":
			case "deposit":
			case "2. deposit":
				userInt.deposit(in);
				enter(in);
				break;
			case "3":
			case "3.":
			case "w":
			case "withdraw":
			case "3. withdraw":
				userInt.withdraw(in);
				enter(in);
				break;
			case "4":
			case "4.":
			case "t":
			case "transfer":
			case "4. transfer":
				userInt.transfer(in);
				enter(in);
				break;
			case "5":
			case "5.":
			case "n":
			case "new":
			case "new account":
			case "5. new account":
				userInt.newAccount(in);
				enter(in);
				break;
			case "6":
			case "6.":
			case "delete":
			case "delete account":
			case "6. delete":
				userInt.deleteAccount(in);
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
		log.traceExit();
	}

	// App helper functions

	public void lineBreak(int numBreak) {
		log.traceEntry();
		for (int i = 0; i < numBreak; ++i) {
			System.out.print("\n");
		}
		log.traceExit();
	}

	public void notValidRequest() {
		log.traceEntry();
		System.out.println("I am sorry, I did not understand the request.");
		log.traceExit();
	}

	public void enter(Scanner in) {
		log.traceEntry();
		System.out.println("Press ENTER to continue.");
		in.nextLine();
		log.traceExit();
	}
}
