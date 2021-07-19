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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.math.BigDecimal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StockSearchService stockSearchService;

    @Autowired
    private StockService stockService;

    @GetMapping("/users/list")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user-list";
    }

    @PostMapping("/users/stocks/list")
    public String listStocks(@ModelAttribute User user, Model model) throws IOException {
        BigDecimal portfolioValue = new BigDecimal(0);
        for ( Stock stock : userService.getUserByNickname(user.getNickname()).getStocks()){
            stock.setPrice(stockSearchService.findPrice(stockSearchService.findStock(stock.getTicker())));
            portfolioValue = portfolioValue.add(stock.getPrice());
        }
        model.addAttribute("stocks", userService.getUserByNickname(user.getNickname()).getStocks());
        model.addAttribute("portfolioValue",portfolioValue);
        return "user-stock-list";
    }

    @GetMapping("/users/stocks/list")
    public String listStocksForm(Model model) {
        model.addAttribute("user", new User());
        return "user-stock-query";
    }

    @GetMapping("/users/add")
    public String saveUser(Model model) {
        model.addAttribute("user", new User());
        return "user-add";
    }

    @GetMapping("/main")
    public String frontPage() {
        return "main";
    }

    @PostMapping("/users/add")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        userService.saveUser(user);
        model.addAttribute("userNickname", user.getNickname());
        model.addAttribute("userId", user.getId());
        return "user-result";
    }

    @GetMapping("/users/{nickname}")
    public String getUserByNickname(@PathVariable("nickname") String nickname, Model model) {
        User user = userService.getUserByNickname(nickname);
        model.addAttribute("userNickname", user.getNickname());
        model.addAttribute("userId", user.getId());
        return "user-details";
    }

}
