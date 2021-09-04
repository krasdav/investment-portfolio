package org.dav.equitylookup.controller;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.exceptions.PortfolioNotFoundException;
import org.dav.equitylookup.exceptions.ShareNotFoundException;
import org.dav.equitylookup.model.Coin;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.Share;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.dto.CoinDTO;
import org.dav.equitylookup.model.dto.PortfolioDTO;
import org.dav.equitylookup.model.dto.ShareDTO;
import org.dav.equitylookup.model.form.CoinForm;
import org.dav.equitylookup.model.form.ShareForm;
import org.dav.equitylookup.service.CryptoService;
import org.dav.equitylookup.service.PortfolioService;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class PortfolioController {

    private final UserService userService;

    private final StockService stockService;

    private final CryptoService cryptoService;

    private final ModelMapper modelMapper;

    private final PortfolioService portfolioService;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/portfolio/show")
    public String listStocksForm(Model model, Principal loggedUser) throws IOException {
        User user = userService.getUserByUsername(loggedUser.getName());
        Portfolio portfolio = user.getPortfolio();

        stockService.updateStockPrices(portfolio);
        List<ShareDTO> shareDTOS = modelMapper.map(portfolio.getShares(), new TypeToken<List<ShareDTO>>() {
        }.getType());
        stockService.addAnalysisDetails(shareDTOS);
        PortfolioDTO portfolioDTO = modelMapper.map(user.getPortfolio(), PortfolioDTO.class);
        portfolioService.addAnalysisDetails(portfolioDTO);

        List<CoinDTO> coinDTOS = cryptoService.getCoinDTO(portfolio);

        model.addAttribute("portfolio", portfolioDTO);
        model.addAttribute("shares", shareDTOS);
        model.addAttribute("coins", coinDTOS);
        model.addAttribute("share", new ShareForm());
        model.addAttribute("coin",new CoinForm());
        return "portfolio/show";
    }

    @GetMapping("/portfolio/add")
    public String addStock(Model model) {
        model.addAttribute("share", new ShareForm());
        model.addAttribute("coin",new CoinForm());
        return "portfolio/add";
    }

    @PostMapping("/portfolio/share/add")
    public String addStock(@ModelAttribute("share") ShareForm shareForm, Model model, Principal loggedUser) throws IOException {
        User user = userService.getUserByUsername(loggedUser.getName());
        String portfolio = user.getPortfolio().getName();
        Share share = stockService.obtainShare(shareForm.getTicker(), user);
        try {
            portfolioService.addShare(share, portfolio);
        } catch (PortfolioNotFoundException e) {
            e.printStackTrace();
        }
        model.addAttribute("shareAdded", "Share added: " + share.getTicker());
        return "redirect:/portfolio/show";
    }

    @PostMapping("/portfolio/crypto/add")
    public String addCrypto(@ModelAttribute("coin") CoinForm coinForm, Model model, Principal loggedUser) throws IOException {
        User user = userService.getUserByUsername(loggedUser.getName());
        String portfolio = user.getPortfolio().getName();
        Coin coin = cryptoService.obtainCoin(coinForm.getSymbol(), user);
        try {
            portfolioService.addCoin(coin,portfolio);
        } catch (PortfolioNotFoundException e) {
            e.printStackTrace();
        }
        model.addAttribute("coinAdded", "Coin added: " + coin.getSymbol());
        return "redirect:/portfolio/show";
    }

    @PostMapping("/portfolio/share/remove/")
    public String removeStockFromUser(@RequestParam long shareId, @RequestParam String portfolioName) throws ShareNotFoundException, PortfolioNotFoundException {
        portfolioService.removeShareById(shareId, portfolioName);
        return "redirect:/portfolio/show";
    }

    @PostMapping("/portfolio/crypto/remove/")
    public String removeCryptoFromUser(@RequestParam long cryptoId, @RequestParam String portfolioName) throws ShareNotFoundException, PortfolioNotFoundException {
        portfolioService.removeCoinById(cryptoId, portfolioName);
        return "redirect:/portfolio/show";
    }

}