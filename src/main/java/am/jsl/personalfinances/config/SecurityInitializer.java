package am.jsl.personalfinances.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Registers springSecurityFilterChain before any other registered {@link javax.servlet.Filter}.
 * @author hamlet
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
}