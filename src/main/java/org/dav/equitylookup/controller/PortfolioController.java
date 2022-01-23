package org.dav.equitylookup.controller;

import com.binance.api.client.exception.BinanceApiException;
import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.exceptions.CryptoNotFoundException;
import org.dav.equitylookup.exceptions.PortfolioNotFoundException;
import org.dav.equitylookup.exceptions.StockNotFoundException;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.TransactionRecord;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.dto.CryptoDTO;
import org.dav.equitylookup.model.dto.PortfolioDTO;
import org.dav.equitylookup.model.dto.StockDTO;
import org.dav.equitylookup.model.dto.TransactionRecordDTO;
import org.dav.equitylookup.model.enums.Operation;
import org.dav.equitylookup.model.form.CryptoForm;
import org.dav.equitylookup.model.form.StockForm;
import org.dav.equitylookup.service.CryptoService;
import org.dav.equitylookup.service.PortfolioService;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@SessionAttributes({"stockForm", "cryptoForm"})
@RequiredArgsConstructor
@Controller
public class PortfolioController {

    private final UserService userService;

    private final StockService stockService;

    private final CryptoService cryptoService;

    private final ModelMapper modelMapper;

    private final PortfolioService portfolioService;

    @ModelAttribute("stockForm")
    public StockForm getStockForm() {
        return new StockForm();
    }

    @ModelAttribute("cryptoForm")
    public CryptoForm getCryptoForm() {
        return new CryptoForm();
    }

    @GetMapping("/portfolio/show")
    public String listStocksForm(Model model, Principal loggedUser) throws IOException, StockNotFoundException, CryptoNotFoundException {
        User user = userService.getUserByUsername(loggedUser.getName());
        Portfolio portfolio = user.getPortfolio();

        List<StockDTO> stockDTOS = stockService.getAnalyzedStockDTOS(portfolio);
        List<StockDTO> soldOUTStockDTOS = stockService.getAndRemoveSoldOutStocks(stockDTOS);
        List<CryptoDTO> cryptoDTOS = cryptoService.getAnalyzedCryptoDTOS(portfolio);
        List<CryptoDTO> soldOutCryptoDTOS = cryptoService.getAndRemoveSoldOutCryptos(cryptoDTOS);
        PortfolioDTO portfolioDTO = portfolioService.obtainAnalyzedDTO(portfolio);

        model.addAttribute("portfolio", portfolioDTO);
        model.addAttribute("stocks", stockDTOS);
        model.addAttribute("soldOutStocks", soldOUTStockDTOS.isEmpty() ? null : soldOUTStockDTOS);
        model.addAttribute("soldOutCrypto", soldOutCryptoDTOS.isEmpty() ? null : soldOutCryptoDTOS);
        model.addAttribute("cryptos", cryptoDTOS);
        model.addAttribute("stockForm", new StockForm());
        model.addAttribute("cryptoForm", new CryptoForm());
        return "portfolio/show";
    }

