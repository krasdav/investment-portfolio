package org.dav.equitylookup;

import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.service.StockSearchService;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@SpringBootApplication
@Controller
@EnableJpaRepositories("org.dav.equitylookup.repository")
public class EquityLookUpApplication {

	@Autowired
	private UserService userService;

	@Autowired
	private StockService stockService;

	@Autowired
	private StockSearchService stockSearchService;

	public static void main(String[] args) {
		SpringApplication.run(EquityLookUpApplication.class, args);
	}

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			User newUser = new User("Michal");
			newUser.setStocks(new ArrayList<>());
			userService.saveUser(newUser);
			Stock newStock = new Stock("GOOG");
			newUser.addStock(newStock);
			newStock.setPrice(stockSearchService.findPrice(stockSearchService.findStock(newStock.getTicker())));
			newStock.setBoughtPrice(stockSearchService.findPrice(stockSearchService.findStock(newStock.getTicker())));
			newStock.setUser(newUser);
			stockService.saveStock(newStock);

			newStock = new Stock("AAPL");
			newStock.setPrice(stockSearchService.findPrice(stockSearchService.findStock(newStock.getTicker())));
			newStock.setBoughtPrice(stockSearchService.findPrice(stockSearchService.findStock(newStock.getTicker())));
			newStock.setUser(newUser);

			newStock = new Stock("INTC");
			newStock.setPrice(stockSearchService.findPrice(stockSearchService.findStock(newStock.getTicker())));
			newStock.setBoughtPrice(stockSearchService.findPrice(stockSearchService.findStock(newStock.getTicker())));
			newStock.setUser(newUser);
		};
	}

}
