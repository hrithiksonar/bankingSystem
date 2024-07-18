<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.monocept.pojos.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Passbook</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css">
<script type="text/javascript">
	function preventBack() {
		window.history.forward();
	}
	setTimeout("preventBack()", 0);
	window.onunload = function() {
		null
	};
</script>
</head>
<body
	style="background-image: linear-gradient(to right, #434343 0%, black 100%);">
	<div class="container mt-3">
		<form action="UserController">
			<div class="row">
				<div class="col-6 text-left">
					<div class="previous">
						<a href="userHome.jsp"><button type="button"
								class="btn btn-light">Go Back</button></a>
					</div>
				</div>
				<div class="col-6 text-right">
					<div class="next">
						<input type="hidden" name="userOperation" value="logout" /> <input
							type="submit" class="btn btn-danger" value="Logout">
					</div>
				</div>
			</div>
		</form>
	</div>
	<div class="mx-2 my-2">
		<%
			User user = (User) session.getAttribute("user");
		%>
		<c:url var="emptyLink" value="login.jsp">
			<c:param name="userSession" value="empty"></c:param>
		</c:url>
		<c:if test="${empty user}">
			<c:redirect url="${emptyLink}"></c:redirect>
		</c:if>
		<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
			url="jdbc:mysql://localhost:3306/webstudent" user="webstudent"
			password="webstudent" />
		<sql:query dataSource="${snapshot}" var="result">
         SELECT * from transaction where accno="${user.accountNo}";
      </sql:query>
		<div class="container">
			<h3 class="text-center text-white">Balance : ${user.balance} INR</h3>
			<table class="table table-light table-hover mt-4">
				<thead class="thead-dark">
					<tr class="text-center">
						<th>Transaction_type</th>
						<th>Amount(in INR)</th>
						<th>Date</th>
						<th>Time</th>
						<th>Transaction_id</th>

					</tr>
				</thead>
				<tbody>
					<c:if test="${empty result.rows}">
						<c:redirect url="userHome.jsp"></c:redirect>
					</c:if>
					<c:forEach var="row" items="${result.rows}">
						<tr class="text-center">
							<c:if test="${row.transaction_type == 'deposit'}">
								<tr class="text-center text-success">
							</c:if>
							<c:if test="${row.transaction_type == 'withdraw'}">
								<tr class="text-center text-primary">
							</c:if>
							<td><c:out value="${row.transaction_type}" /></td>
							<td><c:out value="${row.amount}" /></td>
							<td><c:out value="${row.date}" /></td>
							<td><c:out value="${row.time}" /></td>
							<td><c:out value="${row.transaction_id}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</div>

	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
		integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
		integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
		crossorigin="anonymous"></script>
</body>
</html>