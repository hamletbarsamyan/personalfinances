package am.jsl.personalfinances.web.controller;

import am.jsl.personalfinances.domain.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Defines method for retrieving current user from pages.
 * @author hamlet
 */
@ControllerAdvice
public class CurrentUserController extends BaseController {

    /**
     * Resolves and returns the current user from spring security context.
     * @param currentUser the UserDetails
     * @return the UserDetails
     */
    @ModelAttribute("currentUser")
    public UserDetails getCurrentUser(@AuthenticationPrincipal UserDetails currentUser) {
        User user = (User) currentUser;
        if (user != null && user.getId() == 0) {
            user = (User) userService.loadUserByUsername(user.getLogin());
        }
        return user;
    }
}
