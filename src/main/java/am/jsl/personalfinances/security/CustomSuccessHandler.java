package am.jsl.personalfinances.security;

import am.jsl.personalfinances.domain.user.Role;
import am.jsl.personalfinances.log.AppLogger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom {@link SimpleUrlAuthenticationSuccessHandler} that redirects to
 * user role specific url where users should be sent after successful authentication.
 * @author hamlet
 */
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static AppLogger logger = new AppLogger(AccessDeniedExceptionHandler.class);

    /**
     * The RedirectStrategy
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.error("Can't redirect");
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * Determines url for the given Authentication.
     * The url will be "/home" for USER roles, "/admin" for ADMIN roles,
     * otherwise "/accessDenied".
     * @param authentication the Authentication
     * @return the success url
     */
    protected String determineTargetUrl(Authentication authentication) {
        String url = "";

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        if (isUser(roles)) {
            url = "/home";
        } else if (isAdmin(roles)) {
            url = "/admin";
        } else {
            url = "/accessDenied";
        }

        return url;
    }

    /**
     * Checks whether the given list contains USER role.
     * @param roles the role list
     * @return true if roles contains USER role
     */
    private boolean isUser(List<String> roles) {
        if (roles.contains(Role.USER.name())) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether the given list contains ADMIN role.
     * @param roles the role list
     * @return true if roles contains ADMIN role
     */
    private boolean isAdmin(List<String> roles) {
        if (roles.contains(Role.ADMIN.name())) {
            return true;
        }
        return false;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}