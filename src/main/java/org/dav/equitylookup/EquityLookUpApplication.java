package org.dav.equitylookup;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.service.PortfolioService;
import org.dav.equitylookup.service.impl.YahooApiService;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@SpringBootApplication
@Controller
@EnableJpaRepositories("org.dav.equitylookup.repository")
public class EquityLookUpApplication {

    private final UserService userService;

    private final StockService stockService;

    private final YahooApiService yahooApiService;

    private final PortfolioService portfolioService;

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

            Stock google = yahooApiService.findStock("GOOG");

            Stock apple = yahooApiService.findStock("APPL");;

            Stock intel = yahooApiService.findStock("INTC");;


            Portfolio portfolio = new Portfolio("Michal's Portfolio", michal);
            portfolioService.savePortfolio(portfolio);

            michal.setPortfolio(portfolio);

            Share shareGoogle = stockService.obtainShare(google.getTicker(), michal);
//            shareService.saveShare(shareGoogle);

            Share shareIntel = stockService.obtainShare(intel.getTicker(), michal);
//            shareService.saveShare(shareIntel);
            portfolioService.addShare(shareGoogle, portfolio.getName());
            portfolioService.addShare(shareIntel, portfolio.getName());

        };
    }

}
