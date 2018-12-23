package am.jsl.personalfinances.web.controller;

import am.jsl.personalfinances.web.util.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Defines methods for redirecting users to home or admin page.
 * @author hamlet
 */
@Controller
public class IndexController {

    /**
     * Mapping for home page.
     * Sets the current user name in model and returns home page.
     * @param model the ModelMap
     * @return the home page
     */
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String homePage(ModelMap model) {
        model.addAttribute("user", getCurrentUsername());
        return WebUtils.PAGE_HOME;
    }

    /**
     * Mapping for admin home page.
     * @param model  the ModelMap
     * @return the admin home page
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(ModelMap model) {
        model.addAttribute("user", getCurrentUsername());
        return WebUtils.PAGE_ADMIN;
    }

    /**
     * Mapping for access denied page.
     * @param model the ModelMap
     * @return the access denied page
     */
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getCurrentUsername());
        return "accessDenied";
    }

    /**
     * Log outs the current user from Spring Security Context and redirects to logout page.
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @return the logout page
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    /**
     * Returns the current user name from Springs Security Context.
     * @return the current user name
     */
    private String getCurrentUsername() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
