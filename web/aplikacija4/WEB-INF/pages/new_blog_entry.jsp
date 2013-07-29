<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common_functions.jsp"%>

<!doctype html>
<html>
<head>
<%= head() %>
</head>
<body>
	<%= headder(request) %>
	
	<form class="form-horizontal" action="/aplikacija4/new" method="POST">
    <div class="control-group">
    <label class="control-label" for="title">Title</label>
    <div class="controls">
    <input type="text" id="title" name="title" placeholder="Title">
    </div>
    </div>
    <div class="control-group">
    <label class="control-label" for="text">Text</label>
    <div class="controls">
    <textarea rows="5" cols="10" id="inputPassword" name="text" placeholder="Text"></textarea>
    </div>
    </div>
    <input type="hidden" name="eid" value="new">
    <div class="control-group">
    <div class="controls">
    <i class="icon-ok-sign"></i><button type="submit" class="btn btn-success">Create Entry</button>
    <a href="/aplikacija4/servleti/author/<% out.write((String)request.getSession().getAttribute("current.user.nick")); %>"><i class="icon-remove-circle"></i><button class="btn btn-danger" type="button">Cancel</button></a>
    </div>
    </div>
    </form>
	
</body>
</html>