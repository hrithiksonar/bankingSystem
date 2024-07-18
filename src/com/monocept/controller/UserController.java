package com.monocept.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.monocept.model.UserDBUtil;
import com.monocept.pojos.User;
import com.mysql.cj.Session;

@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserController() {
		super();

	}

	@Resource(name = "jdbc/webstudent")
	private DataSource dataSource;
	private UserDBUtil userDBUtil;

	@Override
	public void init() throws ServletException {
		super.init();
		userDBUtil = new UserDBUtil(dataSource);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userOp = request.getParameter("userOperation");
		if (userOp == null) {
			userOp = "auth";
		}
		switch (userOp) {
		case "auth":
			try {
				authenticate(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "logout":
			HttpSession session = request.getSession(false);
			session.invalidate();
			response.sendRedirect("MainController");
			break;
		case "registerForm":
			registerForm(request, response);
			break;
		case "register":
			register(request, response);
			break;
		case "transaction":
			transaction(request, response);
			break;
		case "passbook":
			passbook(request, response);
			break;
		case "withdraw":
			withdraw(request, response);
			break;
		case "deposit":
			deposit(request, response);
			break;

		}

	}

	private void deposit(HttpServletRequest request, HttpServletResponse response) {
		float amount = Float.parseFloat(request.getParameter("amount"));
		String accNo = request.getParameter("acc");
		request.setAttribute("amount", amount);
		HttpSession session = request.getSession();
		userDBUtil.depositAmount(amount, Integer.parseInt(accNo));
		session.setAttribute("user", userDBUtil.getUser(Integer.parseInt(accNo)));
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher("userHome.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void withdraw(HttpServletRequest request, HttpServletResponse response) {
		String accNo = request.getParameter("acc");
		float amount = Float.parseFloat(request.getParameter("amount"));
		userDBUtil.withdrawAccount(amount, Integer.parseInt(accNo));
		request.setAttribute("amount", amount);
		HttpSession session = request.getSession();
		session.setAttribute("user", userDBUtil.getUser(Integer.parseInt(accNo)));
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher("userHome.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void passbook(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String accNo = request.getParameter("acc");

		System.out.println(accNo);
		request.setAttribute("acc", Integer.parseInt(accNo));
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher("passbook.jsp");
			dispatcher.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void transaction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect("transaction.jsp");
	}

	private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String username = request.getParameter("userName");
		String password = request.getParameter("password");
		String fullName = request.getParameter("firstName") + " " + request.getParameter("lastName");
		String email = request.getParameter("email");
		String mob = request.getParameter("mobileNumber");
		User user = new User(username, password, fullName, email, mob, "inactive");
		try {
			userDBUtil.upload(user);
			response.sendRedirect("MainController");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void registerForm(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		try {
			response.sendRedirect("registration.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void authenticate(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		User user = null;
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		user = userDBUtil.authenticate(userName, password);
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			response.sendRedirect("userHome.jsp");

		} else {
			response.sendRedirect("MainController");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
