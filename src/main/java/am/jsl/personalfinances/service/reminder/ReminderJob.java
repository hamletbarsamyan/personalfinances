package am.jsl.personalfinances.service.reminder;

import am.jsl.personalfinances.log.AppLogger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Simple quartz job that periodically processes expired reminders.
 * @author hamlet
 */
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Qualifier("reminderJob")
public class ReminderJob extends QuartzJobBean {
    private static final AppLogger log = new AppLogger(ReminderJob.class);

    /**
     * The reminder service
     */
    private transient ReminderService reminderService;

    /**
     * Executes the actual job with the given context bundle.
     * @param context the JobExecutionContext
     * @throws JobExecutionException if error occurs
     */
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        try {
            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

            reminderService.processDueReminders();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Sets the reminder service
     * @param reminderService the {@link ReminderService}
     */
    public void setReminderService(ReminderService reminderService) {
        this.reminderService = reminderService;
    }
}
