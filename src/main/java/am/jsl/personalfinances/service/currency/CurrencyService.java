package am.jsl.personalfinances.service.currency;

import am.jsl.personalfinances.domain.Currency;
import am.jsl.personalfinances.service.BaseService;

import java.util.List;

/**
 * Service interface which defines all the methods for working with {@link Currency} domain object.
 * @author hamlet
 */
public interface CurrencyService extends BaseService<Currency> {
    /**
     * Returns a currency by code.
     * @return the currency
     */
    Currency getByCode(String isoCode);

    /**
     * Returns all currencies.
     * @return list of currencies
     */
    List<Currency> list();

    /**
     * Deletes a currency with the given code.
     * @param code the currency code
     */
    void delete(String code);

    /**
     * Returns exchange rate for the given currencies.
     * @param fromCurrency the source currency
     * @param toCurrency the target currency
     * @return the exchange rate
     */
    Double getRate(String fromCurrency, String toCurrency);

    /**
     * Clears rates cache.
     */
    void clearRatesCache();
}
