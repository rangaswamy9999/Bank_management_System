package com.RMe_Bank.DAO;

import java.sql.ResultSet;
import java.util.List;

import com.RMe_Bank.Model.BankUserDetails;

public interface BankUserDAO {
	void insertUserRegistartion(BankUserDetails bud);

	List<BankUserDetails> selectAllTheDetailsOfBankUsers();

	int updatePinAndAccountNumber(int pin, int anumber, long phoneNumber);

	BankUserDetails userLoginByEmailAndPassword(String email, int pin);

	int userDeposit(String email, double amount);

	int userWithdraw(String email, double amount);

	double receiveBalance(String email);

	BankUserDetails checkMobileNumber(Long mobilenumber);

	int setAccountClose(String email);

	int deleteSelectedUserAccount(long phonenumber);

}
