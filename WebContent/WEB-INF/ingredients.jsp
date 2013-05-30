<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Ingredients</h1>
	
	<table>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Dish</th>
		</tr>
		
	<c:forEach var="i" items="${ingredients }">
		<tr>
			<td>${i.id }</td>
			<td>${i.name }</td>
			<td><a href="/dishes/${i.dish.id }">${i.dish.title }</a></td>
		</tr>
	</c:forEach>
	</table>
</body>
</html>