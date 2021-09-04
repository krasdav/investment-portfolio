package org.dav.equitylookup.config;

import org.dav.equitylookup.datacache.CacheStore;
import org.dav.equitylookup.model.cache.CoinInfo;
import org.dav.equitylookup.model.cache.Stock;
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
    public CacheStore<Stock> stockCache() {
        return new CacheStore<>(30, TimeUnit.SECONDS);
    }

    @Bean
    public CacheStore<CoinInfo> coinCache() {
        return new CacheStore<>(30, TimeUnit.SECONDS);
    }
}
