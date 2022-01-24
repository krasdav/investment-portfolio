package org.dav.portfoliotracker;

import lombok.RequiredArgsConstructor;
import org.dav.portfoliotracker.config.BeanConfig;
import org.dav.portfoliotracker.model.Portfolio;
import org.dav.portfoliotracker.model.enums.Operation;
import org.dav.portfoliotracker.service.PortfolioService;
import org.dav.portfoliotracker.service.StockService;
import org.dav.portfoliotracker.service.UserService;
import org.dav.portfoliotracker.model.TransactionRecord;
import org.dav.portfoliotracker.model.User;
import org.dav.portfoliotracker.model.cache.StockCached;
import org.dav.portfoliotracker.service.CryptoService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@SpringBootApplication
@Controller
@EnableJpaRepositories("org.dav.portfoliotracker")
public class PortfolioTrackerApplication {

    private final UserService userService;

    private final StockService stockService;

    private final PortfolioService portfolioService;

    private final PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(PortfolioTrackerApplication.class, args);
    }

    @Bean
    InitializingBean sendDatabase() {
        return () -> {
            System.out.println(org.hibernate.Version.getVersionString());
            User michal = new User("Michal");
            michal.setRole("ROLE_USER");
            michal.setPassword(passwordEncoder.encode("pw"));
            userService.saveUser(michal);

            StockCached google = stockService.getStock("GOOG");

            StockCached intel = stockService.getStock("INTC");

            Portfolio portfolio = new Portfolio("Michal's Portfolio", michal);
            portfolioService.savePortfolio(portfolio);

            michal.setPortfolio(portfolio);

            TransactionRecord transactionRecord = new TransactionRecord.TransactionRecordBuilder()
                    .timeOfPurchase(LocalDate.now())
                    .asset(intel.getTicker())
                    .operation(Operation.BUY)
                    .quantity(1)
                    .value(new BigDecimal("59.22"))
                    .build();
            portfolioService.addStock(transactionRecord, portfolio.getName());

            transactionRecord = new TransactionRecord.TransactionRecordBuilder()
                    .timeOfPurchase(LocalDate.now())
                    .asset(google.getTicker())
                    .operation(Operation.BUY)
                    .quantity(1)
                    .value(new BigDecimal("1599.12"))
                    .build();

            portfolioService.addStock(transactionRecord, portfolio.getName());

            transactionRecord = new TransactionRecord.TransactionRecordBuilder()
                    .timeOfPurchase(LocalDate.now())
                    .asset("BTC")
                    .operation(Operation.BUY)
                    .quantity(1)
                    .value(new BigDecimal("43000"))
                    .build();

            portfolioService.addCrypto(transactionRecord, portfolio.getName());
        };
    }

}
