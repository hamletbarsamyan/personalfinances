package am.jsl.personalfinances.service.reminder;

import am.jsl.personalfinances.dto.reminder.ReminderListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

/**
 * Helper class that uses Thymeleaf's {@link TemplateEngine} for generating reminders html.
 * @author hamlet
 */
@Service
public class ReminderAlertBuilder {
    /**
     * The instance of Thymeleaf's {@link TemplateEngine}
     */
    private TemplateEngine templateEngine;

    /**
     * Constructs a new ReminderAlertBuilder with given TemplateEngine
     * @param templateEngine the TemplateEngine
     */
    @Autowired
    public ReminderAlertBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Generates a html from the given list of reminders and returns text of html.
     * @param reminders the list to reminders
     * @return html text of generated reminders list
     */
    public String build(List<ReminderListDTO> reminders) {
        Context context = new Context();
        context.setVariable("reminders", reminders);
        return templateEngine.process("reminder/reminder-alert-template", context);
    }
}
