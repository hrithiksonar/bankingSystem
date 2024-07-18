<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.monocept.pojos.User"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Transaction</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
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
	style="background-image: radial-gradient(circle farthest-corner at 22.4% 21.7%, rgba(4, 189, 228, 1) 0%, rgba(2, 83, 185, 1) 100.2%);">
	<div class="mx-2 my-2">
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
	</div>
	<%
		User user = (User) session.getAttribute("user");
	%>
	<div class="container mt-5">
		<h2 class="mt-5">Welcome !!! ${user.fullname}.</h2>
		<br>
		<h2>Balance : ${user.balance}</h2>
		<c:if test="${empty user}">
			<c:redirect url="login.jsp"></c:redirect>
		</c:if>
		<span>
			<h3 class="mt-3">Transaction :</h3>
			<form action="UserController" method="post">
				<div class="form-check form-check-inline my-3">
					<input type="hidden" name="acc" value="${user.accountNo}" /> <input
						class="form-check-input" type="radio" name="userOperation"
						id="flexRadioDefault1" value="deposit" checked> <label
						class="form-check-label mr-4" for="flexRadioDefault1">Deposit
					</label> <br> <input class="form-check-input" type="radio"
						name="userOperation" id="flexRadioDefault2" value="withdraw">
					<label class="form-check-label" for="flexRadioDefault2">Withdraw
					</label>
				</div>
				<div class="text-white">Note : Your account should have
					atleast 1000 Rs. after withdraw</div>
				<div class="form-group row">
					<h3 class="ml-3 mt-3">Amount :</h3>
					<input type="number" min="1" class="form-control col-6  ml-5 mt-3"
						id="exampleInputEmail1" aria-describedby="emailHelp"
						placeholder="Enter Amount" name="amount">
				</div>
				<button type="submit" class="btn btn-primary">Submit</button>
			</form>
		</span>

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