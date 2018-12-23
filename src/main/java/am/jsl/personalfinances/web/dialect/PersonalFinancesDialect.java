package am.jsl.personalfinances.web.dialect;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

/**
 * Custom Thymeleaf dialect.
 * @author hamlet
 */
public class PersonalFinancesDialect extends AbstractDialect implements IExpressionObjectDialect {
    private final IExpressionObjectFactory PF_TRACKER_EXPRESSION_OBJECTS_FACTORY = new PersonalFinancesExpressionFactory();

    public PersonalFinancesDialect() {
        super("personalfinances");
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return PF_TRACKER_EXPRESSION_OBJECTS_FACTORY;
    }
}
