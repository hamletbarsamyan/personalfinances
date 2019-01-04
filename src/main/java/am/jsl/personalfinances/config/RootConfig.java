package am.jsl.personalfinances.config;

import am.jsl.personalfinances.service.databasedump.DatabaseDumpJob;
import am.jsl.personalfinances.service.databasedump.DatabaseDumpService;
import am.jsl.personalfinances.service.reminder.ReminderJob;
import am.jsl.personalfinances.service.reminder.ReminderService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring managed root configuration.
 * Configures applications jobs and PropertySourcesPlaceholderConfigurer.
 * @author hamlet
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"am.jsl.personalfinances"})
public class RootConfig {
    /**
     * Cron expression for database dump job.
     */
    @Value("${personalfinances.db.export.cronExpression}")
    private String dbDumpCronExp;

    /**
     * Cron expression for reminder processing job.
     */
    @Value("${personalfinances.reminder.cronExpression}")
    private String reminderCronExp;

    /**
     * The database dump service.
     */
    @Autowired
    @Qualifier("databaseDumpService")
    private DatabaseDumpService databaseDumpService;

    /**
     * The reminder service.
     */
    @Autowired
    @Qualifier("reminderService")
    private ReminderService reminderService;

    /**
     * Creates Quartz {@link JobDetail} instance for executing database dump job.
     * @return the JobDetail
     */
    @Bean
    public JobDetail databaseDumpJobDetail() {
        // pass databaseDumpService
        Map<String, Object> jobDataAsMap = new HashMap<>();
        jobDataAsMap.put("databaseDumpService", databaseDumpService);

        return JobBuilder.newJob(DatabaseDumpJob.class).withIdentity("databaseDumpJob")
                .setJobData(new JobDataMap(jobDataAsMap)).storeDurably().build();
    }

    /**
     *  Creates with Quartz {@link Trigger} instance for scheduling databaseDumpJob.
     * @return the Trigger
     */
    @Bean
    public Trigger databaseDumpJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(3000).repeatForever();

        return TriggerBuilder.newTrigger().forJob(databaseDumpJobDetail())
                .withIdentity("databaseDumpJobTrigger").withSchedule(scheduleBuilder).build();
    }

    /**
     * Creates Quartz {@link JobDetail} instance for executing reminder processing job.
     * @return the JobDetail
     */
    @Bean
    public JobDetail reminderJobDetail() {
        // pass reminderService
        Map<String, Object> jobDataAsMap = new HashMap<>();
        jobDataAsMap.put("reminderService", reminderService);

        return JobBuilder.newJob(ReminderJob.class).withIdentity("reminderJob")
                .setJobData(new JobDataMap(jobDataAsMap)).storeDurably().build();
    }

    /**
     * Creates with Quartz {@link Trigger} instance for scheduling scheduling reminderJob.
     * @return the Trigger
     */
    @Bean
    public Trigger reminderJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(3000).repeatForever();

        return TriggerBuilder.newTrigger().forJob(reminderJobDetail())
                .withIdentity("reminderJobTrigger").withSchedule(scheduleBuilder).build();
    }

    /**
     * Creates Springs {@link PropertySourcesPlaceholderConfigurer} for resolving ${...} placeholders
     * within Spring bean definitions.
     *
     * @return the PropertySourcesPlaceholderConfigurer
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}