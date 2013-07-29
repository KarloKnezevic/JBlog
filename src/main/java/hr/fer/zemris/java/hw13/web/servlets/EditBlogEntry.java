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
 * Promjena postojećeg bloga.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
@WebServlet(name = "edit_blog_entry", urlPatterns = { "/new", "/edit" })
public class EditBlogEntry extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Editiranje bloga.
	 */
	@Override
	protected void doGet(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {

		edit(req, resp);

	}

	/**
	 * Editiranje bloga.
	 */
	@Override
	protected void doPost(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {

		edit(req, resp);

	}

	/**
	 * Zapis promjene na sloj perzistencije.
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
	private void edit(final HttpServletRequest req,
			final HttpServletResponse resp) throws IOException,
			ServletException {

		Long eid = null;
		try {
			eid = Long.parseLong(req.getParameter("eid"));
		} catch (final NumberFormatException e) {
			if (!req.getParameter("eid").equals("new")) {
				req.getRequestDispatcher("/WEB-INF/pages/error/url_error.jsp")
						.forward(req, resp);
				return;
			}
		}

		final String uid = req.getSession().getAttribute("current.user.id")
				.toString();

		if (!Validation.valid(req, new String[] { "title", "text" },
				new String[] { null, null })) {
			req.getRequestDispatcher(
					"/WEB-INF/pages/error/empty_form_error.jsp").forward(req,
					resp);
			return;
		}

		if (req.getParameter("eid").equals("new")) {
			DAOProvider.getDAO().createNewBlogEntry(Long.parseLong(uid),
					req.getParameter("title"), req.getParameter("text"));
		} else {
			DAOProvider.getDAO().editBlogEntry(eid, req.getParameter("title"),
					req.getParameter("text"));
		}

		resp.sendRedirect("/aplikacija4/servleti/author/"
				+ (String) req.getSession().getAttribute("current.user.nick"));

	}

}
