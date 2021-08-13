package org.dav.equitylookup;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.service.PortfolioService;
import org.dav.equitylookup.service.ShareService;
import org.dav.equitylookup.service.implementation.StockSearchService;
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

	private final PortfolioService portfolioService;

	private final ShareService shareService;

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
			User michal = new User("Michal");
			michal.setRole("ROLE_USER");
			michal.setPassword("pw");
			userService.saveUser(michal);

			Stock google = new Stock("GOOG");
			stockService.saveStock(google);

			Stock apple = new Stock("AAPL");
			stockService.saveStock(apple);

			Stock intel = new Stock("INTC");
			stockService.saveStock(intel);

			Portfolio portfolio = new Portfolio("Michal's Portfolio", michal);
			portfolioService.savePortfolio(portfolio);

			michal.setPortfolio(portfolio);

			Share shareGoogle = new Share(stockSearchService.findPrice(stockSearchService.findStock(google.getTicker())),google,michal);
			shareService.saveShare(shareGoogle);

			Share shareIntel = new Share(stockSearchService.findPrice(stockSearchService.findStock(intel.getTicker())),intel,michal);
			shareService.saveShare(shareIntel);

			portfolio.addShare(shareGoogle);
			portfolio.addShare(shareIntel);

//			intel.addUser(michal);
//			userService.addStockToUser(intel,michal);
//
//			google.addUser(michal);
//			userService.addStockToUser(google,michal);
		};
	}

}
