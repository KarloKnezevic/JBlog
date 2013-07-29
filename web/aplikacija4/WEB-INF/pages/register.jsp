<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common_functions.jsp"%>

<!doctype html>
<html>
<head>
<%= head() %>
</head>
<body>
	<table class="table table-striped">
	<tr>
		<td class="text-left"><i class="icon-leaf"></i>Registration</td>
	</tr>
	</table>
	
	<h1>Registration</h1>
	<p class="text-info text-left">
			Please, fill all fields to register. Email must be valid and nick unique.
	</p>


	<form class="form-horizontal" action="/aplikacija4/servleti/register" method="POST">
    <div class="control-group">
    <label class="control-label" for="firstName">First name</label>
    <div class="controls">
    <input type="text" id="firstName" name="firstName" placeholder="First name">
    </div>
    </div>
    <div class="control-group">
    <label class="control-label" for="lastName">Last name</label>
    <div class="controls">
    <input type="text" id="lastName" name="lastName" placeholder="Last name">
    </div>
    </div>
    <div class="control-group">
    <label class="control-label" for="email">Email</label>
    <div class="controls">
    <input type="text" id="email" name="email" placeholder="Email">
    </div>
    </div>
    <div class="control-group">
    <label class="control-label" for="nick">Nick</label>
    <div class="controls">
    <input type="text" id="nick" name="nick" placeholder="Nick">
    </div>
    </div>
    <div class="control-group">
    <label class="control-label" for="password">Password</label>
    <div class="controls">
    <input type="password" id="password" name="password" placeholder="Password">
    </div>
    </div>
    <input type="hidden" name="register" value="true">
    <div class="control-group">
    <div class="controls">
    <i class="icon-leaf"></i><button type="submit" class="btn-primary">Register</button>
    <a href="/aplikacija4"><i class="icon-home"></i><button class="btn btn-danger" type="button">Cancel</button></a>
    </div>
    </div>
    </form>
	
</body>
</html>