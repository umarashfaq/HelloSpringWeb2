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
	<h1>Add New Dish</h1>
	
	<form:form>
		<label>Title</label><form:input path="title"/><br/>
		<label>Cost</label>
		<form:select path="cost">
			<form:option value="500">500</form:option>
			<form:option value="1000">1000</form:option>
			<form:option value="2000">2000</form:option>
			<form:option value="3000">3000</form:option>
		</form:select><br/>
		<label>Price</label><form:input path="price"/><br/>
		<label>Description</label><form:input path="description"/><br/>
		<input type="submit"/>
	</form:form>
</body>
</html>