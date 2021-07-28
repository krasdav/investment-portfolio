package org.dav.equitylookup.controller;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.dto.StockDTO;
import org.dav.equitylookup.dto.UserDTO;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.UserService;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    private final StockService stockService;

    private final ModelMapper modelMapper;

    @GetMapping("/user")
    public String welcomePage(Model model, Principal user) {
        model.addAttribute("username", user.getName());
        return "user/user-welcome";
    }

    @GetMapping("/user/stocks/list")
    public String listStocksForm(Model model, Principal loggedUser) throws IOException {
        User user = userService.getUserByUsername(loggedUser.getName());
        userService.updatePortfolioValue(user);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        model.addAttribute("stocks", userDTO.getStocks());
        model.addAttribute("portfolioValue", userDTO.getPortfolio());
        return "user/user-stock-list";
    }

    @GetMapping("/user/stocks/add")
    public String addStock(Model model){
        model.addAttribute("stock", new Stock());
        return "user/user-stock-add";
    }

    @PostMapping("user/stocks/add")
    public String addStock(@ModelAttribute("stock") StockDTO stockDto, Model model, Principal loggedUser) throws IOException {
        Stock stock = modelMapper.map(stockDto, Stock.class);
        User user = userService.getUserByUsername(loggedUser.getName());
        stockService.addStock(stock, user);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("ticker", stock.getTicker());
        return "user/user-stock-result";
    }

}
