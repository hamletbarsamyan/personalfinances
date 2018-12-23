package am.jsl.personalfinances.service.databasedump;

import am.jsl.personalfinances.log.AppLogger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Simple quartz job that periodically dumps database.
 * @author hamlet
 */
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class DatabaseDumpJob extends QuartzJobBean {

    private static final AppLogger log = new AppLogger(DatabaseDumpJob.class);

    /**
     * The database dump service.
     */
    private DatabaseDumpService databaseDumpService;

    /**
     * Executes the actual job with the given context bundle.
     * @param context the JobExecutionContext
     * @throws JobExecutionException if error occurs
     */
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        try {
            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

            databaseDumpService.dumpDatabase();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Sets the database dump service.
     * @param databaseDumpService the {@link DatabaseDumpService}
     */
    public void setDatabaseDumpService(DatabaseDumpService databaseDumpService) {
        this.databaseDumpService = databaseDumpService;
    }
}
