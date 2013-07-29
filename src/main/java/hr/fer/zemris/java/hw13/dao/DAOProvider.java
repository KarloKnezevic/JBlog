package hr.fer.zemris.java.hw13.dao;

import hr.fer.zemris.java.hw13.dao.jpa.JPADAOImpl;

/**
 * Razred u čijoj je nadležnosti odabir implementacije sloja za perzistenciju
 * podataka (DAO layer). Konkretno, ovaj razred odabire koja će se
 * implementacija sučelja DAO koristiti u aplikaciji, stvara je i nudi ostatku
 * aplikacije na uporabu kroz statičku metodu {@linkplain #getDAO()}.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
public class DAOProvider {

	/**
	 * Referenca na odabranu implementaciju sloja za perzistenciju
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Metoda dohvaća implementaciju sloja za perzistenciju podataka.
	 * 
	 * @return referencu na {@linkplain DAO} koji se koristi
	 */
	public static DAO getDAO() {
		return dao;
	}

}