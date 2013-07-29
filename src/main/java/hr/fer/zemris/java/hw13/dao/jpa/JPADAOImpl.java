package hr.fer.zemris.java.hw13.dao.jpa;

import hr.fer.zemris.java.hw13.dao.DAO;
import hr.fer.zemris.java.hw13.dao.DAOException;
import hr.fer.zemris.java.hw13.model.BlogComment;
import hr.fer.zemris.java.hw13.model.BlogEntry;
import hr.fer.zemris.java.hw13.model.BlogUser;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 * Implementacija sučelja DAO.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
public class JPADAOImpl implements DAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw13.dao.DAO#getBlogEntry(java.lang.Long)
	 */
	@Override
	public BlogEntry getBlogEntry(final Long id) throws DAOException {

		final BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(
				BlogEntry.class, id);
		return blogEntry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw13.dao.DAO#listAllUsers()
	 */
	@Override
	public List<BlogUser> listAllUsers() {

		@SuppressWarnings("unchecked")
		final List<BlogUser> list = JPAEMProvider.getEntityManager()
				.createQuery("select u from BlogUser as u").getResultList();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw13.dao.DAO#getUserByNick(java.lang.String)
	 */
	@Override
	public BlogUser getUserByNick(final String nick) {

		BlogUser user = null;

		try {
			user = (BlogUser) JPAEMProvider
					.getEntityManager()
					.createQuery("select u from BlogUser as u where u.nick =:n")
					.setParameter("n", nick).getSingleResult();
		} catch (final NoResultException ignorable) {
		}

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw13.dao.DAO#registerUser(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean registerUser(final String firstName, final String lastName,
			final String email, final String nick, final String hashPassword) {

		final BlogUser newUser = new BlogUser();

		try {
			newUser.setFirstName(firstName);
			newUser.setLastName(lastName);
			newUser.setEmail(email);
			newUser.setNick(nick);
			newUser.setPasswordHash(hashPassword);

			final EntityManager em = JPAEMProvider.getEntityManager();

			// spremanje zapisa u bazu
			em.persist(newUser);
			em.getTransaction().commit();
		} catch (final Exception e) {

			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw13.dao.DAO#addNewComment(java.lang.Long,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void addNewComment(final Long eid, final String email,
			final String comment) {

		final EntityManager em = JPAEMProvider.getEntityManager();

		final BlogEntry entry = em.find(BlogEntry.class, eid);
		final BlogComment blogComment = new BlogComment();

		blogComment.setUsersEMail(email);
		blogComment.setMessage(comment);
		blogComment.setBlogEntry(entry);
		blogComment.setPostedOn(new Date());

		// spremanje zapisa u bazu
		em.persist(blogComment);
		em.getTransaction().commit();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw13.dao.DAO#createNewBlogEntry(long,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void createNewBlogEntry(final long uid, final String title,
			final String text) {

		final EntityManager em = JPAEMProvider.getEntityManager();

		final BlogUser user = em.find(BlogUser.class, uid);
		final BlogEntry entry = new BlogEntry();

		entry.setTitle(title);
		entry.setText(text);
		entry.setCreatedAt(new Date());
		entry.setLastModifiedAt(entry.getCreatedAt());
		entry.setCreator(user);

		// spremanje zapisa u bazu
		em.persist(entry);
		em.getTransaction().commit();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw13.dao.DAO#editBlogEntry(java.lang.Long,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void editBlogEntry(final Long eid, final String title,
			final String text) {

		final EntityManager em = JPAEMProvider.getEntityManager();

		final BlogEntry entry = em.find(BlogEntry.class, eid);

		entry.setTitle(title);
		entry.setText(text);
		entry.setLastModifiedAt(new Date());

		em.getTransaction().commit();

	}

}