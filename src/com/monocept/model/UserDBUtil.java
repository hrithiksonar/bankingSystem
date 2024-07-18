package com.monocept.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import com.monocept.pojos.Transaction;
import com.monocept.pojos.User;

public class UserDBUtil {

	private DataSource dataSource;

	public UserDBUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public User authenticate(String userName, String password) {
		String sql = "select * from user where username=? and password=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userName);
			pstmt.setString(2, password);

			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				return new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7),
						resultSet.getFloat(8));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close(conn, pstmt, resultSet);
		}

		return null;

	}

	private void close(Connection conn, Statement stmt, ResultSet res) {
		try {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
			if (res != null)
				res.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void upload(User user) {
		String sql = "insert into user (username,password,fullname,email, mob,status,balance) VALUES (?,?,?,?,?,?,?);";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getFullname());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getMob());
			pstmt.setString(6, user.getStatus());
			pstmt.setFloat(7, 0);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, null);
		}

	}

	public User getUser(int acc) {
		String sql = "select * from user where accountNo=?";
		User user1 = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, acc);
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				String username = resultSet.getString(2);
				String password = resultSet.getString(3);
				String fullname = resultSet.getString(4);
				String email = resultSet.getString(5);
				String mob = resultSet.getString(6);
				String status = resultSet.getString(7);
				Float amount = resultSet.getFloat(8);

				user1 = new User(acc, username, password, fullname, email, mob, status, amount);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, null);
		}

		return user1;
	}

	public List<User> getUsers() throws SQLException {
		List<User> userList = new ArrayList<>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet res = null;

		try {
			connection = dataSource.getConnection();
			String sql = "select * from user";
			stmt = connection.createStatement();
			res = stmt.executeQuery(sql);

			while (res.next()) {
				int acc = res.getInt(1);
				String username = res.getString(2);
				String password = res.getString(3);
				String fullname = res.getString(4);
				String email = res.getString(5);
				String mob = res.getString(6);
				String status = res.getString(7);
				Float amount = res.getFloat(8);

				userList.add(new User(acc, username, password, fullname, email, mob, status, amount));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, stmt, res);
		}

		return userList;
	}

	public List<Transaction> getTransactions() throws SQLException {
		List<Transaction> transactionList = new ArrayList<>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet res = null;

		try {
			connection = dataSource.getConnection();
			String sql = "select * from transaction";
			stmt = connection.createStatement();
			res = stmt.executeQuery(sql);

			while (res.next()) {
				int acc = res.getInt(1);
				String fullname = res.getString(2);
				String type = res.getString(3);
				int amount = res.getInt(4);
				String date = res.getString(5);
				String id = res.getString(6);
				String time = res.getString(7);

				transactionList.add(new Transaction(acc, fullname, type, amount, id, date, time));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, stmt, res);
		}

		return transactionList;
	}

	public void depositAmount(float amount, int acc) {
		User user = getUser(acc);
		updateAmount(acc, amount + user.getBalance());
		transactionTableUpdate(user, amount, "deposit");

	}

	private void transactionTableUpdate(User user, float amount, String type) {
		String sql = "insert into transaction (accno,fullname,transaction_type,amount,date,transaction_id,time) values(?,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user.getAccountNo());
			pstmt.setString(2, user.getFullname());
			pstmt.setString(3, type);
			pstmt.setFloat(4, amount);
			pstmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
			pstmt.setString(6, String.valueOf(java.util.UUID.randomUUID()));
			pstmt.setTime(7, new java.sql.Time(new Date().getTime()));
			pstmt.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close(conn, pstmt, null);
		}

	}

	public void withdrawAccount(float amount, int acc) {
		User user = getUser(acc);
		float finalAmount = user.getBalance() - amount;
		if (!(finalAmount < 1000)) {
			updateAmount(acc, finalAmount);
			transactionTableUpdate(user, amount, "withdraw");
		}
	}

	public void updateAmount(int acc, float amm) {
		String sql = "UPDATE user SET balance = ? WHERE accountNo = ? ";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setFloat(1, amm);
			pstmt.setInt(2, acc);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, null);
		}
	}

	public void editUser(User user) {
		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = dataSource.getConnection();
			String sql = "update user set username = ?,fullname = ?, email = ?,mob = ? where accountNo = ?;";
			stmt = connection.prepareStatement(sql);

			stmt.setInt(5, user.getAccountNo());
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getFullname());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getMob());

			stmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, stmt, null);
		}
	}

	public List<Transaction> getTransactions(int search) {
		List<Transaction> transactionList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet res = null;

		try {
			connection = dataSource.getConnection();
			String sql = "select * from transaction where accno = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, search);
			res = stmt.executeQuery();

			while (res.next()) {
				int acc = res.getInt(1);
				String fullname = res.getString(2);
				String type = res.getString(3);
				int amount = res.getInt(4);
				String date = res.getString(5);
				String id = res.getString(6);
				String time = res.getString(7);

				transactionList.add(new Transaction(acc, fullname, type, amount, id, date, time));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, stmt, res);
		}

		return transactionList;
	}

	public void changeStatus(User user) {
		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = dataSource.getConnection();
			String sql = "update user set status = ? where accountNo = ?;";
			stmt = connection.prepareStatement(sql);

			if (user.getStatus().equalsIgnoreCase("active"))
				stmt.setString(1, "inactive");
			else
				stmt.setString(1, "active");
			stmt.setInt(2, user.getAccountNo());

			stmt.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(connection, stmt, null);
		}

	}

	public List<Transaction> getTransactionsWithDate(String startDate, String endDate) {
		// TODO Auto-generated method stub
		List<Transaction> transactionList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet res = null;

		try {
			connection = dataSource.getConnection();
			String sql = "SELECT * from transaction where " + "(date BETWEEN ? AND ?) " + "OR (date BETWEEN ? AND ?) "
					+ "OR (date <= ? AND date >= ?)";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, startDate);
			stmt.setString(2, endDate);
			stmt.setString(3, startDate);
			stmt.setString(4, endDate);
			stmt.setString(5, startDate);
			stmt.setString(6, endDate);

			res = stmt.executeQuery();

			while (res.next()) {
				int acc = res.getInt(1);
				String fullname = res.getString(2);
				String type = res.getString(3);
				int amount = res.getInt(4);
				String date = res.getString(5);
				String id = res.getString(6);
				String time = res.getString(7);

				transactionList.add(new Transaction(acc, fullname, type, amount, id, date, time));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, stmt, res);
		}

		return transactionList;
	}

	public Boolean checkPasswordAdmin(String password) {
		String sql = "select * from admin where password=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, password);

			resultSet = pstmt.executeQuery();
			return resultSet.next();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close(conn, pstmt, resultSet);
		}

		return null;

	}

}
