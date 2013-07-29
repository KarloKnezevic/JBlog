package hr.fer.zemris.java.hw13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Redirector na home page.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
@WebServlet(name = "index", urlPatterns = { "/index.jsp" })
public class Index extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Metoda redirecta na home page.
	 */
	@Override
	protected void doGet(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {

		resp.sendRedirect(req.getContextPath() + "/servleti/main");

	}

}
