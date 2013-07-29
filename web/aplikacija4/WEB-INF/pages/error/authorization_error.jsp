<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common_functions.jsp"%>

<!doctype html>
<html>
<head>
<%= head() %>
</head>
<body>
	<%= headder(request) %>
	
	<h1>Authorization Error</h1>
	
	<p class="text-error">You are not authirized for this action.</p>
</body>
</html>