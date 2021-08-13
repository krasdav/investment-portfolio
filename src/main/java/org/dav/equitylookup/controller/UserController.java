package org.dav.equitylookup.controller;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.dto.PortfolioDTO;
import org.dav.equitylookup.model.form.ShareForm;
import org.dav.equitylookup.service.ShareService;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.UserService;
import org.dav.equitylookup.service.implementation.StockSearchService;
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

    private final StockSearchService stockSearchService;

    private final ShareService shareService;

    @GetMapping("/user")
    public String welcomePage(Model model, Principal user) {
        model.addAttribute("username", user.getName());
        return "user/user-welcome";
    }

    @GetMapping("/user/stocks/list")
    public String listStocksForm(Model model, Principal loggedUser) throws IOException {
        User user = userService.getUserByUsername(loggedUser.getName());
        stockService.updateStockPrices(user.getPortfolio().getShares());
        user.getPortfolio().updatePortfolioValue();
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
        Share share = new Share(stockSearchService.findPrice(stockSearchService.findStock(stock.getTicker())),stock, user);
        shareService.saveShare(share);
        Portfolio portfolio = user.getPortfolio();

        portfolio.addShare(share);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("ticker", share.getTicker());
        return "user/user-stock-result";
    }

    @PostMapping("/user/stocks/remove/")
    public String removeStockFromUser(@RequestParam String id, Principal loggedUser){
        shareService.deleteShareById(Integer.parseInt(id));
        return "redirect:/user/stocks/list";
    }

}
