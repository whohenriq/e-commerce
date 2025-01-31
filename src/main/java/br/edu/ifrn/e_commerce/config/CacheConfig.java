package br.edu.ifrn.e_commerce.config;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager manager = new ConcurrentMapCacheManager();
        manager.setAllowNullValues(false);
        manager.setCacheNames(Arrays.asList("produtosCache"));

        return manager;
    }

    @CacheEvict(value = "produtosCache", allEntries = true)
    @Scheduled(fixedDelay = 7000, initialDelay = 0)
    public void evictProdutosCache() {
        System.err.println("Evicting Products Cache");
    }
}
