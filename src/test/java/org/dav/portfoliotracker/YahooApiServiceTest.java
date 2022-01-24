package org.dav.portfoliotracker;

import org.dav.portfoliotracker.service.impl.stock.YahooApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YahooApiServiceTest {

    @Autowired
    YahooApiService yahooApiService;

    @Test
    void invoke(){
    }
}
