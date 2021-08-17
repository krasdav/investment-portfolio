package org.dav.equitylookup.controller;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.dto.StockDTO;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.form.UserRegistrationForm;
import org.dav.equitylookup.service.PortfolioService;
import org.dav.equitylookup.service.UserService;
import org.dav.equitylookup.service.impl.YahooApiService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final PortfolioService portfolioService;

    private final YahooApiService yahooApiService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/index")
    public String frontPage(Model model) throws IOException {
        logger.error("Front Page");
        List<StockDTO> topStocks = new ArrayList<>();
        topStocks.add(modelMapper.map(yahooApiService.findStock("GOOG"),StockDTO.class));
        topStocks.add(modelMapper.map(yahooApiService.findStock("INTC"),StockDTO.class));
        topStocks.add(modelMapper.map(yahooApiService.findStock("AAPL"),StockDTO.class));
        topStocks.add(modelMapper.map(yahooApiService.findStock("CSCO"),StockDTO.class));
        topStocks.add(modelMapper.map(yahooApiService.findStock("BABA"),StockDTO.class));
        topStocks.add(modelMapper.map(yahooApiService.findStock("SPOT"),StockDTO.class));
        topStocks.add(modelMapper.map(yahooApiService.findStock("DBX"),StockDTO.class));
        topStocks.add(modelMapper.map(yahooApiService.findStock("ADBE"),StockDTO.class));
        model.addAttribute("stocks",topStocks);
        return "index";
    }

    @GetMapping("/registration")
    public String saveUser(Model model) {
        model.addAttribute("user", new UserRegistrationForm());
        return "registration-form";
    }

    @PostMapping("/registration")
    public String saveUser(@Valid @ModelAttribute("user") UserRegistrationForm userRegistrationForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration-form";
        } else if (userService.getUserByUsername(userRegistrationForm.getUsername()) != null &&
                userService.findByEmail(userRegistrationForm.getEmail()) != null) {
            bindingResult.rejectValue("username", "user.username", "User with this email and username already exists");
            return "registration-form";
        }
        User user = new User(userRegistrationForm.getUsername());
        user.setPassword(userRegistrationForm.getPassword());
        user.setRole("ROLE_USER");
        user.setEmail(userRegistrationForm.getEmail());
        userService.saveUser(user);
        Portfolio portfolio = new Portfolio(userRegistrationForm.getPortfolioName(), user);
        portfolioService.savePortfolio(portfolio);
        user.setPortfolio(portfolio);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "registration-success";
    }
}
