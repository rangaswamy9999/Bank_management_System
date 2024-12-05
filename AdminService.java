package com.RMe_Bank.Service;

import com.RMe_Bank.Model.BankUserDetails;

public interface AdminService {
	void adminLogin();

	void getAllthePendingUserAccounts();

	void selectAllUserDetails();

	void acceptUserRequest(long pnumber);

	void rejectUserRequest(BankUserDetails bankUserDetails);

	void deleteUserAccountBasedOnRequest();
}
