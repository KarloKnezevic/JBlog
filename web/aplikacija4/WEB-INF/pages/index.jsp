<%@page import="hr.fer.zemris.java.hw13.model.BlogUser"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common_functions.jsp"%>

<!doctype html>
<html>
<head>
<%= head() %>
</head>
<body>

	<% List<BlogUser> list = (List<BlogUser>) request.getAttribute("users");  %>

	<%= headder(request, list) %>
	
	<%= welcome(request) %>

	<h2>Existing authors</h2>
	
	<% if (list != null && !list.isEmpty()) { %> 
	
	<ul>
	<% for (BlogUser u : list) { %>
	<li><i class="icon-search"></i>
	<a
		href=<% out.write("\"/aplikacija4/servleti/author/" + u.getNick()
							+ "\""); %>>
		<%
			out.write(u.getNick());
		%>
	</a></li>
	<%
		}
		}
	%>
	</ul>


</body>
</html>