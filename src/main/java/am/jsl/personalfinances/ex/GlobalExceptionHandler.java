package am.jsl.personalfinances.ex;

import am.jsl.personalfinances.service.ErrorTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The GlobalExceptionHandler handles uncaught exceptions and
 * emails exception details via {@link ErrorTrackerService}.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * The ErrorTrackerService.
     */
    @Autowired
    private ErrorTrackerService errorTrackerService;

    @ExceptionHandler(Exception.class)
    public void handle(final Exception e) {
        errorTrackerService.sendError(e);
    }
}
