package com.monocept.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.monocept.pojos.Transaction;
import com.monocept.pojos.User;

@WebServlet("/AdminController")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminController() {
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

		String op = request.getParameter("op");

		if (op == null) {
			op = "admin";
		}

		switch (op) {
		case "admin":
			authenticate(request, response);
			break;
		case "users":
			showUser(request, response);
			break;
		case "trans":
			showTrans(request, response);
			break;
		case "edit":
			edit(request, response);
			break;
		case "load":
			load(request, response);
			break;
		case "toggle":
			toggle(request, response);
			break;
		case "searchuser":
			searchUser(request, response);
			break;
		case "searchtrans":
			searchTrans(request, response);
			break;
		case "searchdate":
			searchDate(request, response);
			break;
		case "logout":
			HttpSession session = request.getSession(false);
			session.invalidate();
			response.sendRedirect("MainController");
			break;

		}

	}

	private void searchDate(HttpServletRequest request, HttpServletResponse response) {

		String startDate = request.getParameter("startdate");
		String endDate = request.getParameter("enddate");

		List<Transaction> transaction = userDBUtil.getTransactionsWithDate(startDate, endDate);
		request.setAttribute("transactionList", transaction);

		RequestDispatcher dispatcher = request.getRequestDispatcher("adminTransactionPage.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private void searchTrans(HttpServletRequest request, HttpServletResponse response) {
		if (!request.getParameter("search").isEmpty()) {
			System.out.println(request.getParameter("search"));
			int search = Integer.parseInt(request.getParameter("search"));
			List<Transaction> transaction = userDBUtil.getTransactions(search);
			request.setAttribute("transactionList", transaction);

			RequestDispatcher dispatcher = request.getRequestDispatcher("adminTransactionPage.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			searchDate(request, response);
		}

	}

	private void searchUser(HttpServletRequest request, HttpServletResponse response) {
		int accNo = Integer.parseInt(request.getParameter("account"));
		User presentUser = userDBUtil.getUser(accNo);
		request.setAttribute("account", accNo);
		List<User> userlist = new ArrayList<>();
		userlist.add(presentUser);
		request.setAttribute("userlist", userlist);
		List<User> users;
		try {
			users = userDBUtil.getUsers();
			request.setAttribute("userList", users);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("adminUserPage.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void toggle(HttpServletRequest request, HttpServletResponse response) {
		int accNo = Integer.parseInt(request.getParameter("id"));
		User presentUser = userDBUtil.getUser(accNo);
		userDBUtil.changeStatus(presentUser);
		showUser(request, response);
	}

	private void load(HttpServletRequest request, HttpServletResponse response) {
		int accNo = Integer.parseInt(request.getParameter("id"));
		User presentUser = userDBUtil.getUser(accNo);
		RequestDispatcher dispatcher = request.getRequestDispatcher("editUser.jsp");
		request.setAttribute("user", presentUser);
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void edit(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String fullName = request.getParameter("fullName");
		String email = request.getParameter("email");
		String mob = request.getParameter("mobileNo");
		int accountNo = Integer.parseInt(request.getParameter("id"));
		User user = new User(accountNo, userName, fullName, email, mob);
		userDBUtil.editUser(user);
		showUser(request, response);
	}

	private void showTrans(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			List<Transaction> transaction = userDBUtil.getTransactions();

			request.setAttribute("transactionList", transaction);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("adminTransactionPage.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showUser(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			List<User> users = userDBUtil.getUsers();
			request.setAttribute("userList", users);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("adminUserPage.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void authenticate(HttpServletRequest request, HttpServletResponse response) {
		String password = request.getParameter("password");
		Boolean admin = userDBUtil.checkPasswordAdmin(password); /* (password.equalsIgnoreCase("123")); */
		if (admin) {
			HttpSession session = request.getSession();
			session.setAttribute("admin", admin);
			try {
				response.sendRedirect("adminHomepage.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			try {
				response.sendRedirect("login.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
