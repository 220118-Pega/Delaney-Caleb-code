package com.revature.Expense.dl;

import java.util.ArrayList;
import java.util.List;

import com.revature.Expense.models.transaction;
import com.revature.Expense.models.userInfo;

/**
 * is the point where the data lives and can be messed with and returned
 * @author 16del
 *
 */
public class RepoMemory implements IRepo {
	private static List<transaction> reportoftran;
	private static List<userInfo> Listofemp;
	private static int LatesttranId = 1;
	private static int LatestempId = 1;// to keep employee id accurrate in memory to prevent double id's
	private static int LatestMId = 1;// to keep manager id accurrate so double id's don't happen wont do anything if manger is moved to god and the number will just be lost
	
public RepoMemory() {
		reportoftran = new ArrayList<transaction>();
		Listofemp = new ArrayList<userInfo>();
	}

public void IntializeEmployees() {
	//yes a for loop would be nice
	int empid = LatestempId;
	LatestempId++;
	String name = "the lord";
	boolean isM = true;
	int Mid = 1;
	//1 god
	Listofemp.add(new userInfo(empid, name, isM, Mid));
	
	//3 managers
	empid = LatestempId;
	LatestempId++;
	name = "London Revival";
	isM = true;
	Mid = LatestMId;
	LatestMId++;
	Listofemp.add(new userInfo(empid, name, isM, Mid));
	empid = LatestempId;
	LatestempId++;
	name = "Allo Cats";
	isM = true;
	Mid = LatestMId;
	LatestMId++;
	Listofemp.add(new userInfo(empid, name, isM, Mid));
	empid = LatestempId;
	LatestempId++;
	name = "King Blond";
	isM = true;
	Mid = LatestMId;
	LatestMId++;
	Listofemp.add(new userInfo(empid, name, isM, Mid));
	
	//9 employees
	empid = LatestempId;
	LatestempId++;
	name = "Oscar Jeffery";
	isM = false;
	Mid = 1;
	Listofemp.add(new userInfo(empid, name, isM, Mid));
	empid = LatestempId;
	LatestempId++;
	name = "Xander Jolly";
	isM = false;
	Mid = 1;
	Listofemp.add(new userInfo(empid, name, isM, Mid));
	empid = LatestempId;
	LatestempId++;
	name = "Pete Malfoy";
	isM = false;
	Mid = 1;
	Listofemp.add(new userInfo(empid, name, isM, Mid));
	empid = LatestempId;
	LatestempId++;
	name = "Madeleine Colton";
	isM = false;
	Mid = 2;
	Listofemp.add(new userInfo(empid, name, isM, Mid));
	empid = LatestempId;
	LatestempId++;
	name = "Millie Johnson";
	isM = false;
	Mid = 2;
	Listofemp.add(new userInfo(empid, name, isM, Mid));
	empid = LatestempId;
	LatestempId++;
	name = "Maisie Morgan";
	isM = false;
	Mid = 2;
	Listofemp.add(new userInfo(empid, name, isM, Mid));
	empid = LatestempId;
	LatestempId++;
	name = "Jess Davis";
	isM = false;
	Mid = 3;
	Listofemp.add(new userInfo(empid, name, isM, Mid));
	empid = LatestempId;
	LatestempId++;
	name = "Oli Jones";
	isM = false;
	Mid = 3;
	Listofemp.add(new userInfo(empid, name, isM, Mid));
	empid = LatestempId;
	LatestempId++;
	name = "Charlotte Rodriguez";
	isM = false;
	Mid = 3;
	Listofemp.add(new userInfo(empid, name, isM, Mid));
}

//returns the entire list of all employees 
public List<userInfo> getuserInfo(){
	return RepoMemory.Listofemp;
}

//adds a employee to the data base note only managers should have access to this
@Override
public void addemployee(userInfo newuser) {
	newuser.setEmployid(LatestempId);
	Listofemp.add(newuser);
	LatestempId++;
	
}
//gets a employee object out of the list based on the id
@Override
public userInfo getUserById(int id) {
	userInfo foundUser = new userInfo("zero",0);
	for(userInfo UI:Listofemp) {
		if (UI.getEmployid() == id) {
			foundUser = UI;
		}
	}
	return foundUser;
}

//adds a transaction to the data base increments global transaction id
@Override
public void addtransaction(transaction newtran) {
	newtran.setTransactionid(LatesttranId);
	reportoftran.add(newtran);
	LatesttranId++;
}

//returns the entire list of transactions ie gives everything in memory
@Override
public List<transaction> gettransaction() {
	return RepoMemory.reportoftran;
}

//creats a dummy in case of failure and searches through  the database for a match by transaction id returns match if found returns dummy if not
@Override
public transaction gettransactionByTranId(int id) {
	//userid,amount,date,type,id,status
	transaction foundtran = new transaction(0,0,"zero","zero");
	for(transaction transaction:reportoftran) {
		if(transaction.getTransactionid() == id) {
			foundtran = transaction;
		}
	}
	return foundtran;
}
//creates a dummy in case of failure and searches through the database for a match by employee id returns match if found returns dummy if not
@Override
public List<transaction> gettransactionByUserId(int id) {
	//userid,amount,date,type,id,status
	List<transaction> UserReports = new ArrayList<transaction>();
	transaction dummytran = new transaction(0,0,"zero","nothing");
	for(transaction transaction:reportoftran) {
		if(transaction.getUserid() == id) {
			UserReports.add(transaction);
		}
	}
	if(UserReports.isEmpty()) {
		UserReports.add(dummytran);
	}
	return UserReports;
}



}