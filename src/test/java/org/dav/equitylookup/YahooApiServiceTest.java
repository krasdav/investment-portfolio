package org.dav.equitylookup;

import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.service.impl.YahooApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YahooApiServiceTest {

    @Autowired
    YahooApiService yahooApiService;

    @Test
    void invoke(){
        Stock stock = yahooApiService.findStock("GOOG");
    }
}
