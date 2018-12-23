package am.jsl.personalfinances.web.format;

import am.jsl.personalfinances.domain.user.Role;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * Role formatter for {@link Role} objects.
 * @author hamlet
 */
@Component
public class RoleFormatter implements Formatter<Role> {
    @Override
    public Role parse(String object, Locale locale) throws ParseException {
        return Role.valueOf(object);
    }

    @Override
    public String print(Role object, Locale locale) {
        return object.name();
    }
}
