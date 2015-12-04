<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Compose Email</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/SendController" method="post">
		<input name="to" placeholder="To"> <br>
		<input name="subject" placeholder="Subject"> <br> 
		<textarea name="content" placeholder="Put the email content here"></textarea>
		<input type="submit" value="Send">
	</form>
	
	<form action="Logout" method="post">
		<input type="submit" value="Logout"/>
	</form>
</body>
</html>