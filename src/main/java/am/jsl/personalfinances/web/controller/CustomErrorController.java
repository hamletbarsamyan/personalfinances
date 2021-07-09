package am.jsl.personalfinances.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Custom ErrorController for rendering errors.
 * @author hamlet
 */
@Controller
public class CustomErrorController implements ErrorController {

    /**
     * Handlers error and returns error view.
     * @return the error view
     */
    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }

    /**
     * Returns error view path.
     * @return the error path
     */
    public String getErrorPath() {
        return "/error";
    }
}
