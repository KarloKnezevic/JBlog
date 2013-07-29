package hr.fer.zemris.java.hw13.web.servlets;

import hr.fer.zemris.java.hw13.dao.DAOProvider;
import hr.fer.zemris.java.hw13.util.Auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Prikaz home page.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
@WebServlet(name = "main", urlPatterns = { "/servleti/main" })
public class Main extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Procesiranje.
	 */
	@Override
	protected void doGet(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {

		process(req, resp);

	}

	/**
	 * Procesiranje.
	 */
	@Override
	protected void doPost(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {

		process(req, resp);

	}

	/**
	 * Prosljeđivanje zahtjeva na index.jsp.
	 * 
	 * @param req
	 *            http zahtjev
	 * @param resp
	 *            http odgovor
	 * @throws ServletException
	 *             iznimka
	 * @throws IOException
	 *             iznimka
	 */
	private void process(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {

		Auth.authorize(req, resp);
		req.setAttribute("users", DAOProvider.getDAO().listAllUsers());
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);

	}

}
