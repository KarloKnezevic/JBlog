package hr.fer.zemris.java.hw13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Model blog komentara.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/**
	 * Id
	 */
	private Long id;
	/**
	 * Blog
	 */
	private BlogEntry blogEntry;
	/**
	 * Email korisnika
	 */
	private String usersEMail;
	/**
	 * Poruka
	 */
	private String message;
	/**
	 * Datum posta
	 */
	private Date postedOn;

	/**
	 * Id
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Id
	 * 
	 * @param id
	 *            id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Blog
	 * 
	 * @return blog
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Blog
	 * 
	 * @param blogEntry
	 *            blog
	 */
	public void setBlogEntry(final BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Email
	 * 
	 * @return email
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Email
	 * 
	 * @param usersEMail
	 *            email
	 */
	public void setUsersEMail(final String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Poruka
	 * 
	 * @return poruka
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Poruka
	 * 
	 * @param message
	 *            poruka
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * Datum posta
	 * 
	 * @return datum posta
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Datum posta
	 * 
	 * @param postedOn
	 *            datum posta
	 */
	public void setPostedOn(final Date postedOn) {
		this.postedOn = postedOn;
	}

	/**
	 * Hash
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Equals
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}