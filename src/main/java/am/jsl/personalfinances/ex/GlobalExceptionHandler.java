package am.jsl.personalfinances.ex;

import am.jsl.personalfinances.log.AppLogger;
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
    protected static final AppLogger log = new AppLogger(GlobalExceptionHandler.class);

    /**
     * The ErrorTrackerService.
     */
    @Autowired
    private ErrorTrackerService errorTrackerService;

    @ExceptionHandler(Exception.class)
    public void handle(final Exception e) {
        log.error("Handled exception: " + e.getMessage(), e);
        errorTrackerService.sendError(e);
    }
}
