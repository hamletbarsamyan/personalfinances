package am.jsl.personalfinances.service;

import am.jsl.personalfinances.config.EhCacheConfig;
import am.jsl.personalfinances.config.SpringMailConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring application context configuration for junit tests.
 * @author hamlet
 */
@Configuration
@ComponentScan(basePackages = {"am.jsl.personalfinances.dao", "am.jsl.personalfinances.service"})
@Import({EhCacheConfig.class, SpringMailConfig.class})
@EnableTransactionManagement
@EnableAutoConfiguration
public class TestApplicationContext {

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

    /**
     * Creates the {@link PasswordEncoder} backed by {@link BCryptPasswordEncoder}.
     *
     * @return the PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
