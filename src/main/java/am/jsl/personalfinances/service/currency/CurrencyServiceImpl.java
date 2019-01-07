package am.jsl.personalfinances.service.currency;

import am.jsl.personalfinances.dao.currency.CurrencyDao;
import am.jsl.personalfinances.domain.Currency;
import am.jsl.personalfinances.service.BaseServiceImpl;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The service implementation of the {@link CurrencyService}.
 * @author hamlet
 */
@Service("currencyService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CurrencyServiceImpl extends BaseServiceImpl<Currency> implements CurrencyService {

    /**
     * The currency dao
     */
    @Autowired
    @Qualifier("currencyDao")
    private CurrencyDao currencyDao;

    @Override
    public Currency getByCode(String isoCode) {
        return currencyDao.getByCode(isoCode);
    }

    @Override
    @Cacheable(value = "currencies")
    public List<Currency> list() {
        return currencyDao.list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {@CacheEvict(value = "currencies", allEntries = true)})
    public void delete(String code) {
        currencyDao.delete(code);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {@CacheEvict(value = "currencies", allEntries = true)})
    public void create(Currency object) throws Exception {
        currencyDao.create(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @Caching(evict = {@CacheEvict(value = "currencies", allEntries = true)})
    public void update(Currency object) throws Exception {
        currencyDao.update(object);
    }

    @Override
    @Cacheable("rates")
    public Double getRate(String from, String to) {
        try {
            String query = from + "_" + to;

            HttpResponse<JsonNode> result = Unirest.get("http://free.currencyconverterapi.com/api/v5/convert?q=" + query + "&compact=y")
                    .header("accept",  "application/json").asJson();
            JsonNode node =  result.getBody();
            JSONObject jsonObject = node.getObject();
            JSONObject rateObject = jsonObject.getJSONObject(query);

            return rateObject.getDouble("val");
        } catch (Exception ex) {
            log.error("Error while loading currency exchange rates. {}", ex);
        }

        return 1.0;
    }

    @Override
    @CacheEvict(cacheNames="rates", allEntries = true)
    @Scheduled(initialDelay = 1000 * 3600 * 6, fixedDelay = 1000 * 3600 * 6)
    public void clearRatesCache() {
        log.info("Clearing rates cache");
    }
}
