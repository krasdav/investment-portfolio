package org.dav.portfoliotracker.config;

import org.dav.portfoliotracker.datacache.CacheStore;
import org.dav.portfoliotracker.model.cache.CryptoCached;
import org.dav.portfoliotracker.model.cache.StockCached;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CacheStore<StockCached> stockCache() {
        return new CacheStore<>(30, TimeUnit.SECONDS);
    }

    @Bean
    public CacheStore<CryptoCached> coinCache() {
        return new CacheStore<>(30, TimeUnit.SECONDS);
    }
}
