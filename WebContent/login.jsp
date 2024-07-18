<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
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
<body
	style="background: linear-gradient(to right, rgba(106, 17, 203, 1), rgba(37, 117, 252, 1))">
	<section class="vh-100 gradient-custom">
		<div class="container py-5 h-100">
			<%
				if (session.getAttribute("user") != null) {
				response.sendRedirect("userHome.jsp");
			}

			if (session.getAttribute("admin") != null) {
				response.sendRedirect("adminHomepage.jsp");
			}
			%>
			<div
				class="row d-flex justify-content-center align-items-center h-100">
				<div class="col-12 col-md-8 col-lg-6 col-xl-5">
					<div class="card bg-dark text-white" style="border-radius: 1rem;">
						<div class="card-body p-4 text-center">


							<img alt="bank logo" src="bank-logo.png" width="45%" height="120">
							<div class="mt-md-4 pb-5">
								<h2 class="fw-bold mb-2 text-uppercase">Login</h2>

								<form action="MainController">
									<div class="form-outline form-white mb-4 mt-4">
										<input type="text" name="userName"
											class="form-control form-control-lg"
											placeholder="Enter UserName">
									</div>

									<div class="form-outline form-white mb-3">
										<input type="password" name="password"
											class="form-control form-control-lg"
											placeholder="Enter Password" />
									</div>

									<p class="small mb-3 pb-lg-2">
										<a class="text-white-50" href="#">Forgot password?</a>
									</p>

									<button class="btn btn-outline-light btn-lg px-5" type="submit">Login</button>
									<input type="hidden" name="op" value="admin">
								</form>
							</div>
							<div>
								<p class="mb-0">
									Don't have an account? <a href="registration.jsp"
										class="text-white-50 fw-bold">Sign Up</a>
								</p>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>