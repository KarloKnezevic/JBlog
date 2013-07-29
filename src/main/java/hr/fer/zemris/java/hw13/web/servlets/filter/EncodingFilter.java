package hr.fer.zemris.java.hw13.web.servlets.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Encoding filter.
 * 
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 * 
 */
@WebFilter(filterName = "encodingFilter", urlPatterns = { "/*" })
public class EncodingFilter implements Filter {

	/**
	 * Ne koristi se.
	 */
	@Override
	public void destroy() {

	}

	/**
	 * Za svaki zahtjev postavlja se encoding na UTF-8.
	 */
	@Override
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);

	}

	/**
	 * Ne koristi se.
	 */
	@Override
	public void init(final FilterConfig arg0) throws ServletException {

	}

}
