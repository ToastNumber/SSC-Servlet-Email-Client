<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Message Sent</title>
</head>
<body>
	<h1>Message sent!</h1>
	<form action="message-writer.jsp" method="get">
		<input type="submit" value="Compose another email"/>
	</form>
	
	<form action="Logout" method="get">
		<input type="submit" value="Logout"/>
	</form>
</body>
</html>