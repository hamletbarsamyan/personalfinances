package am.jsl.personalfinances.web.interceptor;

import am.jsl.personalfinances.domain.user.User;
import am.jsl.personalfinances.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

import static am.jsl.personalfinances.util.Constants.USER_HTML_PATH;

/**
 * Extended Spring {@link HandlerInterceptor} which will be called before handler method invocation.
 * Checks whether the current user id can access to the static folder inside /userhtml/ directory which contains folders
 * with names consisting user ids.
 *
 * @author hamlet
 */
public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
                             Object handler) throws Exception {
        String uri = req.getRequestURI();
        String path = req.getContextPath() + USER_HTML_PATH;

        // pre handle user html file
        if (uri.startsWith(path)) {
            Principal principal = req.getUserPrincipal();

            if (!(principal instanceof UsernamePasswordAuthenticationToken)) {
                return false;
            }
            User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

            if (user != null && user.getId() == 0) {
                user = (User) userService.loadUserByUsername(user.getLogin());
            }

            return  (user != null && uri.startsWith(path + user.getId() + "/"));
        }

        return true;
    }
}