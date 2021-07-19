package org.dav.equitylookup.controller;

import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.service.StockSearchService;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class StockController {

    @Autowired
    private UserService userService;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockSearchService stockSearchService;

    @GetMapping("/stocks/add")
    public String addStock(Model model) {
        model.addAttribute("stock", new Stock());
        model.addAttribute("user", new User());
        return "user-stock-add";
    }

    @PostMapping("/stocks/add")
    public String addStock(@ModelAttribute("user") User user, @ModelAttribute("stock") Stock stock, Model model) throws IOException {
        User newUser = userService.getUserByNickname(user.getNickname());
        newUser.addStock(stock);
        //TODO : refresh price
        stock.setPrice(stockSearchService.findPrice(stockSearchService.findStock(stock.getTicker())));
        stock.setBoughtPrice(stockSearchService.findPrice(stockSearchService.findStock(stock.getTicker())));
        stock.setUser(newUser);
        stockService.saveStock(stock);
        model.addAttribute("userNickname", user.getNickname());
        model.addAttribute("ticker", stock.getTicker());
        return "user-stock-result";
    }

}
