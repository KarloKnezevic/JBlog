package hr.fer.zemris.java.hw13.web.servlets;

import hr.fer.zemris.java.hw13.dao.DAOProvider;
import hr.fer.zemris.java.hw13.util.Auth;
import hr.fer.zemris.java.hw13.util.Sha1;
import hr.fer.zemris.java.hw13.util.Validation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Registracija korisnika.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
@WebServlet(name = "register", urlPatterns = { "/servleti/register" })
public class Register extends HttpServlet {

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
	 * Registracija ili prosljeđivanje na stranicu na registraciju.
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

		if (req.getParameter("register") != null) {
			register(req, resp);
		} else {
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(
					req, resp);
		}

	}

	/**
	 * Registracija korisnika. Postoji validacija unesenih podataka: ne dopušta
	 * se prazan unos i korisnik mora unijeti ispravan mail.
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
	private void register(final HttpServletRequest req,
			final HttpServletResponse resp) throws IOException,
			ServletException {

		if (Validation
				.valid(req,
						new String[] { "firstName", "lastName", "email",
								"nick", "password" },
						new String[] {
								null,
								null,
								"^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$",
								null, null })) {

			final boolean registrationAccepted = DAOProvider.getDAO()
					.registerUser(req.getParameter("firstName"),
							req.getParameter("lastName"),
							req.getParameter("email"),
							req.getParameter("nick"),
							Sha1.hashHexEncode(req.getParameter("password")));

			if (registrationAccepted) {
				Auth.login(req, resp);
				resp.sendRedirect(req.getContextPath() + "/index.jsp");
				return;
			}

		}

		req.getRequestDispatcher("/WEB-INF/pages/error/registration_error.jsp")
				.forward(req, resp);

	}

}
