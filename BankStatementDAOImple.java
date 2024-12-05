package com.RMe_Bank.DAO;

import java.sql.Connection;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.RMe_Bank.Model.BankStatementDetails;

public class BankStatementDAOImple implements BankStatementDAO {

	private final static String url = "jdbc:mysql://localhost:3306/teca64_advancedjava?user=root&password=12345";
	private final static String insert = "insert into bank_statement values(?,?,?,?,?,?,?)";
	private final static String select="select * from bank_statement where User_Id=?";

	@Override
	public void insertStatementDetails(BankStatementDetails bankStatementDetails) {
		try {
			Connection connection=
			DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(insert);
			preparedStatement.setInt(1,bankStatementDetails.getTransaction_id());
			preparedStatement.setDouble(2,bankStatementDetails.getTransaction_amount());
			preparedStatement.setDouble(3,bankStatementDetails.getBalance());
			preparedStatement.setDate(4, Date.valueOf(bankStatementDetails.getTransaction_date()));
			preparedStatement.setTime(5, Time.valueOf(bankStatementDetails.getTransaction_time()));
			preparedStatement.setString(6, bankStatementDetails.getTransaction_type());
			preparedStatement.setInt(7, bankStatementDetails.getUser_id());
			int result=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<BankStatementDetails> selectAllTransactionByUserid(int userid) {
		try {
			Connection connection=
			DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(select);
			preparedStatement.setInt(1, userid);
			ResultSet resultSet=preparedStatement.executeQuery();
			List<BankStatementDetails> list=new ArrayList<BankStatementDetails>();
			if(resultSet.isBeforeFirst())
			{
				//Transaction_Id, Transaction_Amount, Balance_Amount, Date_Of_Transaction, Time_Of_Transaction, Transaction_Type, User_Id
				while(resultSet.next())
				{
					BankStatementDetails bankStatementDetails=new BankStatementDetails();
					bankStatementDetails.setTransaction_id(resultSet.getInt("Transaction_Id"));
					bankStatementDetails.setTransaction_amount(resultSet.getDouble("Transaction_Amount"));
					bankStatementDetails.setBalance(resultSet.getDouble("Balance_Amount"));
					bankStatementDetails.setTransaction_date(LocalDate.parse(resultSet.getString("Date_Of_Transaction")));
					bankStatementDetails.setTransaction_time(LocalTime.parse(resultSet.getString("Time_Of_Transaction")));
					bankStatementDetails.setTransaction_type(resultSet.getString("Transaction_Type"));
					bankStatementDetails.setTransaction_id(resultSet.getInt("User_Id"));
					list.add(bankStatementDetails);
				}
			}
			else
			{
				return null;
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
