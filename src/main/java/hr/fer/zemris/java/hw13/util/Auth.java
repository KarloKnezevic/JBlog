package hr.fer.zemris.java.hw13.util;

import hr.fer.zemris.java.hw13.dao.DAOProvider;
import hr.fer.zemris.java.hw13.model.BlogUser;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Razred za autentifikaciju korisnika.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
public class Auth {

	/**
	 * Autorizacija korisnika: login ili logout.
	 * 
	 * @param req
	 *            http zahtjev
	 * @param resp
	 *            http odgovor
	 * @throws IOException
	 *             iznimka
	 */
	public static void authorize(final HttpServletRequest req,
			final HttpServletResponse resp) throws IOException {

		if (req.getParameter("logout") != null) {
			logout(req, resp);
		} else if (req.getParameter("login") != null) {
			login(req, resp);
		}

	}

	/**
	 * Logout korisnika.
	 * 
	 * @param req
	 *            http zahtjev
	 * @param resp
	 *            http odgovor
	 */
	public static void logout(final HttpServletRequest req,
			final HttpServletResponse resp) {
		req.getSession().invalidate();
	}

	/**
	 * Login korisnika.
	 * 
	 * @param req
	 *            http zahtjev
	 * @param resp
	 *            http odgovor
	 */
	public static void login(final HttpServletRequest req,
			final HttpServletResponse resp) {

		final String nick = req.getParameter("nick");
		final String pass = req.getParameter("password");

		if ((nick != null) && (pass != null)) {

			final BlogUser user = DAOProvider.getDAO().getUserByNick(nick);

			if (user == null) {
				req.setAttribute("error",
						"User with provided nick does not exist.");
			} else if (!user.getPasswordHash().equals(Sha1.hashHexEncode(pass))) {
				req.setAttribute("passErr", nick);
				req.setAttribute("error", "Invalid password.");
			} else {

				final HttpSession ses = req.getSession();
				ses.setAttribute("current.user.id", user.getId());
				ses.setAttribute("current.user.nick", user.getNick());
				ses.setAttribute("current.user.firstName", user.getFirstName());
				ses.setAttribute("current.user.lastName", user.getLastName());
				ses.setAttribute("current.user.email", user.getEmail());

			}
		}
	}

	/**
	 * Provjera je li korisnik logiran.
	 * 
	 * @param req
	 *            http zahtjev
	 * @return true ukoliko je korisnik logiran, false inače
	 */
	public static boolean isLoggedIn(final HttpServletRequest req) {
		final HttpSession ses = req.getSession();

		if ((ses.getAttribute("current.user.id") != null)
				&& (ses.getAttribute("current.user.nick") != null)) {
			return true;
		}

		return false;

	}

	/**
	 * Provjera je li korisnik vlasnik navedenog bloga.
	 * 
	 * @param req
	 *            http zahtjev
	 * @param username
	 *            nick
	 * @return true ukoliko je korisnik vlasnik bloga, false inače
	 */
	public static boolean isOwner(final HttpServletRequest req,
			final String username) {

		if (isLoggedIn(req)
				&& req.getSession().getAttribute("current.user.nick")
						.equals(username)) {
			return true;
		}

		return false;
	}

}
