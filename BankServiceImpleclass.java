package com.RMe_Bank.Service;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.RMe_Bank.DAO.BankStatementDAO;
import com.RMe_Bank.DAO.BankStatementDAOImple;
import com.RMe_Bank.DAO.BankUserDAO;
import com.RMe_Bank.DAO.BankUserDAOimple;
import com.RMe_Bank.Model.BankStatementDetails;
import com.RMe_Bank.Model.BankUserDetails;
import com.RMe_Bank.exceptions.BankUserException;

public class BankServiceImpleclass implements BankService {
	BankUserDAO bankUserDAO = new BankUserDAOimple();
	Scanner scanner = new Scanner(System.in);

	@Override
	public void output(String s) {
		char res[] = s.toCharArray();
		for (char i : res) {
			System.out.print(i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println();
	}

	@Override
	public void userRegistration() {
		BankUserDetails bankUserDetails = new BankUserDetails();
		System.out.println("Enter Your Name");
		String name = scanner.nextLine();
		bankUserDetails.setName(name);
		System.out.println("Enter Deposit Amount");
		Long amount = scanner.nextLong();
		bankUserDetails.setBalance(amount);
		while (true) {
			try {
				System.out.println("Enter Your Email Id");
				String emailId = scanner.next();
				List<BankUserDetails> list = bankUserDAO.selectAllTheDetailsOfBankUsers();
				long count = 0;
				if (list != null) {
					count = list.stream().filter(e -> e.getEmailid().equals(emailId)).count();
				}
				if (emailId.endsWith("@gmail.com")) {
					if (count == 0) {
						bankUserDetails.setEmailid(emailId);
						break;
					} else {
						throw new BankUserException("Email Id already Exist...");
					}
				} else {
					throw new BankUserException("Invalid Email Id");
				}
			} catch (BankUserException b) {
				System.out.println(b.getMsg());
			}
		}
		while (true) {
			long mnumber;
			try {
				System.out.println("Enter Your Mobile Number");
				mnumber = scanner.nextLong();
				if (mnumber > 6000000000l && mnumber < 10000000000l) {
					List<BankUserDetails> list = bankUserDAO.selectAllTheDetailsOfBankUsers();
					long count = list.stream().filter(e -> e.getMobilenumber() == mnumber).count();
					if (count == 0) {
						bankUserDetails.setMobilenumber(mnumber);
						break;
					} else {
						throw new BankUserException("Mobile Number Already exist..");
					}
				} else {
					throw new BankUserException("Invalid Mobile Number");
				}
			} catch (BankUserException b) {
				System.out.println(b.getMsg());
			} catch (InputMismatchException ime) {
				scanner.nextLine();
				System.out.println("Invalid Input");
			}
		}
		while (true) {
			try {
				System.out.println("Enter Your PAN Number");
				String panNumber = scanner.next();
				List<BankUserDetails> list = bankUserDAO.selectAllTheDetailsOfBankUsers();
				long count = list.stream().filter(e -> e.getPannumber() == panNumber).count();
				int ucount = 0, ncount = 0, lcount = 0;
				if (panNumber.length() == 10) {
					for (int i = 0; i < 5; i++) {
						if ((Character.isUpperCase(panNumber.charAt(i)))) {
							ucount++;
						}
					}
					if (ucount == 5) {
						for (int i = 5; i < panNumber.length() - 1; i++) {
							if ((Character.isDigit(panNumber.charAt(i)))) {
								ncount++;
							} else {
								break;
							}
						}
					} else {
						throw new BankUserException("Invalid PAN Number");
					}
					if (ncount == 4) {
						if ((Character.isUpperCase(panNumber.charAt(panNumber.length() - 1)))) {
							lcount++;
						} else {
							throw new BankUserException("Invalid PAN Number");
						}
					} else {
						throw new BankUserException("Invalid PAN Number");
					}
				} else {
					throw new BankUserException("Invalid PAN Number");
				}
				if (ucount + ncount + lcount == panNumber.length()) {
					if (count == 0) {
						bankUserDetails.setPannumber(panNumber);
						break;
					} else {
						throw new BankUserException("PAN Number Already Exist");
					}
				} else {
					throw new BankUserException("Invalid PAN Number");
				}
			} catch (BankUserException b) {
				System.out.println(b.getMsg());
			}
		}
		while (true) {
			long aadhar;
			try {
				System.out.println("Enter Your Aadhar Number");
				aadhar = scanner.nextLong();
				if (aadhar >= 100000000000l && aadhar <= 999999999999l) {
					List<BankUserDetails> list = bankUserDAO.selectAllTheDetailsOfBankUsers();
					long count = list.stream().filter(e -> e.getAadharnumber() == aadhar).count();
					if (count == 0) {
						bankUserDetails.setAadharnumber(aadhar);
						break;
					} else {
						throw new BankUserException("Aadhar Number Already Exist");
					}
				} else {
					throw new BankUserException("Invalid Aadhar Number");
				}
			} catch (BankUserException b) {
				System.out.println(b.getMsg());
			} catch (InputMismatchException ime) {
				scanner.nextLine();
				System.out.println("Invalid Input");
			}
		}
		System.out.println("Enter Your Address");
		String address = scanner.next();
		bankUserDetails.setAddress(address);
		while (true) {
			try {
				System.out.println("Enter Your Gender");
				String gender = scanner.next();
				if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female")
						|| gender.equalsIgnoreCase("other")) {
					bankUserDetails.setGender(gender);
					break;
				} else {
					throw new BankUserException("Invalid gender");
				}
			} catch (BankUserException e) {
				System.out.println(e.getMsg());
			}
		}
		bankUserDAO.insertUserRegistartion(bankUserDetails);
	}

	@Override
	public void UserLogin() {
		System.out.println("Enter Your EmailId:");
		String email = scanner.next();
		System.out.println("Enter Your Pin:");
		int pin = scanner.nextInt();

		BankUserDetails bankUserDetails = bankUserDAO.userLoginByEmailAndPassword(email, pin);
		String name = "";

		if (bankUserDetails != null) {
			if (bankUserDetails.getGender().equalsIgnoreCase("male")) {
				name = "Hi Mr." + bankUserDetails.getName();
			} else if (bankUserDetails.getGender().equalsIgnoreCase("female")) {
				name = "Hi Miss." + bankUserDetails.getName();
			} else {
				name = "Hi " + bankUserDetails.getName();
			}

			System.out.println("Login Successful..");
			output(name);

			while (true) {
				Random random = new Random();
				LocalDate date = LocalDate.now();
				LocalTime time = LocalTime.now();
				BankStatementDAO bankStatementDAO = new BankStatementDAOImple();
				BankStatementDetails bankStatementDetails = new BankStatementDetails();
				double balance;
				System.out.println(
						"Enter: \n1. To Credit \n2. To Debit \n3. To Check Balance \n4. To Cancel Account Request \n5.For Mobile Transaction \n6.To Get Bank Statement");
				switch (scanner.nextInt()) {
				case 1:
					System.out.println("Enter Amount to be Credit:");
					double amount = scanner.nextDouble();
					if (amount >= 0 && bankUserDAO.userDeposit(email, amount) > 0) {
						System.out.println(name);
						System.out.println("Credit transaction Successful");
						balance = bankUserDAO.receiveBalance(email);
						System.out.println("Your Account Balance: " + balance);
						int t_id = random.nextInt(1000000, 9999999);
						bankStatementDetails.setTransaction_id(t_id);
						bankStatementDetails.setTransaction_amount(amount);
						bankStatementDetails.setTransaction_date(date);
						bankStatementDetails.setTransaction_time(time);
						bankStatementDetails.setTransaction_type("Credit");
						bankStatementDetails.setBalance(balance);
						bankStatementDetails.setUser_id(bankUserDetails.getId());
						bankStatementDAO.insertStatementDetails(bankStatementDetails);
					} else {
						System.out.println("Invalid Data");
					}
					break;
				case 2:
					System.out.println("Enter Amount to Debit:");
					double wamount = scanner.nextDouble();
					if (wamount >= 0 && bankUserDetails.getBalance() >= wamount) {
						if (bankUserDAO.userWithdraw(email, wamount) > 0) {
							System.out.println(name);
							System.out.println("Debit Transaction Successful...");
							balance = bankUserDAO.receiveBalance(email);
							System.out.println("Your Account Balance: " + balance);
							int t_id = random.nextInt(1000000, 9999999);
							bankStatementDetails.setTransaction_id(t_id);
							bankStatementDetails.setTransaction_amount(wamount);
							bankStatementDetails.setTransaction_date(date);
							bankStatementDetails.setTransaction_time(time);
							bankStatementDetails.setTransaction_type("Debit");
							bankStatementDetails.setBalance(balance);
							bankStatementDetails.setUser_id(bankUserDetails.getId());
							bankStatementDAO.insertStatementDetails(bankStatementDetails);
						} else {
							System.out.println("Invalid Data");
						}
					} else {
						System.out.println("Insufficient Balance");
					}
					break;
				case 3:
					System.out.println("Balance Amount: " + bankUserDAO.receiveBalance(email));
					break;
				case 4:
					System.out.println("Cancel Request");
					if (bankUserDAO.setAccountClose(email) > 0) {
						System.out.println("Account closing request raised successfully");
					} else {
						System.out.println("Server Issue");
					}
					break;
				case 5:
					System.out.println("Enter Mobile number");
					long mobilenumber = scanner.nextLong();
					if (mobilenumber >= 6000000000l && mobilenumber <= 9999999999l) {
						BankUserDetails bankUserDetails1 = bankUserDAO.checkMobileNumber(mobilenumber);
						String email1 = bankUserDetails1.getEmailid();
						if (email1 != null) {
							System.out.println("Enter amount:");
							double damount = scanner.nextDouble();
							if (damount >= 0 && bankUserDetails.getBalance() >= damount) {
								if (bankUserDAO.userWithdraw(email, damount) > 0
										&& bankUserDAO.userDeposit(email1, damount) > 0) {
									System.out.println(name);
									balance = bankUserDAO.receiveBalance(email);
									System.out.println("Your Account Balance: " + balance);
									int t_id = random.nextInt(1000000, 9999999);
									bankStatementDetails.setTransaction_id(t_id);
									bankStatementDetails.setTransaction_amount(damount);
									bankStatementDetails.setTransaction_date(date);
									bankStatementDetails.setTransaction_time(time);
									bankStatementDetails.setTransaction_type("Mobile Debit");
									bankStatementDetails.setBalance(balance);
									bankStatementDetails.setUser_id(bankUserDetails.getId());
									bankStatementDAO.insertStatementDetails(bankStatementDetails);
									bankStatementDetails.setTransaction_type("Mobile Credit");
									bankStatementDetails.setBalance(bankUserDAO.receiveBalance(email1));
									bankStatementDetails.setUser_id(bankUserDetails1.getId());
									bankStatementDAO.insertStatementDetails(bankStatementDetails);
									System.out.println("Mobile Transaction Succesffull...");
								} else {
									System.out.println("Invalid Data");
								}
							} else {
								System.out.println("Insufficient Balance");
							}
						} else {
							System.out.println("Invalid Mobile");
						}
					} else {
						System.out.println("Invalid Mobile Number");
					}
					break;
				case 6:
					System.out.println(name + "Your Bank Statement");
					List<BankStatementDetails> list = bankStatementDAO
							.selectAllTransactionByUserid(bankUserDetails.getId());
					if (list!= null) {
						list.forEach(System.out::println);
					} else {
						System.out.println("No Transaction Found");
					}
					break;
				default:
					System.out.println("Invalid Option...");
				}

				System.out.println("Do you want to perform any other operations? (Yes/No):");
				if (scanner.next().equalsIgnoreCase("no")) {
					break;
				}
			}
		} else {
			System.out.println("Invalid Login Data.");
		}
	}

}
