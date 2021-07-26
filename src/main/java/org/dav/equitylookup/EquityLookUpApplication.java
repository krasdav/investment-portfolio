package org.dav.equitylookup;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.service.StockSearchService;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@RequiredArgsConstructor
@SpringBootApplication
@Controller
@EnableJpaRepositories("org.dav.equitylookup.repository")
public class EquityLookUpApplication {

	private final UserService userService;

	private final StockService stockService;

	private final StockSearchService stockSearchService;

	public static void main(String[] args) {
		SpringApplication.run(EquityLookUpApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			User newUser1 = new User("David");
			newUser1.setStocks(new ArrayList<>());
			newUser1.setRoles("ADMIN");
			newUser1.setPassword("pw");
			userService.saveUser(newUser1);
			User newUser = new User("Michal");
			newUser.setStocks(new ArrayList<>());
			newUser.setRoles("ROLE_USER");
			newUser.setPassword("pw");
			userService.saveUser(newUser);
			Stock newStock = new Stock("GOOG");
			newUser.addStock(newStock);
			newStock.setBoughtPrice(stockSearchService.findPrice(stockSearchService.findStock(newStock.getTicker())));
			newStock.setUser(newUser);
			stockService.saveStock(newStock);

			newStock = new Stock("AAPL");
			newStock.setBoughtPrice(stockSearchService.findPrice(stockSearchService.findStock(newStock.getTicker())));
			newStock.setUser(newUser);
			stockService.saveStock(newStock);

			newStock = new Stock("INTC");
			newStock.setBoughtPrice(stockSearchService.findPrice(stockSearchService.findStock(newStock.getTicker())));
			newStock.setUser(newUser);
			stockService.saveStock(newStock);
		};
	}

}
