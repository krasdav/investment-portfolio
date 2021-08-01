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
			User david = new User("David");
			david.setStocks(new ArrayList<>());
			david.setRole("ADMIN");
			david.setPassword("pw");
			userService.saveUser(david);

			User michal = new User("Michal");
			michal.setStocks(new ArrayList<>());
			michal.setRole("ROLE_USER");
			michal.setPassword("pw");
			userService.saveUser(michal);

			Stock google = new Stock("GOOG");
			stockService.saveStock(google);

			Stock apple = new Stock("AAPL");
			stockService.saveStock(apple);

			Stock intel = new Stock("INTC");
			stockService.saveStock(intel);

			userService.addStockToUser(intel,michal);
			intel.addUser(michal);

			userService.addStockToUser(google,michal);
			google.addUser(michal);

			userService.saveUser(michal);

		};
	}

}
