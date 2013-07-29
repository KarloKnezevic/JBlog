<%@page import="hr.fer.zemris.java.hw13.model.BlogEntry"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common_functions.jsp"%>

<!doctype html>
<html>
<head>
<%= head() %>
</head>
<body>
	<%= headder(request) %>
	
	<h1>Welcome to <% out.write((String)request.getAttribute("nick")); %> blog page.</h1>
	
	<p class="text-info text-center">
		Choose any topic and read it. <% 
		
			if (Auth.isLoggedIn(request)) {
				%> You are allowed to comment any topic.<%	
				if (Auth.isOwner(request, (String)request.getAttribute("nick"))) {
					%> You are owner of this blog and only for you is allowed to create New blog entry and Edit existing blog entries.<%
				}
			} else {
				%> Please, login or register to comment.<%	
			}
		
		%>
	</p>
	
	
	
	<% 
		List<BlogEntry> blogEntries = (List<BlogEntry>) request.getAttribute("blogEntries");
		boolean isLoggedIn = Auth.isLoggedIn(request);
		boolean isOwner = Auth.isOwner(request, (String)request.getAttribute("nick"));
	
		if (blogEntries == null || blogEntries.isEmpty()) {
			%> <p class="text-info text-left"> None blog entries. </p> <%
		} else {
			%> <table  class="table table-striped"> 
				<tr>
					<th>Title</th>
					<th>Created At</th>
					<th>Last Modified At</th>
					<% if (isOwner) {
						%> <th>Edit</th> <%
						}
					%>
				</tr>
			
			<%
			
				for (BlogEntry blogEntry : blogEntries) {
					%> 
						<tr>
							<td>
								<i class="icon-search"></i>
								<a href="/aplikacija4/servleti/author/<% out.write((String)request.getAttribute("nick")+"/"+blogEntry.getId().toString()); %>">
								<% out.write(blogEntry.getTitle()); %>
								</a>
							</td>
							<td>
								<i class="icon-time"></i><% out.write(blogEntry.getCreatedAt().toString()); %>
							</td>
							<td>
								<i class="icon-time"></i><% out.write(blogEntry.getLastModifiedAt().toString()); %>
							</td>
							<% if (isOwner) {
								%>
									<td>
										<a href="/aplikacija4/servleti/author/<% out.write((String)request.getAttribute("nick"));%>/edit/<% out.write(blogEntry.getId().toString()); %>"><i class="icon-edit"></i><button class="btn btn-warning" type="button">Edit</button></a>
									</td>
								 <%
							}
							%>
						</tr>
					<%
				}
			%> </table> <%
		}
	%>
		
	
	
	<% if (isOwner) {
		%> Create <a href="/aplikacija4/servleti/author/<% out.write((String)request.getAttribute("nick"));%>/new"><i class="icon-pencil"></i><button class="btn btn-danger" type="button">New</button></a> Entry.  <%
	}
		
		%>
	
	
	
</body>
</html>