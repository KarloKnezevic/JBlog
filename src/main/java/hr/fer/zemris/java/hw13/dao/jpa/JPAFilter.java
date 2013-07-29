package hr.fer.zemris.java.hw13.dao.jpa;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Filter koji nakon opsluživanja zahtjeva na /servleti/* zatvara vezu prema
 * EMProvideru.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
@WebFilter("/servleti/*")
public class JPAFilter implements Filter {

	/**
	 * Ne koristi se.
	 */
	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * Nakon opsluživanja, zatvara se veza prema EMProvideru.
	 */
	@Override
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} finally {
			JPAEMProvider.close();
		}
	}

	/**
	 * Ne koristi se.
	 */
	@Override
	public void destroy() {
	}

}