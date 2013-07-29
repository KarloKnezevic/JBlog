<%@page import="hr.fer.zemris.java.hw13.model.BlogUser"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.hw13.util.Auth"%>

<!-- 

Ova datoteka predstavlja učestale metode koje se koriste i koje predstavljaju
repetitivni dio. Na ovakav način postiže se lokalizacija čestih promjena i na
jednostavan način se mogu dodati novi sadržaju koji propagiraju na sve
stranice.

 -->

<%!public String head() {
	StringBuilder sb = new StringBuilder(150);
	sb.append("<title>" + title() + "</title>")
	.append("<meta charset=\"utf-8\">")
	.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
	.append("<meta name=\"description\" content=\"BlogSpot\">")
	.append("<meta name=\"author\" content=\"KarloKnezevic, karlo.knezevic@fer.hr\">")
	.append("<link href=\"/aplikacija4/ui/css/bootstrap.css\" rel=\"stylesheet\">")
	.append("<script src=\"http://code.jquery.com/jquery.js\"></script>")
	.append("<script src=\"/aplikacija4/ui/js/bootstrap.js\"></script>");

	return sb.toString();
}

public String title() {
	return "Java Blog";
}

public String headder(javax.servlet.http.HttpServletRequest request) {
	return headder(request, null);
}

public String headder(javax.servlet.http.HttpServletRequest request, List<BlogUser> list) {

	String firstAndLastName;
	boolean isLoggendIn = Auth.isLoggedIn(request);
	if(isLoggendIn) {
		firstAndLastName = 
				request.getSession().getAttribute("current.user.firstName").toString() + 
				" " + 
				request.getSession().getAttribute("current.user.lastName").toString();
	} else {
		firstAndLastName = "Not Logged In";
	}
	
	StringBuilder sb = new StringBuilder(500);
	sb.append("<table class=\"table table-striped\">").
	append("<tr>").
	append("<td class=\"text-left\">").append("<i class=\"icon-user\"></i><strong>").append(firstAndLastName).append("</strong></td>").
	append("<td class=\"text-center\">").append("<a href=\"/aplikacija4\"><i class=\"icon-home\"></i><button class=\"btn btn-primary\" type=\"button\">Home</button></a>").append("</td>");
	
	if (list != null && !list.isEmpty()) {
		sb.append("<td><i class=\"icon-globe\"></i>").
		append("<div class=\"btn-group\">").
		append("<button class=\"btn btn-primary dropdown-toggle\" data-toggle=\"dropdown\">Bloggers<span class=\"caret\"></span></button><ul class=\"dropdown-menu\">");
		
		for (BlogUser u : list) {
			sb.append("<li><a href=\"/aplikacija4/servleti/author/").append(u.getNick()).append("\">").append(u.getNick()).append("</a></li>");
		}
		sb.append("</ul></div>");
		
		sb.append("<td>");
	}
	
	if (isLoggendIn) {
		sb.append("<td>").append("<a href=\"/aplikacija4/servleti/author/").append(request.getSession().getAttribute("current.user.nick").toString()).append("\"><i class=\"icon-eye-open\"></i><button class=\"btn btn-primary\" type=\"button\">My Entries</button></a>").append("</td>").
		append("<td>").append("<a href=\"/aplikacija4/servleti/main?logout=true\"><i class=\"icon-off\"></i><button class=\"btn btn-primary\" type=\"button\">Logout</button></a>").append("</td>");
	} else {
		sb.append("<td class=\"text-center\">").append("<form class=\"form-inline\" action=/aplikacija4/servleti/main method=\"POST\">").append("<i class=\"icon-circle-arrow-right\"></i>").
		append("<input type=\"text\" class=\"input-small\" name=\"nick\" ");
		if (request.getAttribute("passErr") != null) {
			sb.append("value=\"" + request.getAttribute("passErr").toString() + "\"");
		}
		sb.append("placeholder=\"Nick\">").
		append("<input type=\"password\" class=\"input-small\" name=\"password\" placeholder=\"Password\">").
		append("<input type=\"hidden\" name=\"login\" value=\"true\">").
		append("<button type=\"submit\" class=\"btn\">Login</button>").append("</form>").append("</td>");
		
		sb.append("<td class=\"text-right\">").append("<a href=\"/aplikacija4/servleti/register\"><i class=\"icon-edit\"></i><button class=\"btn btn-primary\" type=\"button\">Register</button></a>").append("</td>");
	}
	
	sb.append("</table>");
	
	if (request.getAttribute("error") != null) {
		sb.append("<p class=\"text-error\"><strong>"+ request.getAttribute("error").toString() +"</strong></p>");
	}

	return sb.toString();
}

public String welcome(javax.servlet.http.HttpServletRequest request) {
	
	boolean isLoggendIn = Auth.isLoggedIn(request);
	StringBuilder sb = new StringBuilder(500);
	
	sb.append("<h1>").append(title()).append("</h1>");
	
	sb.append("<div class=\"row\">");
	
	sb.append("<div class=\"span10\"><p class=\"text-info text-center\">");
	
	if (isLoggendIn) {
		sb.append("Dear ").append((String)request.getSession().getAttribute("current.user.firstName").toString()).append(" ");
	}
	
	sb.append("Create a free blog and get started blogging right away. Share your thoughts, join the conversation and meet like minded people. You can follow blogggers and topics that interest you and make new friends with similar interests. We are a supportive community of people where real discussions take place and honest opinions are expressed. Welcome to ").append(title()).append(".</p>").append(slide()).append("</div>");
	
	sb.append("<div class=\"span4\"><p>").append("<dl>").append("<dt>").append("<i class=\"icon-edit\"></i></dt><dd>").append("Create a custom blog</dd>").
	append("<dt>").append("<i class=\"icon-user\"></i></dt><dd>").append("Connect with friends</dd>").
	append("<dt>").append("<i class=\"icon-pencil\"></i></dt><dd>").append("Discuss interesting topics</dd>").append("</dl></div></div>");
	
	
	return sb.toString();
}

private String slide() {
	
	StringBuilder sb = new StringBuilder(500);
	sb.append("<div id=\"myCarousel\" class=\"carousel slide\"><ol class=\"carousel-indicators\"><li data-target=\"#myCarousel\" data-slide-to=\"0\" class=\"active\"></li><li data-target=\"#myCarousel\" data-slide-to=\"1\"></li><li data-target=\"#myCarousel\" data-slide-to=\"2\"></li></ol>");
	sb.append("<div class=\"carousel-inner\"><div class=\"active item\"><img src=\"/aplikacija4/ui/img/j1.jpg\" alt=\"\"></div><div class=\"item\"><img src=\"/aplikacija4/ui/img/j2.png\" alt=\"\"></div><div class=\"item\"><img src=\"/aplikacija4/ui/img/j3.png\" alt=\"\"></div></div>");
	sb.append("<a class=\"carousel-control left\" href=\"#myCarousel\" data-slide=\"prev\">&lsaquo;</a><a class=\"carousel-control right\" href=\"#myCarousel\" data-slide=\"next\">&rsaquo;</a></div>");
	
	return sb.toString();
	
}


%>