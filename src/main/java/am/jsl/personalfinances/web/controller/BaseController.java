package am.jsl.personalfinances.web.controller;

import am.jsl.personalfinances.domain.user.User;
import am.jsl.personalfinances.log.AppLogger;
import am.jsl.personalfinances.service.user.UserService;
import am.jsl.personalfinances.web.util.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * The base class for all controllers.
 * Contains common fields and methods.
 * @author hamlet
 */
public class BaseController implements Serializable {
    protected static final AppLogger log = new AppLogger(BaseController.class);

    /**
     * The user service
     */
    @Autowired
    protected transient UserService userService;

    /**
     * The internationalization message wrapper.
     * @see I18n
     */
    @Autowired
    protected I18n i18n;

    /**
     * Returns the current user from Spring Security Context.
     * @return the User
     */
    protected User getUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user != null && user.getId() == 0) {
            user = (User) userService.loadUserByUsername(user.getLogin());
        }
        return user;
    }

    /**
     * Extracts and returns application url from request.
     * @param request the HttpServletRequest
     * @return the application url
     */
    protected String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
