package am.jsl.personalfinances.config;

import am.jsl.personalfinances.util.Constants;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

/**
 * The spring managed email configuration.
 * @author hamlet
 */
@Configuration
public class SpringMailConfig implements ApplicationContextAware {

    private static final String JAVA_MAIL_FILE = "classpath:mail/javamail.properties";

    @Value("${mail.server.host}")
    private String host;

    @Value("${mail.server.port}")
    private String port;

    @Value("${mail.server.protocol}")
    private String protocol;

    @Value("${mail.server.username}")
    private String username;

    @Value("${mail.server.password}")
    private String password;

    @Value("${mailer.from.address}")
    private String from;

    @Value("${mailer.contact_email}")
    private String contactEmail;

    private ApplicationContext applicationContext;

    /**
     * Creates {@link JavaMailSenderImpl} and configures email settings.
     * @return the JavaMailSender
     * @throws IOException if error occurs
     */
    @Bean
    public JavaMailSender mailSender() throws IOException {

        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        // JavaMail-specific mail sender configuration, based on javamail.properties
        final Properties javaMailProperties = new Properties();
        javaMailProperties.load(this.applicationContext.getResource(JAVA_MAIL_FILE).getInputStream());
        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }

    /**
     *  Creates {@link ResourceBundleMessageSource} for loading email messages from properties file.
     * @return the ResourceBundleMessageSource
     */
    @Bean
    public ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("mail/mail_messages");
        return messageSource;
    }

    /**
     * Creates Thymeleaf's {@link TemplateEngine} for generating emails from html template.
     * @return the TemplateEngine
     */
    @Bean(name = "emailTemplateEngine")
    public TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        // Message source, internationalization specific to emails
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    /**
     * Creates Thymeleaf's {@link ITemplateResolver} for resolving email templates.
     * @return the ITemplateResolver
     */
    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(2);
        templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
        templateResolver.setPrefix("/mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding(Constants.UTF8);
        templateResolver.setCacheable(true);
        return templateResolver;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
