package org.dav.equitylookup;

import org.dav.equitylookup.model.StockWrapper;
import org.dav.equitylookup.service.StockSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockSearchServiceTest {

    @Autowired
    StockSearchService stockSearchService;

    @Test
    void invoke(){
        StockWrapper stock = stockSearchService.findStock("GOOG");
        System.out.println(stock.getStock());
    }
}
