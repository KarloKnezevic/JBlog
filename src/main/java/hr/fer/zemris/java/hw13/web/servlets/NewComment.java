package hr.fer.zemris.java.hw13.web.servlets;

import hr.fer.zemris.java.hw13.dao.DAOProvider;
import hr.fer.zemris.java.hw13.util.Validation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Dodavanje novog komentara.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
@WebServlet(name = "new_comment", urlPatterns = { "/comment" })
public class NewComment extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Komentiranje.
	 */
	@Override
	protected void doGet(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {

		comment(req, resp);

	}

	/**
	 * Komentiranje.
	 */
	@Override
	protected void doPost(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {

		comment(req, resp);

	}

	/**
	 * Dodavanje novog komentara. Ne dopušta se prazan komentar!
	 * 
	 * @param req
	 *            http zahtjev
	 * @param resp
	 *            http odgovor
	 * @throws IOException
	 *             iznimka
	 * @throws ServletException
	 *             iznimka
	 */
	private void comment(final HttpServletRequest req,
			final HttpServletResponse resp) throws IOException,
			ServletException {

		Long eid = null;
		try {
			eid = Long.parseLong(req.getParameter("eid"));
		} catch (final NumberFormatException e) {
			req.getRequestDispatcher("/WEB-INF/pages/error/url_error.jsp")
					.forward(req, resp);
			return;
		}

		if (Validation.valid(req, new String[] { "email", "comment", "nick" },
				new String[] { null, null, null })) {
			DAOProvider.getDAO().addNewComment(eid, req.getParameter("email"),
					req.getParameter("comment"));
			resp.sendRedirect("/aplikacija4/servleti/author/"
					+ req.getParameter("nick") + "/" + eid);
		} else {
			req.getRequestDispatcher(
					"/WEB-INF/pages/error/empty_form_error.jsp").forward(req,
					resp);
		}

	}

}
