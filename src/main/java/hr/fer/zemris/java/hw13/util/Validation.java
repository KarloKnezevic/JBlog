package hr.fer.zemris.java.hw13.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Razred za validaciju unešenog teksta od strane korisnika.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
public class Validation {

	/**
	 * Provjera unosa. Params je niz imena parametara z http zahtjevu, a regex
	 * niz koji sadrži regulane izraze koje parametri trebaju zadovoljiti. Nužno
	 * je da oba niza imaju jednaku veličinu, inače se vraća false.
	 * 
	 * @param req
	 *            http zahtjev
	 * @param params
	 *            parametri u zahtjevu
	 * @param regex
	 *            regularni izrazi koje parametri trebaju zadovoljiti
	 * @return true ukoliko je validacija ispravna, false inače
	 */
	public static boolean valid(final HttpServletRequest req,
			final String[] params, final String[] regex) {

		// provjera veličine niza parametara
		if ((params == null) || (params.length < 1)) {
			return false;
		}

		// PROVJERA PARAMETARA
		for (final String param : params) {
			final String val = req.getParameter(param);

			// ne prihvaćaju se nul vrijednosti i prazne poruke
			if ((val == null) || (val.trim().length() == 0)) {
				return false;
			}

		}

		// ispitivanje regularnih izraza
		if ((regex == null) || (regex.length < 1)
				|| (params.length != regex.length)) {
			return true;
		}

		// PROVJERA REGULARNIH IZRAZA
		for (int i = 0; i < regex.length; i++) {
			final String r = regex[i];
			if (r == null) {
				continue;
			}

			if (!req.getParameter(params[i]).matches(r)) {
				return false;
			}
		}

		return true;

	}

}
