package hr.fer.zemris.java.hw13.web.servlets.router;

import hr.fer.zemris.java.hw13.dao.DAOProvider;
import hr.fer.zemris.java.hw13.model.BlogEntry;
import hr.fer.zemris.java.hw13.model.BlogUser;
import hr.fer.zemris.java.hw13.util.Auth;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji predstavlja ruter koji analizira URL i poziva odgovarajuće
 * servlete ili jspove.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
@WebServlet(name = "author-router", urlPatterns = { "/servleti/author/*" })
public class AuthorRouter extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Pozivanje rute.
	 */
	@Override
	protected void doGet(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {

		route(req, resp);

	}

	/**
	 * Pozivanje rute.
	 */
	@Override
	protected void doPost(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {

		route(req, resp);

	}

	/**
	 * Metoda za određivanje rute.
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
	private void route(final HttpServletRequest req,
			final HttpServletResponse resp) throws IOException,
			ServletException {

		final String path = req.getPathInfo().substring(1);
		final String[] pieces = path.split("/");

		if ((pieces.length < 1) || (pieces.length > 3)) {
			resp.sendRedirect(req.getContextPath());
			return;
		}

		final String nick = pieces[0];
		final BlogUser user = DAOProvider.getDAO().getUserByNick(nick);

		// u slučaju da u URL nije ispravan korisnik, automatski redirect na
		// home page
		if (user == null) {
			resp.sendRedirect(req.getContextPath());
			return;
		}

		final List<BlogEntry> blogEntries = user.getBlogEntries();

		// PRIKAZ KOIRSNIČKE WELCOME STRANICE
		if (pieces.length == 1) {
			req.setAttribute("blogEntries", blogEntries);
			req.setAttribute("nick", nick);
			req.getRequestDispatcher("/WEB-INF/pages/nick_blog_entry.jsp")
					.forward(req, resp);
			return;
		}

		final String query = pieces[1];
		BlogEntry blogEntry;
		switch (query) {
		case "new":
			// NEW
			if (Auth.isOwner(req, nick)) {
				req.getRequestDispatcher("/WEB-INF/pages/new_blog_entry.jsp")
						.forward(req, resp);
			} else {
				req.getRequestDispatcher(
						"/WEB-INF/pages/error/authorization_error.jsp")
						.forward(req, resp);
			}
			break;
		case "edit":
			// EDIT
			if (Auth.isOwner(req, nick) && (pieces.length == 3)) {
				Long eid = null;
				try {
					eid = Long.parseLong(pieces[2]);
				} catch (final NumberFormatException e) {
					req.getRequestDispatcher(
							"/WEB-INF/pages/error/url_error.jsp").forward(req,
							resp);
					return;
				}

				blogEntry = DAOProvider.getDAO().getBlogEntry(eid);

				if (!blogEntry.getCreator().getNick().equals(nick)) {
					req.getRequestDispatcher(
							"/WEB-INF/pages/error/authorization_error.jsp")
							.forward(req, resp);
					return;
				}

				req.setAttribute("blogEntry", blogEntry);
				req.getRequestDispatcher("/WEB-INF/pages/edit_blog_entry.jsp")
						.forward(req, resp);
			} else {
				req.getRequestDispatcher(
						"/WEB-INF/pages/error/authorization_error.jsp")
						.forward(req, resp);
			}
			break;
		default:
			// POGLED NA NEKI BLOG
			Long eid = null;
			try {
				eid = Long.parseLong(query);
			} catch (final NumberFormatException ignorable) {
				resp.sendRedirect(req.getContextPath());
				return;
			}

			blogEntry = DAOProvider.getDAO().getBlogEntry(eid);

			if (blogEntry != null) {

				if (!blogEntry.getCreator().getNick().equals(nick)) {
					req.getRequestDispatcher(
							"/WEB-INF/pages/error/authorization_error.jsp")
							.forward(req, resp);
					return;
				}

				req.setAttribute("blogEntry", blogEntry);
				req.getRequestDispatcher("/WEB-INF/pages/blog_view.jsp")
						.forward(req, resp);

			} else {
				req.getRequestDispatcher("/WEB-INF/pages/error/url_error.jsp")
						.forward(req, resp);
			}

		}

	}

}
