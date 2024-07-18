package com.monocept.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MainController")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MainController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String command = request.getParameter("userName");

		if (command == null) {
			command = "login";
		} else {
			if (!command.equalsIgnoreCase("admin")) {
				command = "user";
			}
		}

		switch (command) {
		case "admin":
			adminLogin(request, response);
			break;
		case "login":
			login(request, response);
			break;
		case "user":
			userLogin(request, response);
			break;
		}

	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect("login.jsp");
	}

	private void adminLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("AdminController");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}

	}

	private void userLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("UserController");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
