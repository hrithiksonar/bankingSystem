<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit User</title>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
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
<body class="bg-dark">
	<div class="container bg-light pb-5">
		<h1 class="text-center my-3">EDIT USER</h1>
		<form action="AdminController">
			<input type="hidden" name="id" value="${user.accountNo}" />
			<div class="form-group my-3">
				<label>Enter User Name : </label> <input type="text"
					value="${user.username}" class="form-control my-3" name="userName"
					required="required">
			</div>

			<div class="form-group my-3">
				<label>Enter Full Name :</label> <input type="text"
					value="${user.fullname}" class="form-control my-3" name="fullName">
			</div>

			<div class="form-group my-3">
				<label>Enter Email : </label> <input type="text"
					value="${user.email}" class="form-control my-3" name="email">
			</div>
			<div class="form-group my-3">
				<label>Enter Mobile Number : </label> <input type="text"
					value="${user.mob}" class="form-control my-3" name="mobileNo">
			</div>
			<input type="hidden" name="op" value="edit">
			<button type="submit" class="btn btn-success">Save</button>
		</form>
	</div>
</body>
</html>