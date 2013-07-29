package hr.fer.zemris.java.hw13.dao.jpa;

import hr.fer.zemris.java.hw13.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * Ovaj razred predstavlja implementaciju sloja za pohranu podataka u relacijsku
 * bazu podataka uporabom specifikacije JPA (Java Persistence API).
 * 
 * Odgovarajući EntityManager stvara se po potrebi (kada ga prvi puta zatreba
 * neka metode u DAO objektima), i povezuje u kontekst trenutne dretve. Sve
 * metode koje izvodi ista dretva imaju pristup do istog pohranjenog
 * EntityManager-a.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
public class JPAEMProvider {

	/**
	 * ThreadLocal za pohranu podataka o uspostavljenim vezama prema DBMS-u.
	 */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * Dohvat EntityManagera iz ThreadLocal mape. Ukoliko isti ne postoji,
	 * stvara se novi EntityManager i započinje transakcija.
	 * 
	 * @return EntityManager
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if (ldata == null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Zatvaranje transakcije prema bazi podataka i gašenje EntityManagera.
	 * 
	 * @throws DAOException
	 */
	public static void close() throws DAOException {
		final LocalData ldata = locals.get();
		if (ldata == null) {
			return;
		}
		DAOException dex = null;
		try {
			if (ldata.em.getTransaction().isActive()
					&& !ldata.em.getTransaction().getRollbackOnly()) {
				ldata.em.getTransaction().commit();
			} else if (ldata.em.getTransaction().isActive()) {
				ldata.em.getTransaction().rollback();
			}
		} catch (final Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch (final Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null) {
			throw dex;
		}
	}

	/**
	 * Pomoćni razred čiji se primjerci vežu u kontekst dretve u korišteni
	 * {@linkplain ThreadLocal} objekt. Zaseban razred napravljen je stoga da se
	 * omogući kasnija eventualna pohrana dodatnih podataka.
	 * 
	 * @author Karlo Knezevic, karlo.knezevic@fer.hr
	 * 
	 */
	private static class LocalData {
		/**
		 * EntityManager koji se koristi za rad s bazom podataka
		 */
		EntityManager em;
	}

}