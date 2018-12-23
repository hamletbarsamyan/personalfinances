package am.jsl.personalfinances.web.dialect;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Custom Thymeleaf expression factory.
 * @author hamlet
 */
public class PersonalFinancesExpressionFactory implements IExpressionObjectFactory {
    private static final String PERSONAL_FINANCES_EVALUATION_VARIABLE_NAME = "personalfinances";

    private static final Set<String> ALL_EXPRESSION_OBJECT_NAMES = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(PERSONAL_FINANCES_EVALUATION_VARIABLE_NAME)));

    @Override
    public Set<String> getAllExpressionObjectNames() {
        return ALL_EXPRESSION_OBJECT_NAMES;
    }

    @Override
    public Object buildObject(IExpressionContext context, String expressionObjectName) {
        if (PERSONAL_FINANCES_EVALUATION_VARIABLE_NAME.equals(expressionObjectName)) {
            return new PersonalFinances(context.getLocale());
        }

        return null;
    }

    @Override
    public boolean isCacheable(String expressionObjectName) {
        return PERSONAL_FINANCES_EVALUATION_VARIABLE_NAME.equals(expressionObjectName);
    }
}
