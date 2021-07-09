package am.jsl.personalfinances.web.filter;

import am.jsl.personalfinances.log.AppLogger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter for logging response times in milliseconds.
 * @author hamlet
 */
@Component
@Order(1)
public class ResponseTimerFilter implements Filter {
	private final static AppLogger log = new AppLogger(ResponseTimerFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
		chain.doFilter(request, response);
		long elapsed = System.currentTimeMillis() - startTime;
		String name = "personalfinancesDefaultServlet";

		if (request instanceof HttpServletRequest) {
			name = ((HttpServletRequest) request).getRequestURI();
		}

		if(elapsed > 1000){
			log.info(name + " took " + elapsed + " ms");
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
