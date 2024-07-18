<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<style>
.switch {
	position: relative;
	display: inline-block;
	width: 60px;
	height: 34px;
}

.switch input {
	opacity: 0;
	width: 0;
	height: 0;
}

.slider {
	position: absolute;
	cursor: pointer;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: #ccc;
	-webkit-transition: .4s;
	transition: .4s;
}

.slider:before {
	position: absolute;
	content: "";
	height: 26px;
	width: 26px;
	left: 4px;
	bottom: 4px;
	background-color: white;
	-webkit-transition: .4s;
	transition: .4s;
}

input:checked+.slider {
	background-color: #2196F3;
}

input:focus+.slider {
	box-shadow: 0 0 1px #2196F3;
}

input:checked+.slider:before {
	-webkit-transform: translateX(26px);
	-ms-transform: translateX(26px);
	transform: translateX(26px);
}

/* Rounded sliders */
.slider.round {
	border-radius: 34px;
}

.slider.round:before {
	border-radius: 50%;
}
</style>
<title>User Details</title>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
	style="background-image: linear-gradient(to right, #ff512f, #f09819);">
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
	<%
		if (session.getAttribute("admin") == null) {
		response.sendRedirect("login.jsp");
	}
	%>
	<div class="container pb-3">
		<h1 class="text-center my-3 pt-3">All User Details</h1>
		<div class="container mt-5">

			<form action="AdminController">
				<div class="row">
					<div class="col-6 px-0">
						<input type="text" name="account"
							class="form-control border-dark rounded-0 mx-4"
							placeholder="Search Account Number" />
					</div>
					<input type="hidden" name="op" value="searchuser">
					<div class="col-1 px-0">
						<button type="submit" class="btn btn-primary rounded-0 mx-4">
							<i class="fa fa-search"></i>
						</button>
					</div>
					<div class="col text-end">
						<c:url var="reload" value="AdminController">
							<c:param name="op" value="users"></c:param>
						</c:url>
						<button type="reset" class="btn btn-primary rounded-0 mx-4"
							onclick="window.location.href='${reload}'">
							<i class="fa fa-refresh"></i>
						</button>
					</div>
				</div>
			</form>
		</div>

		<table class="table table-hover mt-5">
			<thead>
				<tr class="text-center">
					<th scope="col">accountNo</th>
					<th scope="col">userName</th>
					<th scope="col">fullname</th>
					<th scope="col">Email</th>
					<th scope="col">Mobile No</th>
					<th scope="col">Edit</th>
					<th scope="col">Status</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${fn:length(userlist) != 1}">
						<c:forEach var="user" items="${userList}">
							<c:url var="editlink" value="AdminController">
								<c:param name="op" value="load"></c:param>
								<c:param name="id" value="${user.accountNo}"></c:param>
							</c:url>
							<c:url var="togglelink" value="AdminController">
								<c:param name="op" value="toggle"></c:param>
								<c:param name="id" value="${user.accountNo}"></c:param>
							</c:url>

							<tr class="text-center text-white">
								<td>${user.accountNo}</td>
								<td>${user.username}</td>
								<td>${user.fullname}</td>
								<td>${user.email}</td>
								<td>${user.mob}</td>
								<td><a href="${editlink}"><button
											class="btn btn-success mx-3">
											<i class="fa fa-edit"></i>
										</button></a></td>
								<c:choose>
									<c:when test="${user.status == 'active' }">
										<td><label class="switch"> <input type="checkbox"
												onclick="window.location.href='${togglelink}'" checked>
												<span class="slider round"></span>
										</label></td>
									</c:when>
									<c:otherwise>
										<td><label class="switch"> <input type="checkbox"
												onclick="window.location.href='${togglelink}'"> <span
												class="slider round"></span>
										</label></td>
									</c:otherwise>
								</c:choose>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach var="user" items="${userlist}">
							<tr class="text-center text-white bg-primary">
								<td>${user.accountNo}</td>
								<td>${user.username}</td>
								<td>${user.fullname}</td>
								<td>${user.email}</td>
								<td>${user.mob}</td>
								<td><a href="${editlink}"><button
											class="btn btn-success mx-3">
											<i class="fa fa-edit"></i>
										</button></a></td>
								<c:choose>
									<c:when test="${user.status == 'active' }">
										<td><label class="switch"> <input type="checkbox"
												onclick="window.location.href='${togglelink}'" checked>
												<span class="slider round"></span>
										</label></td>
									</c:when>
									<c:otherwise>
										<td><label class="switch"> <input type="checkbox"
												onclick="window.location.href='${togglelink}'"> <span
												class="slider round"></span>
										</label></td>
									</c:otherwise>
								</c:choose>
							</tr>
						</c:forEach>
						<c:forEach var="user" items="${userList}">
							<c:if test="${user.accountNo != account}">
								<tr class="text-center">
									<td>${user.accountNo}</td>
									<td>${user.username}</td>
									<td>${user.fullname}</td>
									<td>${user.email}</td>
									<td>${user.mob}</td>
									<td><a href="${editlink}"><button
												class="btn btn-success mx-3">
												<i class="fa fa-edit"></i>
											</button></a></td>
									<c:choose>
										<c:when test="${user.status == 'active' }">
											<td><label class="switch"> <input
													type="checkbox"
													onclick="window.location.href='${togglelink}'" checked>
													<span class="slider round"></span>
											</label></td>
										</c:when>
										<c:otherwise>
											<td><label class="switch"> <input
													type="checkbox"
													onclick="window.location.href='${togglelink}'"> <span
													class="slider round"></span>
											</label></td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:if>
						</c:forEach>
					</c:otherwise>
				</c:choose>

			</tbody>
		</table>
	</div>
</body>
</html>