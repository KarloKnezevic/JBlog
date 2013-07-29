<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page
	import="hr.fer.zemris.java.hw13.model.BlogEntry,hr.fer.zemris.java.hw13.model.BlogComment"%>
<%@page import="java.util.List"%>
<%@ include file="common_functions.jsp"%>


<!doctype html>
<html>
<head>
<%= head() %>
</head>
<body>
	<%= headder(request) %>

	<% BlogEntry blogEntry = (BlogEntry)request.getAttribute("blogEntry"); %>

	<% if(blogEntry==null) { %>
	<h1>Not Found</h1>
	<p class="text-error">Requested blog entry not found. Please, use
		predefined entries links.</p>
	<% } else { %>

	<h1><%= blogEntry.getTitle() %></h1>

	<p class="text-info"><%= blogEntry.getText() %></p>
	<% if(!blogEntry.getComments().isEmpty()) { %>
	<table class="table table-striped">
		<tr>
			<th><i class="icon-user"></i>User</th>
			<th><i class="icon-time"></i>Posted</th>
			<th><i class="icon-comment"></i>Comment</th>
		</tr>
		<% for(BlogComment c : blogEntry.getComments()) { %>
		<tr>
			<td><%= c.getUsersEMail() %></td>
			<td><%= c.getPostedOn() %></td>
			<td><%= c.getMessage() %></td>
		</tr>
		<% } %>
	</table>
	<% } else { %>
	<p class="text-warning">
		<i class=" icon-info-sign"></i>No comment for this entry.
	</p>
	<% } 
	  
	  String email;
					if (!Auth.isLoggedIn(request)) { %>
	<p class="text-error">It is highly recommended to be logged in for
		commenting.</p>
	<% 
						email = "Anonymous";
					} else { 
						email = request.getSession().getAttribute("current.user.email").toString();
						
					 } %>

	<form class="form-horizontal" action="/aplikacija4/comment"
		method="POST">
		<div class="control-group">
			<label class="control-label" for="comment"><%= email %></label>
			<div class="controls">
				<textarea rows="5" cols="10" id="comment" name="comment"
					placeholder="Comment this blog..."></textarea>
			</div>
		</div>
		<input type="hidden" name="eid"
			value="<% out.write(blogEntry.getId().toString()); %>"> <input
			type="hidden" name="email" value="<% out.write(email); %>">
			<input type="hidden" name="nick" value="<% out.write(blogEntry.getCreator().getNick()); %>">
		<div class="control-group">
			<div class="controls">
				<i class="icon-ok-sign"></i>
				<button type="submit" class="btn btn-success">Comment</button>
			</div>
		</div>
	</form>

	<% } %>



</body>
</html>