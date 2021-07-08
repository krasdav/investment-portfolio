package org.dav.equitylookup;

import org.dav.equitylookup.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
@RestController
public class EquityLookUpApplication {

	@Autowired
	private StockService stockService;

	public static void main(String[] args) {
		SpringApplication.run(EquityLookUpApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello() throws IOException {
		return stockService.findPrice(stockService.findStock("INTC")).toString();
	}

}
