package org.dav.equitylookup.datacache;

import org.dav.equitylookup.model.Stock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheStoreBeans {

    @Bean
    public CacheStore<Stock> stockCache(){
        return new CacheStore<>(30, TimeUnit.SECONDS);
    }
}
