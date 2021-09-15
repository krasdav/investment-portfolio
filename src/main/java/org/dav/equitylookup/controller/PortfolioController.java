package org.dav.equitylookup.controller;

import com.binance.api.client.exception.BinanceApiException;
import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.exceptions.PortfolioNotFoundException;
import org.dav.equitylookup.exceptions.ShareNotFoundException;
import org.dav.equitylookup.model.CryptoShare;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.StockShare;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.dto.*;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        List<GroupedStockSharesDTO> groupedStockSharesDTOS = stockService.obtainGroupedAnalyzedDTO(portfolio);
        List<GroupedCryptoSharesDTO> groupedCryptoShareDTOS = cryptoService.obtainGroupedAnalyzedDTO(portfolio);
        PortfolioDTO portfolioDTO = portfolioService.obtainAnalyzedDTO(portfolio);

        model.addAttribute("portfolio", portfolioDTO);
        model.addAttribute("stockShares", groupedStockSharesDTOS);
        model.addAttribute("cryptoShares", groupedCryptoShareDTOS);
        model.addAttribute("stockShare", new ShareForm());
        model.addAttribute("cryptoShare", new CoinForm());
        return "portfolio/show";
    }

    @PostMapping("/portfolio/stockshare/add")
    public String addStock(@ModelAttribute("stockShare") ShareForm shareForm, Model model, Principal loggedUser) throws IOException {
        User user = userService.getUserByUsername(loggedUser.getName());
        String portfolio = user.getPortfolio().getName();
        for (int i = 0; i < shareForm.getAmount(); i++) {
            StockShare stockShare = stockService.obtainShare(shareForm.getTicker(), shareForm.getPrice(), user);
            try {
                portfolioService.addShare(stockShare, portfolio);
            } catch (PortfolioNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/portfolio/show";
    }

    @PostMapping("/portfolio/cryptoshare/add")
    public String addCrypto(@ModelAttribute("cryptoShare") CoinForm coinForm, RedirectAttributes redirectAttributes, Principal loggedUser) {
        User user = userService.getUserByUsername(loggedUser.getName());
        String portfolio = user.getPortfolio().getName();
        if ( coinForm.getAmount() <= 0.0){
            redirectAttributes.addFlashAttribute("InvalidAmount", "Invalid amount: " + coinForm.getAmount());
            return "redirect:/portfolio/show";
        }
        try {
            CryptoShare cryptoShare = cryptoService.obtainCryptoShare(coinForm.getAmount(), coinForm.getSymbol(), coinForm.getPrice(), user);
            portfolioService.addCryptoShare(cryptoShare, portfolio);
        } catch (BinanceApiException bae) {
            redirectAttributes.addFlashAttribute("NotFoundError", "Cryptocurrency not found");
            return "redirect:/portfolio/show";
        } catch (PortfolioNotFoundException e) {
            e.printStackTrace();
        }

        return "redirect:/portfolio/show";
    }

    @PostMapping("/portfolio/stockshare/details")
    public String showAllCompanyShares(@RequestParam String ticker, Model model, Principal loggedUser) throws IOException {
        Portfolio portfolio = userService.getUserByUsername(loggedUser.getName()).getPortfolio();
        List<StockShare> companyStockShares = portfolio.getStockSharesByCompany(ticker);
        model.addAttribute("stockShares", modelMapper.map(companyStockShares, new TypeToken<List<StockShareDTO>>() {
        }.getType()));
        model.addAttribute("company", companyStockShares.get(0).getCompany());
        model.addAttribute("currentPrice", stockService.getStock(ticker).getCurrentPrice());
        return "shares/company-shares";
    }

    @PostMapping("/portfolio/cryptoshare/details")
    public String showAllCryptoShares(@RequestParam String symbol, Model model, Principal loggedUser) throws IOException {
        Portfolio portfolio = userService.getUserByUsername(loggedUser.getName()).getPortfolio();
        List<CryptoShare> cryptoShares = portfolio.getCryptoSharesBySymbol(symbol);
        model.addAttribute("cryptoShares", modelMapper.map(cryptoShares, new TypeToken<List<CryptoShareDTO>>() {
        }.getType()));
        model.addAttribute("company", cryptoShares.get(0).getSymbol());
        model.addAttribute("currentPrice", cryptoService.getCoinPrice(symbol));
        return "shares/crypto-shares";
    }

    @PostMapping("/portfolio/share/remove/")
    public String removeStockFromUser(@RequestParam long shareId, @RequestParam String portfolioName) throws ShareNotFoundException, PortfolioNotFoundException {
        portfolioService.removeShareById(shareId, portfolioName);
        return "redirect:/portfolio/show";
    }

    @PostMapping("/portfolio/crypto/remove/")
    public String removeCryptoFromUser(@RequestParam long cryptoId, @RequestParam String portfolioName) throws ShareNotFoundException, PortfolioNotFoundException {
        portfolioService.removeCryptoShareById(cryptoId, portfolioName);
        return "redirect:/portfolio/show";
    }

}
