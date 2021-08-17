package org.dav.equitylookup.controller;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.exceptions.PortfolioNotFoundException;
import org.dav.equitylookup.exceptions.ShareNotFoundException;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.dto.PortfolioDTO;
import org.dav.equitylookup.model.form.ShareForm;
import org.dav.equitylookup.service.PortfolioService;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.UserService;
import org.dav.equitylookup.service.impl.YahooApiService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    private final StockService stockService;

    private final ModelMapper modelMapper;

    private final YahooApiService yahooApiService;

    private final PortfolioService portfolioService;

    @GetMapping("/user")
    public String welcomePage(Model model, Principal user) {
        model.addAttribute("username", user.getName());
        return "user/user-welcome";
    }

    @GetMapping("/user/stocks/list")
    public String listStocksForm(Model model, Principal loggedUser) throws IOException {
        User user = userService.getUserByUsername(loggedUser.getName());
        stockService.updateStockPrices(user.getPortfolio().getShares());
        try {
            portfolioService.updatePortfolioValue(user.getPortfolio().getName());
        } catch (PortfolioNotFoundException e) {
            e.printStackTrace();
        }
        PortfolioDTO portfolioDTO = modelMapper.map(user.getPortfolio(), PortfolioDTO.class);
        model.addAttribute("portfolio", portfolioDTO);
        return "user/user-stock-list";
    }

    @GetMapping("/user/stocks/add")
    public String addStock(Model model) {
        model.addAttribute("share", new ShareForm());
        return "user/user-stock-add";
    }

    @PostMapping("user/stocks/add")
    public String addStock(@ModelAttribute("share") ShareForm shareForm, Model model, Principal loggedUser) throws IOException {
        User user = userService.getUserByUsername(loggedUser.getName());
        Stock stock = stockService.getStockByTicker(shareForm.getTicker());
        if( stock == null ){
            stock = new Stock(shareForm.getTicker());
            stockService.saveStock(stock);
        }
        Share share = new Share(yahooApiService.findPrice(stock),stock, user);
        try {
            portfolioService.addShare(share,user.getPortfolio().getName());
        } catch (PortfolioNotFoundException e) {
            e.printStackTrace();
        }

        model.addAttribute("username", user.getUsername());
        model.addAttribute("ticker", share.getTicker());
        return "user/user-stock-result";
    }

    @PostMapping("/user/stocks/remove/")
    public String removeStockFromUser(@RequestParam long id,@RequestParam String portfolioName) throws ShareNotFoundException, PortfolioNotFoundException {
        portfolioService.removeShareById(id,portfolioName);
        return "redirect:/user/stocks/list";
    }

}
