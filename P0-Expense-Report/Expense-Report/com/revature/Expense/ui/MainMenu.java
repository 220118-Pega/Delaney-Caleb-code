package com.revature.Expense.ui;

import java.util.Scanner;
import com.revature.Expense.bl.ReportBL;
import com.revature.Expense.models.transaction;

public class MainMenu {
	private Scanner myScanner;
	private ReportBL ReportBL;
	public MainMenu(Scanner myScanner,ReportBL ReportBL) {
		this.myScanner = myScanner;
		this.ReportBL = ReportBL;
	}
	
public void start() {
	boolean keepGoing = true;
	do {
		MainTextMenu();
		
		String userInput = myScanner.nextLine();
		switch(userInput) {
		case "1":
			System.out.println("Filing your report");
			createtransaction();
			break;
		case "2":
			System.out.println("Welcome to Records");
			getAlltransaction();
			break;
		case "3":
			Approvetransaction();
			break;
		case "4":
			Denytransaction();
			break;
		case "x":
			GoodBye();
			keepGoing = false;
			break;
		default:
			PickRealOption();
			break;
		}
		
	}while(keepGoing);
}

//approves a transaction
private void Approvetransaction() {
	// if given incorect id num then a dummy is changed need to give warning
	transaction updatetran = Pendingtransaction();
	if(confirm(updatetran))
		updatetran.Approve();
		System.out.println("Approved");
	}
//denys a transaction
private void Denytransaction() {
	// if given incorect id num then a dummy is changed need to give warning
	transaction updatetran = Pendingtransaction();
	if(confirm(updatetran)) {
		updatetran.Deny();
		System.out.println("Denied");
	}	
}
//is the menu to retrive records from memory repo has options for employees and managers currently trusts people to tell the truth
private void getAlltransaction() {
	//variables for program
	Boolean keepGoing = true;
	String Mchoice = "";
	
	//employee or manager
	do {
	MainAllTransactionMenu();
	String authority = myScanner.nextLine();
	
	switch(authority) {
	//if employee they only can access their own records
	case "1":
		GetEmpRecords();
		keepGoing = false;
		break;
	//if manager they have access to manager menu
	//a manager isnt auto moved out of this screen they need to push x to leave
	case "2":
		do {
		MainManagerMenu();
		Mchoice = myScanner.nextLine();
		switch(Mchoice) {
		//manager prints all transactions
		case "1":
			for(transaction transaction:ReportBL.gettransaction()) {
				System.out.println(transaction);
			}
		//manager gets records for a single employee
		case "2":
			GetEmpRecords();
		//manager gets all approved records
		case "3":
			GetAllOfState(Integer.parseInt(Mchoice));
		//manager gets all denied records
		case "4":
			GetAllOfState(Integer.parseInt(Mchoice));
		//manager gets all pending records
		case "5":
			GetAllOfState(Integer.parseInt(Mchoice));
		// there are 2 loops the manager loop and the general function loop so both have default and exit statments at the end
		case "x":
			GoodBye();
			keepGoing = false;
			break;
		default:
			PickRealOption();
			break;
			
		}}while(keepGoing);
	case "x":
		GoodBye();
		keepGoing = false;
		break;
	default:
		PickRealOption();
		break;
	}}while(keepGoing);
	
	
	//employee leads to 1
	
	//manager  leads to 1-5
	
	//1 list all with specific employee id
	
	//2 warning only manager list all
	
	//3 warning only manager list all approved
	
	//4 warning only manager list all denied
	
	//5 warning only manager list all pending
	}

//creates a transaction from given info and uploads it to memory repo
//takes someone through proccess to create a transaction and upload to memory repo
private void createtransaction() {
	boolean keepGoing = true;
	//all the variables
	int userid = 0;
	double transactionamount = 0;
	int quit = 0;
	//getting all variable info from employee
	//a do while loop to get an accurate emp id
	EmployeeIdRequest();
	userid = userIntAnswer();
	if (userid == 0) {
		GoodBye();
	}
	else {
	
	//do while loop to get a accurate tran amout
	System.out.println("Please enter transaction amount: ");
	while(keepGoing) {
		if (myScanner.hasNextDouble()){
				transactionamount = myScanner.nextDouble();
				keepGoing = false;
		}
		else {
			String answer = myScanner.nextLine();
			if(answer.contains("x")) {
				keepGoing = false;
			}
			else {
				System.out.println("invaild number please enter vaild amount: ");
				System.out.println("or enter x to quit:");}
		}}
	if(transactionamount == 0) {
		GoodBye();
	}
	else {
	keepGoing = true;
	//they can do anything to date need to make them enter a date format
	System.out.println("Please enter date of transaction: ");
	System.out.println("or enter x to quit:");
	String date = myScanner.nextLine();
	if(date.equals("x")) {
		GoodBye();
	}
	//they can say anything for descrption no need to control it
	System.out.println("Please enter a descrption of the transaction: ");
	System.out.println("or enter x to quit:");
	String descrption = myScanner.nextLine();
	if(descrption.equals("x")) {
		GoodBye();
	}
	else {
	
	//making new transaction with info
	transaction newReport = new transaction(userid,transactionamount,date,descrption);
	
	// adding the right type
	while(keepGoing) {
	MainTypeMenu();
	String transactiontype = myScanner.nextLine();
	//type
	switch(transactiontype) {
	case "1":
		newReport.Lodging();
		keepGoing = false;
		break;
	case "2":
		newReport.Travel();
		keepGoing = false;
		break;
	case "3":
		newReport.Food();
		keepGoing = false;
		break;
	case "4":
		newReport.Other();
		keepGoing = false;
		break;
	case "x":
		keepGoing = false;
		quit = 1;
		break;
	default:
		PickRealOption();
		break;
	}}
	if( quit == 1) {
		GoodBye();
	}
	else {
	//saving
	ReportBL.addtransaction(newReport);
	System.out.println(newReport);
	
}}}}}


//contains the simplifications to above code by taking repeating code and turning those to a method
//the similar process of approve and deny
private transaction Pendingtransaction() {
	int tranid = 0;
	System.out.println("Enter the id of the transaction: ");
	tranid = userIntAnswer();
	transaction updatetran = ReportBL.gettransactionByTranId(tranid);
	return updatetran;
}
//confirms that the transaction is a real transaction and not the dummy transaction
private boolean confirm(transaction updatetran) {
	boolean real = false;
	if(updatetran.getTransactionid() == 0) {
		System.out.println("No Record of Transaction with that id check id used.");
		myScanner.nextLine();
	}else {
		real = true;
	}
	return real;
}
//prints all employee transactions
//common tasks method out for ease of code
private void GetEmpRecords() {
	EmployeeIdRequest();
	int userid = userIntAnswer();
	for(transaction user:ReportBL.gettransaction()) {
		if(user.getUserid() == userid) {
			System.out.println(user);
			}}
	myScanner.nextLine();//this is to clear the line so things arent left for future checks
}
//prints all transactions of a type decided by number manager entered
private void GetAllOfState(int choice) {
	transaction helper = new transaction(0,1.0,"zero","zero");
	if (choice == 3) {helper.Approve();}
	if (choice == 4) {helper.Deny();}
	if (choice == 5) {helper.Pending();}
	for(transaction transaction:ReportBL.gettransaction()) {
		if(transaction.getState() == helper.getState()) {
			System.out.println(transaction);
	}}
	myScanner.nextLine();//this is the clear it for returning to method
}
//takes person through a loop to get a accurate id number with 0 being the dummny number if they exit early
private int userIntAnswer() {
	int id = 0;
	boolean keepGoing = true;
	while(keepGoing) {
			if (myScanner.hasNextInt()){
					id = myScanner.nextInt();
					keepGoing = false;
					break;
			}
			else {
				String answer = myScanner.nextLine();
				if(answer.contains("x")) {
					keepGoing = false;
				}
				else {
					System.out.println("invaild id please enter vaild id: ");
					System.out.println("or enter x to quit:");}
	}}
	return id;
}
//holding text blocks for ease of editing

//these are all just strings that were repeated so put them down here to make code above better
private void PickRealOption() {
	System.out.println("Please only put listed options");
}
private void GoodBye() {
	System.out.println("Good Bye \n");
}
private void MainTextMenu() {
	System.out.println("Welcome to expense reports");
	System.out.println("[1] Create a reinburstment request");
	System.out.println("[2] Records of requests");
	System.out.println("[3] Approve request");
	System.out.println("[4] Deny request");
	System.out.println("[x] Exit");
}
private void MainTypeMenu() {
	System.out.println("please enter type of transaction");
	System.out.println("[1] Lodging");
	System.out.println("[2] Travel");
	System.out.println("[3] Food");
	System.out.println("[4] Other");
	System.out.println("[x] to quit");
}
private void MainManagerMenu() {
	System.out.println("[1] list all records");
	System.out.println("[2] list 1 employee's records");
	System.out.println("[3] list all approved records");
	System.out.println("[4] list all denied records");
	System.out.println("[5] list all pending records");
	System.out.println("[x] Quit");
}
private void EmployeeIdRequest() {
	System.out.println("Please enter a employee id");
	System.out.println("or press x to exit: ");
}
private void MainAllTransactionMenu() {
	System.out.println("[1] i am employee");
	System.out.println("[2] i am manager");
	System.out.println("[x] Quit");
}







}

