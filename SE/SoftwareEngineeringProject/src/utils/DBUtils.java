package utils;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtils {
	private static Connection conn;

	public static Connection getConnection() {
		Logger logger = Logger.getLogger(DBUtils.class.getName());
		logger.log(Level.INFO,"No open connection");
		if (conn != null)
			return conn;
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		String url = "mysql@softengproject.cspvcqknb3vj.eu-central-1.rds.amazonaws.com:3306";
		String username = "fur_seal_admin";
		String password = "*PecVE3K!nr85HW{";

		try {
			logger.log(Level.INFO,"Attempting to connection to: " + url + " with user: " + username + " password: " +password);
			conn = DriverManager.getConnection(url, username, password);
		}

		catch (java.sql.SQLException e) {
			logger.log(Level.SEVERE,"Connection not opened");
			System.out.println(e.getMessage());
		}

		return conn;
	}

	public static void closeConnection(Connection toBeClosed) {
		if (toBeClosed == null)
			return;

		try {
			toBeClosed.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
