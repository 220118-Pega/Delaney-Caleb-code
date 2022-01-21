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
		System.out.println("Welcome to expense reports");
		System.out.println("[1] Create a reinburstment request");
		System.out.println("[2] List all requests");
		System.out.println("[3] Approve request");
		System.out.println("[4] Deny request");
		System.out.println("[x] Exit");
		
		String userInput = myScanner.nextLine();
		switch(userInput) {
		case "1":
			System.out.println("Filing your report");
			createtransaction();
			break;
		case "2":
			System.out.println("Showing all current requests");
			getAlltransaction();
			break;
		case "3":
			Approvetransaction();
			break;
		case "4":
			Denytransaction();
			break;
		case "x":
			System.out.println("Dismissed");
			keepGoing = false;
			break;
		default:
			System.out.println("Please only put listed options");
			break;
		}
		
	}while(keepGoing);
}

private void Approvetransaction() {
	// if given incorect id num then a dummy is changed need to give warning
	System.out.println("Enter the id of the transaction: ");
	String tranid = myScanner.nextLine();
	transaction updatetran = ReportBL.gettransactionById(Integer.parseInt(tranid));
	if(updatetran.getTransactionid() == 0) {
		System.out.println("No Record of Transaction with that id check id used.");
	}else {
		updatetran.Approve();
		
		System.out.println("Approved");
	}
	
}

private void Denytransaction() {
	// if given incorect id num then a dummy is changed need to give warning
	System.out.println("Enter the id of the transaction: ");
	String tranid = myScanner.nextLine();
	transaction updatetran = ReportBL.gettransactionById(Integer.parseInt(tranid));
	if(updatetran.getTransactionid() == 0) {
		System.out.println("No Record of Transaction with that id check id used.");
	}else {
		updatetran.Deny();
		
		System.out.println("Denied");
	}
	
}

private void getAlltransaction() {
	// TODO Auto-generated method stub
	for(transaction transaction:ReportBL.gettransaction()) {
		System.out.println(transaction);
	}
}

private void createtransaction() {
	boolean keepGoing = true;
	//all the variables
	String userid = "";
	String transactionamount = "";
	// TODO Auto-generated method stub
	//getting all variable info from employee
	//a do while loop to get an accurate emp id
	System.out.println("Please enter you employee id: ");
	do {
		if (myScanner.hasNextInt()){
			userid = myScanner.nextLine();
			keepGoing = false;
		}else {
			System.out.println("invaild employee id please enter employee id: ");
			myScanner.nextLine();
	}}while(keepGoing);	
	keepGoing = true;
	
	//do while loop to get a accurate tran amout
	System.out.println("Please enter transaction amount: ");
	do{
		if(myScanner.hasNextFloat()){;
			transactionamount = myScanner.nextLine();
			keepGoing = false;
		}else {
			System.out.println("invaild number please enter exact amount: ");
			myScanner.nextLine();
	}}while(keepGoing);
	keepGoing = true;
		
	System.out.println("Please enter date of transaction: ");
	String date = myScanner.nextLine();
	//inserting into transaction
	transaction newReport = new transaction(Integer.parseInt(userid),Float.parseFloat(transactionamount),date);
	
	
	while(keepGoing) {
	System.out.println("please enter type of transaction: ");
	System.out.println("[1] Lodging");
	System.out.println("[2] Travel");
	System.out.println("[3] Food");
	System.out.println("[4] Other");
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
	default:
		System.out.println("Please only put listed options");
		break;
	}}
	
	//saving
	ReportBL.addtransaction(newReport);
	System.out.println(newReport);
	
}
}