package hr.fer.zemris.java.hw13.dao;

/**
 * Osnovna iznimka koju bacaju metode u DAO sloju. Ukoliko se želi napraviti
 * finija granulacija iznimki, sve preostale iznimke moraju biti izvedene iz
 * ove.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor koji prima tekst poruke kao i iznimku koju je potrebno
	 * zamotati u ovu iznimku.
	 * 
	 * @param message
	 *            poruka
	 * @param cause
	 *            iznimka koja se zamata
	 */
	public DAOException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Konstruktor koji prima tekst poruke.
	 * 
	 * @param message
	 *            poruka
	 */
	public DAOException(final String message) {
		super(message);
	}
}