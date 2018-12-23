package am.jsl.personalfinances.config;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring managed EhCache configuration.
 */
@Configuration
@EnableCaching
public class EhCacheConfig {

	/**
	 * Creates {@link Ehcache}s cacheManager with appropriate cache configurations.
	 * Used for storing the results from queries userByName, currencies, transaction search.
	 * @return the CacheManager
	 */
	@Bean(destroyMethod = "shutdown")
	public net.sf.ehcache.CacheManager ehCacheManager() {
		System.setProperty("net.sf.ehcache.skipUpdateCheck", "false");

		// user by name cache
		net.sf.ehcache.CacheManager manager = net.sf.ehcache.CacheManager.create();

		CacheConfiguration cacheConfig = new CacheConfiguration();
		cacheConfig.setName("userByName");
		cacheConfig.maxEntriesLocalHeap(200);
		cacheConfig.maxEntriesLocalDisk(10);
		cacheConfig.timeToLiveSeconds(3600*2);
		cacheConfig.timeToIdleSeconds(0);
		Cache cache = new Cache(cacheConfig);
		manager.addCache(cache);

		// currencies cache
		cacheConfig = new CacheConfiguration();
		cacheConfig.setName("currencies");
		cacheConfig.maxEntriesLocalHeap(200);
		cacheConfig.maxEntriesLocalDisk(200);
		cache = new Cache(cacheConfig);
		manager.addCache(cache);

		// exchange rates cache
		cacheConfig = new CacheConfiguration();
		cacheConfig.setName("rates");
		cacheConfig.maxEntriesLocalHeap(200);
		cacheConfig.maxEntriesLocalDisk(200);
		cache = new Cache(cacheConfig);
		manager.addCache(cache);

		// Transactions Search cache
		cacheConfig = new CacheConfiguration();
		cacheConfig.setName("transactionSearch");
		cacheConfig.maxEntriesLocalHeap(200);
		cacheConfig.maxEntriesLocalDisk(10);
		cacheConfig.timeToLiveSeconds(3600*2);
		cache = new Cache(cacheConfig);
		manager.addCache(cache);
		return manager;
	}

	/**
	 * Creates CacheManager backed by EhCache manager.
	 * @return the CacheManager backed by EhCache manager.
	 */
	@Bean
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheManager());
	}
}
