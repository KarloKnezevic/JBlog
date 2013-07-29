package hr.fer.zemris.java.hw13.web.init;

import hr.fer.zemris.java.hw13.dao.jpa.JPAEMFProvider;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Inicijalizacija EntityManagerFactory-a. Čitanje xml datoteke za Hibernate.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	/**
	 * Inicijalizacija Hibernatea čitanjem Persistence.xml datoteke.
	 */
	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		final EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("baza.podataka.za.blog");
		sce.getServletContext().setAttribute("my.application.emf", emf);
		JPAEMFProvider.setEmf(emf);
	}

	/**
	 * Uništavanje resursa.
	 */
	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);
		final EntityManagerFactory emf = (EntityManagerFactory) sce
				.getServletContext().getAttribute("my.application.emf");
		if (emf != null) {
			emf.close();
		}
	}
}