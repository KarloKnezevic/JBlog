package hr.fer.zemris.java.hw13.console;

import hr.fer.zemris.java.hw13.model.BlogComment;
import hr.fer.zemris.java.hw13.model.BlogEntry;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Primjer s predavanja.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
public class Example1 {

	/**
	 * Primjer s predavanja.
	 * 
	 * @param args
	 *            nema argumenata
	 */
	public static void main(final String[] args) {
		final EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("baza.podataka.za.blog");

		// 1. korak - stvaranje novog blog zapisa...
		// -----------------------------------------
		System.out.println("Dodajem blog zapis.");
		final BlogEntry blogEntry = dodajZapis(emf);
		System.out.println("Dodajem blog zapis - gotovo.");

		final Long blogEntryID = blogEntry.getId();

		// 2. korak - dodavanje dva komentara...
		// -----------------------------------------
		System.out.println("Dodajem komentar.");
		dodajKomentar(emf, blogEntryID, "Blog ti je super!");
		System.out.println("Dodajem komentar - gotovo.");

		try {
			Thread.sleep(1000);
		} catch (final InterruptedException ex) {
		}

		System.out.println("Dodajem komentar.");
		dodajKomentar(emf, blogEntryID, "Vau!");
		System.out.println("Dodajem komentar - gotovo.");

		try {
			Thread.sleep(1000);
		} catch (final InterruptedException ex) {
		}

		System.out.println("Dodajem komentar.");
		dodajKomentar(emf, blogEntryID, "Još jedan komentar.");
		System.out.println("Dodajem komentar - gotovo.");

		System.out.println("Primjer upita.");
		primjerUpita(emf, blogEntryID);
		System.out.println("Primjer upita - gotovo.");

		try {
			Thread.sleep(1000);
		} catch (final InterruptedException ex) {
		}

		System.out.println("Primjer upita 2.");
		primjerUpita2(emf, blogEntryID);
		System.out.println("Primjer upita 2 - gotovo.");

		emf.close();
	}

	/**
	 * Dodavanje zapisa.
	 * 
	 * @param emf
	 *            tvornica
	 * @return blog
	 */
	public static BlogEntry dodajZapis(final EntityManagerFactory emf) {
		// veza prema sloju perzistencije
		final EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		final BlogEntry blogEntry = new BlogEntry();
		blogEntry.setCreatedAt(new Date());
		blogEntry.setLastModifiedAt(blogEntry.getCreatedAt());
		blogEntry.setTitle("Moj prvi blog");
		blogEntry.setText("Ovo je moj prvi blog zapis.");

		// zove se samo ako se izvana stvorio novi objekt kojeg em nije svjestan
		em.persist(blogEntry);

		em.getTransaction().commit();
		em.close();

		return blogEntry;
	}

	/**
	 * Dodavanje komentara.
	 * 
	 * @param emf
	 *            tvornica
	 * @param blogEntryID
	 *            id bloga
	 * @param message
	 *            poruka
	 */
	public static void dodajKomentar(final EntityManagerFactory emf,
			final Long blogEntryID, final String message) {
		final EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		final BlogEntry blogEntry = em.find(BlogEntry.class, blogEntryID);

		final BlogComment blogComment = new BlogComment();
		blogComment.setUsersEMail("ivica@host.com");
		blogComment.setPostedOn(new Date());
		blogComment.setMessage(message);
		blogComment.setBlogEntry(blogEntry);

		em.persist(blogComment);

		// ako se u kodu mijenjaju veze, veze treba držati ispravnim
		// tijekom runtimea ovo moramo raditi sami
		// ako mislimo koristiti blogEntry u ovoj metodi, nećemo dobiti
		// komentare
		// jer komentari se dodaju prlikom učitavanja iz baze od strane EM
		blogEntry.getComments().add(blogComment);

		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Uporaba jezika JPA-QL
	 * 
	 * @param emf
	 *            tvornica
	 * @param blogEntryID
	 *            id bloga
	 */
	private static void primjerUpita(final EntityManagerFactory emf,
			final Long blogEntryID) {
		final EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		final BlogEntry blogEntry = em.find(BlogEntry.class, blogEntryID);

		@SuppressWarnings("unchecked")
		final List<BlogComment> comments = em
				.createQuery(
						"select b from BlogComment as b where b.blogEntry=:be")
				.setParameter("be", blogEntry).getResultList();

		for (final BlogComment bc : comments) {
			System.out.println("Blog [" + bc.getBlogEntry().getTitle()
					+ "] ima komentar: [" + bc.getMessage() + "]");
		}

		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Uporaba jezika JPA-QL
	 * 
	 * @param emf
	 *            tvornica
	 * @param blogEntryID
	 *            id bloga
	 */
	private static void primjerUpita2(final EntityManagerFactory emf,
			final Long blogEntryID) {
		final EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		final BlogEntry blogEntry = em.find(BlogEntry.class, blogEntryID);

		@SuppressWarnings("unchecked")
		final List<BlogComment> comments = em
				.createNamedQuery("BlogEntry.upit1")
				.setParameter("be", blogEntry)
				.setParameter("when", new Date(new Date().getTime() - 2000))
				.getResultList();

		for (final BlogComment bc : comments) {
			System.out.println("Blog [" + bc.getBlogEntry().getTitle()
					+ "] ima komentar: [" + bc.getMessage() + "]");
		}

		em.getTransaction().commit();
		em.close();
	}
}