package hr.fer.zemris.java.hw13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Razred koji čuva referencu na EntityManagerFactory.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
public class JPAEMFProvider {

	/**
	 * EntityManagerFactory za stvaranje EntityManager objekata
	 */
	public static EntityManagerFactory emf;

	/**
	 * Dohvat reference na EntityManagerFactory.
	 * 
	 * @return referenca na EntityManagerFactory ili null ukoliko nije
	 *         postavljena.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Pridruživanje konkretne implementacije referenci.
	 * 
	 * @param emf
	 *            implementacija razreda EntityManagerFactory
	 */
	public static void setEmf(final EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}