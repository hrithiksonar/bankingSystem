<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

</head>
<body
	style="background-image: linear-gradient(to right, #ff512f, #f09819);">
	<%
		if (session.getAttribute("admin") == null) {
		response.sendRedirect("login.jsp");
	}
	%>
	<div class="mx-2 my-2">
		<div class="container mt-3">
			<form action="AdminController" method="post">
				<div class="row">
					<div class="col-6 text-left">
						<div class="previous">
							<a href="adminHomepage.jsp"><button type="button"
									class="btn btn-light">Go Back</button></a>
						</div>
					</div>
					<div class="col-6 text-end">
						<div class="next">
							<input type="hidden" name="op" value="logout" /> <input
								type="submit" class="btn btn-danger" value="Logout">
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>

	<div class="container pb-3">
		<h1 class="text-center text-dark my-4">
			<u>User Transactions</u>
		</h1>
		<div class="container ">
			<form action="AdminController">
				<div class="row">
					<div class="col-4 px-0">
						<input type="text" name="search"
							class="form-control border-dark rounded-0"
							placeholder="Search Account Number" />
					</div>
					<div class="col-1 px-0">
						<a href="AdminController"><button type="submit"
								class="btn btn-dark rounded-0">
								<i class="fa fa-search"></i>
							</button></a> <input type="hidden" name="op" value="searchtrans">
					</div>
					<div class="col-2 px-0 mx-1">
						<input type="text" name="startdate"
							class="form-control border-dark rounded-0"
							placeholder="Start Date[yyyy-mm-dd]" />
					</div>
					<div class="col-2 px-0 mx-1">
						<input type="text" name="enddate"
							class="form-control border-dark rounded-0"
							placeholder="End Date[yyyy-mm-dd]" />
					</div>
					<div class="col-1 px-0">
						<a href="AdminController"><button type="submit"
								class="btn btn-dark rounded-0">
								<i class="fa fa-search"></i>
							</button></a>
					</div>
					<div class="col text-end">
						<c:url var="reload" value="AdminController">
							<c:param name="op" value="trans"></c:param>
						</c:url>
						<button type="reset" class="btn btn-dark rounded-0 mx-4"
							onclick="window.location.href='${reload}'">
							<i class="fa fa-refresh"></i>
						</button>
					</div>
				</div>
			</form>
		</div>
		<table class="table table-dark table-hover mt-4">
			<thead>
				<tr>
					<th scope="col" class="text-center">AccountNo</th>
					<th scope="col" class="text-center">Type</th>
					<th scope="col" class="text-center">Amount</th>
					<th scope="col" class="text-center">Date</th>
					<th scope="col" class="text-center">Time</th>
					<th scope="col" class="text-center">Transaction_id</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach var="trans" items="${transactionList}">
					<tr class="text-center">
						<td>${trans.accountNo}</td>
						<td>${trans.transaction_type}</td>
						<td>${trans.amount}</td>
						<td>${trans.id}</td>
						<td>${trans.time}</td>
						<td>${trans.date}</td>

					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>
</body>
</html>