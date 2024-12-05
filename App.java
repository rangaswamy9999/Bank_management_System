package com.RMe_Bank;

import java.util.Scanner;

import com.RMe_Bank.DAO.AdminUserDAO;
import com.RMe_Bank.DAO.AdminUserDAOImple;
import com.RMe_Bank.Service.AdminService;
import com.RMe_Bank.Service.AdminServiceImple;
import com.RMe_Bank.Service.BankService;
import com.RMe_Bank.Service.BankServiceImpleclass;

public class App {
	public static void main(String[] args)
	{
		BankService service = new BankServiceImpleclass();
		service.output("Welcome To RMe Bank..🙏");
		while (true) {
			System.out.println("Enter \n1: For User Registration \n2: For Admin Login \n3: For User Login");
			Scanner scanner = new Scanner(System.in);
			switch (scanner.nextInt()) {
			case 1:
				service.output("User registration");
				service.userRegistration();
				break;
			case 2:
				service.output("Admin Login");
				AdminService adminService = new AdminServiceImple();
				adminService.adminLogin();
				break;
			case 3:
				service.output("User Login");
				service.UserLogin();
				break;
			default:
				service.output("Invalid Option..");
			}
			service.output("Do you want to continue(Y/N)");
			if (scanner.next().equalsIgnoreCase("N")) {
				service.output("Thank You Visit Again 🙏🙏🙏");
				break;
			}
		}
	}
}
