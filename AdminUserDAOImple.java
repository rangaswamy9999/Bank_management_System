package com.RMe_Bank.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminUserDAOImple implements AdminUserDAO {
	private static final String url = "jdbc:mysql://localhost:3306/teca64_advancedjava?user=root&password=12345";
	private static final String select = "select * from admin_details where Admin_EmailId=? and Admin_Password=?";

	@Override
	public boolean getAdminDetailsFormEmailIdAndPassword(String emailid, String password) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(select);
			preparedStatement.setString(1, emailid);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
