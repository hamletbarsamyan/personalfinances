package am.jsl.personalfinances.service.currency;

import am.jsl.personalfinances.domain.Currency;
import am.jsl.personalfinances.service.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains CurrencyService tests.
 */
public class CurrencyServiceTest extends BaseTest {

    /**
     * Executed before all CurrencyServiceTest tests.
     * @throws Exception if failed
     */
    @BeforeAll
    public void setUp() throws Exception {
        user = createUser();
    }

    @Test
    @DisplayName("Create Currency Test")
    public void testCreateCurrency() throws Exception {
        log.info("Starting test for create currency");
        Currency currency = new Currency();
        currency.setCode("TST");
        currency.setName("Test Currency");
        currency.setSymbol("t");
        currencyService.create(currency);

        currency = currencyService.getByCode(currency.getCode());

        assertNotNull(currency);
        log.info("Finished test for create currency");
    }

    @Test
    @DisplayName("Update Currency Test")
    public void testUpdateCurrency() throws Exception {
        log.info("Starting test for update currency");
        String name = "name updated";
        String symbol = "t1";

        Currency currency = createCurrency();

        // update currency
        currency.setName(name);
        currency.setSymbol(symbol);

        currencyService.update(currency);

        // validate currency
        currency = currencyService.getByCode(currency.getCode());

        assertNotNull(currency);

        assertEquals(name, currency.getName());
        assertEquals(symbol, currency.getSymbol());

        log.info("Finished test for update currency");
    }

    @Test
    @DisplayName("Delete Currency Test")
    public void testDeleteCurrency() throws Exception {
        log.info("Starting test for delete currency");

        Currency currency = createCurrency();
        String isoCode = currency.getCode();

        currencyService.delete(isoCode);

        // validate currency
        currency = currencyService.getByCode(isoCode);
        assertNull(currency);

        log.info("Finished test for delete currency");
    }

    @Test
    @DisplayName("List Currencies Test")
    public void testLookupCurrencies() throws Exception {
        log.info("Starting test for list currencies");
        List<Currency> currencies = currencyService.list();

        assertTrue(currencies.size() > 0);
        log.info("Finished test for list currencies");
    }

    @Test
    @DisplayName("Get Rate Test")
    public void testGetRate() throws Exception {
        log.info("Starting test for get rate");
        double rate = currencyService.getRate(CURRENCY_EUR, CURRENCY_USD);

        assertTrue(rate >= 1);
        log.info("Finished test for get rate");
    }

    /**
     * Executed after all CurrencyServiceTest tests.
     * @throws Exception if failed
     */
    @AfterAll
    public void cleanUp() throws Exception {
        super.cleanUp();
    }
}