    @PostMapping("/portfolio/stockshare/add")
    public String addStock(@Validated @ModelAttribute("stockForm") StockForm stockForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal loggedUser, SessionStatus sessionStatus) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.stockForm", bindingResult);
            return "redirect:/portfolio/show";
        }

        User user = userService.getUserByUsername(loggedUser.getName());
        String portfolio = user.getPortfolio().getName();
        TransactionRecord transactionRecord = new TransactionRecord.TransactionRecordBuilder()
                .timeOfPurchase(stockForm.getDate())
                .asset(stockForm.getTicker())
                .operation(Operation.BUY)
                .quantity(stockForm.getAmount())
                .value(stockForm.getPrice())
                .build();
        try {
            portfolioService.addStock(transactionRecord, portfolio);
        } catch (PortfolioNotFoundException e) {
            e.printStackTrace();
        } catch (StockNotFoundException snfe) {
            bindingResult.rejectValue("ticker", "error.stockForm", "Stock not found");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.stockForm", bindingResult);
            return "redirect:/portfolio/show";
        }
        sessionStatus.setComplete();
        return "redirect:/portfolio/show";
    }

    @PostMapping("/portfolio/cryptoshare/add")
    public String addCrypto(@Validated @ModelAttribute("cryptoForm") CryptoForm cryptoForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal loggedUser, SessionStatus sessionStatus) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.cryptoForm", bindingResult);
            return "redirect:/portfolio/show";
        }
        User user = userService.getUserByUsername(loggedUser.getName());
        String portfolio = user.getPortfolio().getName();

        TransactionRecord transactionRecord = new TransactionRecord.TransactionRecordBuilder()
                .timeOfPurchase(cryptoForm.getDate())
                .asset(cryptoForm.getSymbol())
                .operation(Operation.BUY)
                .quantity(cryptoForm.getAmount())
                .value(cryptoForm.getPrice())
                .build();
        try {
            portfolioService.addCrypto(transactionRecord, portfolio);
        } catch (PortfolioNotFoundException e) {
            e.printStackTrace();
        } catch (BinanceApiException | CryptoNotFoundException bae) {
            bindingResult.rejectValue("symbol", "error.cryptoForm", "Cryptocurrency not found");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.cryptoForm", bindingResult);
            return "redirect:/portfolio/show";
        }
        sessionStatus.setComplete();
        return "redirect:/portfolio/show";
    }

    @PostMapping("/portfolio/stockshare/details")
    public String showAllCompanyShares(@RequestParam String ticker, Model model, Principal loggedUser) {
        Portfolio portfolio = userService.getUserByUsername(loggedUser.getName()).getPortfolio();
        List<TransactionRecordDTO> recordDTOS = modelMapper.map(portfolio.getStockByTicker(ticker).getTransactionRecords(), new TypeToken<List<TransactionRecordDTO>>() {
        }.getType());
        model.addAttribute("asset", portfolio.getStockByTicker(ticker).getCompany());
        model.addAttribute("records", recordDTOS);
        return "transactions/asset-history";
    }

    @PostMapping("/portfolio/cryptoshare/details")
    public String showAllCryptoShares(@RequestParam String symbol, Model model, Principal loggedUser) {
        Portfolio portfolio = userService.getUserByUsername(loggedUser.getName()).getPortfolio();
        List<TransactionRecordDTO> recordDTOS = modelMapper.map(portfolio.getCryptoCurrencyBySymbol(symbol).getTransactionRecords(), new TypeToken<List<TransactionRecordDTO>>() {
        }.getType());
        model.addAttribute("asset", symbol);
        model.addAttribute("records", recordDTOS);
        return "transactions/asset-history";
    }

    @PostMapping("/portfolio/crypto/remove")
    public String removeCrypto(@Validated @ModelAttribute("cryptoForm") CryptoForm cryptoForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal loggedUser, SessionStatus sessionStatus) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.cryptoForm", bindingResult);
            return "redirect:/portfolio/show";
        }
        Portfolio portfolio = userService.getUserByUsername(loggedUser.getName()).getPortfolio();
        TransactionRecord transactionRecord = new TransactionRecord.TransactionRecordBuilder()
                .timeOfPurchase(cryptoForm.getDate())
                .asset(cryptoForm.getSymbol())
                .operation(Operation.SELL)
                .quantity(cryptoForm.getAmount())
                .value(cryptoForm.getPrice())
                .build();
        try {
            portfolioService.removeCrypto(transactionRecord, portfolio.getName());
        } catch (CryptoNotFoundException bae) {
            bindingResult.rejectValue("symbol", "error.cryptoForm", "Cryptocurrency not found");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.cryptoForm", bindingResult);
            return "redirect:/portfolio/show";
        } catch (PortfolioNotFoundException e) {
            e.printStackTrace();
        }
        sessionStatus.setComplete();
        return "redirect:/portfolio/show";
    }

    @PostMapping("/portfolio/stock/remove")
    public String removeStock(@Validated @ModelAttribute("stockForm") StockForm stockForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal loggedUser, SessionStatus sessionStatus) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.stockForm", bindingResult);
            return "redirect:/portfolio/show";
        }
        User user = userService.getUserByUsername(loggedUser.getName());
        String portfolio = user.getPortfolio().getName();
        TransactionRecord transactionRecord = new TransactionRecord.TransactionRecordBuilder()
                .timeOfPurchase(stockForm.getDate())
                .asset(stockForm.getTicker())
                .operation(Operation.SELL)
                .quantity(stockForm.getAmount())
                .value(stockForm.getPrice())
                .build();
        try {
            portfolioService.removeStock(transactionRecord, portfolio);
        } catch (PortfolioNotFoundException e) {
            e.printStackTrace();
        } catch (StockNotFoundException snfe) {
            bindingResult.rejectValue("ticker", "error.stockForm", "Stock not found");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.stockForm", bindingResult);
            return "redirect:/portfolio/show";
        }
        sessionStatus.setComplete();
        return "redirect:/portfolio/show";
    }
}
