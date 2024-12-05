package com.RMe_Bank.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.RMe_Bank.Model.BankUserDetails;
import com.RMe_Bank.Service.BankService;
import com.RMe_Bank.Service.BankServiceImpleclass;

public class BankUserDAOimple implements BankUserDAO {
	private final String url = "jdbc:mysql://localhost:3306/teca64_advancedjava?user=root&password=12345";
	private final String insert = "insert into bank_user_details(User_name, User_emailid, User_adhar_number, User_PAN_number, User_mobile_number, User_address, User_gender,balance, status) values(?,?,?,?,?,?,?,?,?)";
	private final String update = "update bank_user_details set User_pin=?,User_account_number=?,status=? where User_mobile_number=?";
	private final String delete = "delete from bank_user_details where User_mobile_number=?";
	private final String userLogin = "select * from bank_user_details where User_emailid=? and User_pin=?";
	private final String deposit = "update bank_user_details set balance=balance+? where User_emailid=?";
	private final String withdraw = "update bank_user_details set balance=balance-? where User_emailid=?";
	private final String reciveBalance = "select * from bank_user_details where User_emailid=?";
	private final String checkmobile = "select * from bank_user_details where User_mobile_number=?";
	private final String closeaccount = "update bank_user_details set status='close' where User_emailid=?";

	@Override
	public void insertUserRegistartion(BankUserDetails bud) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(insert);
			preparedStatement.setString(1, bud.getName());
			preparedStatement.setString(2, bud.getEmailid());
			preparedStatement.setLong(3, bud.getAadharnumber());
			preparedStatement.setString(4, bud.getPannumber());
			preparedStatement.setLong(5, bud.getMobilenumber());
			preparedStatement.setString(6, bud.getAddress());
			preparedStatement.setString(7, bud.getGender());
			preparedStatement.setDouble(8, bud.getBalance());
			preparedStatement.setString(9, "pending");
			int result = preparedStatement.executeUpdate();
			if (result > 0) {
				System.out.println("Date inserted successfully");
			} else {
				System.out.println("Invalid data");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static final String select_All = "select * from bank_user_details";

	@Override
	public List<BankUserDetails> selectAllTheDetailsOfBankUsers() {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(select_All);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<BankUserDetails> list = new ArrayList<BankUserDetails>();
			if (resultSet.isBeforeFirst()) {
				while (resultSet.next()) {
					BankUserDetails bankUserDetails = new BankUserDetails();
					bankUserDetails.setName(resultSet.getString("User_name"));
					bankUserDetails.setEmailid(resultSet.getString("User_emailid"));
					bankUserDetails.setAadharnumber(resultSet.getLong("User_adhar_number"));
					bankUserDetails.setPannumber(resultSet.getString("User_PAN_number"));
					bankUserDetails.setMobilenumber(resultSet.getLong("User_mobile_number"));
					bankUserDetails.setStatus(resultSet.getString("status"));
					list.add(bankUserDetails);
				}
				return list;
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updatePinAndAccountNumber(int pin, int anumber, long pnumber) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(update);
			preparedStatement.setInt(1, pin);
			preparedStatement.setInt(2, anumber);
			preparedStatement.setString(3, "Accepted");
			preparedStatement.setLong(4, pnumber);
			int result = preparedStatement.executeUpdate();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int deleteSelectedUserAccount(long phonenumber) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setLong(1, phonenumber);
			int result = preparedStatement.executeUpdate();
			if (result > 0) {
				return 1;
			} else {
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public BankUserDetails userLoginByEmailAndPassword(String email, int pin) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(userLogin);
			preparedStatement.setString(1, email);
			preparedStatement.setInt(2, pin);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<BankUserDetails> list = new ArrayList<BankUserDetails>();
			if (resultSet.next()) {
				BankUserDetails bankUserDetails = new BankUserDetails();
				bankUserDetails.setId(resultSet.getInt("User_id"));
				bankUserDetails.setName(resultSet.getString("User_name"));
				bankUserDetails.setEmailid(resultSet.getString("User_emailid"));
				bankUserDetails.setAadharnumber(resultSet.getLong("User_adhar_number"));
				bankUserDetails.setPannumber(resultSet.getString("User_PAN_number"));
				bankUserDetails.setMobilenumber(resultSet.getLong("User_mobile_number"));
				bankUserDetails.setGender(resultSet.getString("User_gender"));
				bankUserDetails.setBalance(resultSet.getDouble("balance"));
				return bankUserDetails;
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int userDeposit(String email, double amount) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(deposit);
			preparedStatement.setString(2, email);
			preparedStatement.setDouble(1, amount);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int userWithdraw(String email, double amount) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(withdraw);
			preparedStatement.setString(2, email);
			preparedStatement.setDouble(1, amount);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public double receiveBalance(String email) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(reciveBalance);
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getDouble("balance");
			} else {
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}

	@Override
	public BankUserDetails checkMobileNumber(Long mobilenumber) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(checkmobile);
			preparedStatement.setLong(1, mobilenumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			BankUserDetails bankUserDetails = new BankUserDetails();
			if (resultSet.next()) {
				bankUserDetails.setEmailid(resultSet.getString("User_emailid"));
				bankUserDetails.setId(resultSet.getInt("User_id"));
				return bankUserDetails;
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int setAccountClose(String email) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(closeaccount);
			preparedStatement.setString(1, email);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

}
