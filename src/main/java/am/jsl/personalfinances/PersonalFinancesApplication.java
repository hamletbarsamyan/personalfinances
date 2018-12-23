package am.jsl.personalfinances;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class that used to bootstrap and launch a Spring application.
 * @author hamlet
 */
@SpringBootApplication
public class PersonalFinancesApplication {
    public static void main(String[] args) {
        SpringApplication.run(PersonalFinancesApplication.class, args);
    }
}