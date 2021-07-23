package org.dav.equitylookup.controller;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.dto.StockDTO;
import org.dav.equitylookup.dto.UserDTO;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class StockController {

    private final UserService userService;
    private final StockService stockService;
    private final ModelMapper modelMapper;

    @GetMapping("/stocks/add")
    public String addStock(Model model) {
        model.addAttribute("stock", new Stock());
        model.addAttribute("user", new User());
        return "user-stock-add";
    }

    @PostMapping("/stocks/add")
    public String addStock(@ModelAttribute("user") UserDTO userDto, @ModelAttribute("stock") StockDTO stockDto, Model model) throws IOException {
        User user = modelMapper.map(userDto, User.class);
        Stock stock = modelMapper.map(stockDto,Stock.class);
        user = userService.getUserByNickname(user.getNickname());
        stockService.addStock(stock,user);
        model.addAttribute("userNickname", user.getNickname());
        model.addAttribute("ticker", stock.getTicker());
        return "user-stock-result";
    }

}
