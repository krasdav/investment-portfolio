package org.dav.equitylookup;

import org.dav.equitylookup.model.StockWrapper;
import org.dav.equitylookup.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StockServiceTest {

    @Autowired
    StockService stockService;

    @Test
    void invoke(){
        StockWrapper stock = stockService.findStock("GOOG");
        System.out.println(stock.getStock());
    }
}
