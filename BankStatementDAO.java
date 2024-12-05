package com.RMe_Bank.DAO;

import java.util.List;

import com.RMe_Bank.Model.BankStatementDetails;

public interface BankStatementDAO {
	void insertStatementDetails(BankStatementDetails bankStatementDetails);

	List<BankStatementDetails> selectAllTransactionByUserid(int userid);
}
