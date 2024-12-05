package com.RMe_Bank.Service;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.RMe_Bank.DAO.AdminUserDAO;
import com.RMe_Bank.DAO.AdminUserDAOImple;
import com.RMe_Bank.DAO.BankUserDAO;
import com.RMe_Bank.DAO.BankUserDAOimple;
import com.RMe_Bank.Exception.AdminException;
import com.RMe_Bank.Model.BankUserDetails;

public class AdminServiceImple implements AdminService {
	Scanner scanner = new Scanner(System.in);
	BankUserDAO bankUserDAO = new BankUserDAOimple();
	AdminUserDAO adminUserDAO = new AdminUserDAOImple();

	@Override
	public void adminLogin() {
		System.out.println("Enter Admin Email Id:");
		String aemilid = scanner.next();
		System.out.println("Enter Admin Password");
		String apassword = scanner.next();
		System.out.println("Admin Login Successfull....");
		try {
			if (adminUserDAO.getAdminDetailsFormEmailIdAndPassword(aemilid, apassword)) {
				while (true) {
					System.out.println(
							"Enter \n 1.To Get All User Details.\n2.To Get All Account Request Details\n3.To Get All Account Closing Request Details");
					switch (scanner.nextInt()) {
					case 1:
						System.out.println("All User Details");
						selectAllUserDetails();
						break;
					case 3:
						System.out.println("All User Account Closing Request Details");
						deleteUserAccountBasedOnRequest();
						break;
					case 2:
						System.out.println("All User Account Request Details");
						getAllthePendingUserAccounts();
						break;
					default:
						System.out.println("Invalid Option..");
					}
					System.out.println("Do you want to perform Any Admin Operations(Yes/No):");
					if (scanner.next().equalsIgnoreCase("no")) {
						break;
					}
				}
			} else {
				throw new AdminException("Invalid Login Details..");
			}
		} catch (AdminException a) {
			System.out.println(a.geteMsg());
		}
	}

	@Override
	public void getAllthePendingUserAccounts() {
		List<BankUserDetails> pending = bankUserDAO.selectAllTheDetailsOfBankUsers();

		List<BankUserDetails> list = pending.stream().filter(e -> e.getStatus().equals("pending"))
				.collect(Collectors.toList());
		int count = 1;
		for (BankUserDetails b : list) {
			System.out.println("S No: " + count++);
			System.out.println("User Name: " + b.getName());
			System.out.println("User Email Id: " + b.getEmailid());
			System.out.println("User Account Status: " + b.getStatus());
			System.out.print("User Aadhar Number: ");
			String adnum = String.valueOf(b.getAadharnumber());
			for (int i = 0; i < adnum.length(); i++) {
				System.out.print(i < 4 || i > 8 ? adnum.charAt(i) : "*");
			}
			System.out.println();
			System.out.print("User Mobile Number: ");
			String number = String.valueOf(b.getMobilenumber());
			for (int i = 0; i < number.length(); i++) {
				System.out.print(i < 3 || i > 7 ? number.charAt(i) : "*");
			}
			System.out.println();
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("Enter \n 1.To Accept \n 2.To Reject");
			switch (scanner.nextInt()) {
			case 1:
				System.out.println("Accepted");
				acceptUserRequest(b.getMobilenumber());
				System.out.println(b.getName() + " accepted your Account Request.");
				break;
			case 2:
				System.out.println("Rejected");
				rejectUserRequest(b);
				System.out.println(b.getName() + " rejected your Account Request.");
				break;
			default:
				System.out.println("Invalid");
			}
		}
		if (count == 1) {
			System.out.println("No pending accounts found..");
		}
	}

	@Override
	public void selectAllUserDetails() {
		List<BankUserDetails> allacounts = bankUserDAO.selectAllTheDetailsOfBankUsers();
		allacounts.forEach(System.out::println);

	}

	@Override
	public void acceptUserRequest(long phoneNumber) {
		Random random = new Random();
		int pin = random.nextInt(1000, 10000);
		int accountNumber = random.nextInt(1000000, 10000000);
		BankUserDAO bankUserDAO = new BankUserDAOimple();
		if (bankUserDAO.updatePinAndAccountNumber(pin, accountNumber, phoneNumber) > 0) {
			System.out.println("Account number and pin Updated Successfully");
		} else {
			System.out.println("Failed to update Account number and pin...");
		}
	}

	@Override
	public void rejectUserRequest(BankUserDetails bankUserDetails) {
		BankUserDAO bankUserDAO = new BankUserDAOimple();
		if (bankUserDAO.deleteSelectedUserAccount(bankUserDetails.getMobilenumber()) > 0) {
			System.out.println("Account deleted Successfully..");
		} else {
			System.out.println("Data doesn't exist");
		}
	}

	@Override
	public void deleteUserAccountBasedOnRequest() {
		BankUserDAO bankUserDAO = new BankUserDAOimple();
		List<BankUserDetails> pending = bankUserDAO.selectAllTheDetailsOfBankUsers();

		List<BankUserDetails> list = pending.stream().filter(e -> e.getStatus().equals("close"))
				.collect(Collectors.toList());
		for (BankUserDetails bankUserDetails : list) {
			String name = bankUserDetails.getName();
			if (bankUserDAO.deleteSelectedUserAccount(bankUserDetails.getMobilenumber()) > 0) {
				System.out.println(name + " account closed Successfully");
			}
		}
		if (list == null) {
			System.out.println("No Account closing requests found..");
		}
	}

}
