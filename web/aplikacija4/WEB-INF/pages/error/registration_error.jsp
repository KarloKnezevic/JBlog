<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common_functions.jsp"%>

<!doctype html>
<html>
<head>
<%= head() %>
</head>
<body>
	<%= headder(request) %>
	
	<h1>Registration Error</h1>
	
	<p class="text-error">Please, fill all fields. Mail must be valid and nick unique.</p>
</body>
</html>