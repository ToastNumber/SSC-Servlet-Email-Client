<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Servlet Email Client</title>
</head>
<body>
	<%
		String username = request.getParameter("username");
		if (username == null) username = "";
		
		String password = request.getParameter("password");
		if (password == null) password = "";
	%>

	<form action="LoginController" method="get">
		<input name="username" placeholder="asd@xyz.com"
			value=<%= username %> ><br> <input
			type="password" name="password" placeholder="password"
			value=<%= password %> ><br> <input type="submit"
			value="Login">
	</form>
</body>
</html>