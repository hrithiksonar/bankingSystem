<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Home Page</title>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
</head>
<body
	style="background-image: linear-gradient(to right, #141e30, #243b55);">
	<form action="AdminController" method="post">
		<div class="container pt-3 text-end">
			<input type="hidden" name="op" value="logout" /> <input
				type="submit" class="btn btn-danger" value="Logout">
		</div>
	</form>
	<%
		if (session.getAttribute("admin") == null) {
		response.sendRedirect("login.jsp");
	}
	%>
	<div class="container mt-4">
		<c:url var="userlink" value="AdminController">
			<c:param name="op" value="users"></c:param>
		</c:url>
		<c:url var="translink" value="AdminController">
			<c:param name="op" value="trans"></c:param>
		</c:url>
		<div class="row row-cols-1 row-cols-md-2 g-4">
			<div class="col">
				<div class="card bg-light">
					<img src="user.jpg" class="card-img-top "
						alt="Hollywood Sign on The Hill"
						style="width: 100%; height: 18rem;" />
					<div class="card-body text-center">
						<h5 class="card-title">USERS</h5>
						<p class="card-text">This is a longer card with supporting
							text below as a natural lead-in to additional content. This
							content is a little bit longer.</p>
						<a href="${userlink}" class="btn btn-primary btn-dark">Show
							Users</a>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="card bg-dark text-white">
					<img src="transaction.jpg" class="card-img-top"
						alt="Palm Springs Road" style="width: 100%; height: 18rem;" />
					<div class="card-body text-center">
						<h5 class="card-title">TRANSACTIONS</h5>
						<p class="card-text">This is a longer card with supporting
							text below as a natural lead-in to additional content. This
							content is a little bit longer.</p>
						<a href="${translink}" class="btn btn-primary btn-info">Show
							Transactions</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>