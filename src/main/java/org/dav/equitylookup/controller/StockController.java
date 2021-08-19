package org.dav.equitylookup.controller;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.form.StockForm;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.impl.YahooApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class StockController {

    private final YahooApiService yahooApiService;

    private final StockService stockService;

    @GetMapping("/stock")
    public String stockForm(Model model) {
        model.addAttribute("stockForm",new StockForm());
        return "stock/stock-query";
    }

    @PostMapping("/stock")
    public String stockPrice(@ModelAttribute StockForm stockForm, Model model) throws IOException {
        Stock stock = stockService.getStock(stockForm.getTicker());
        model.addAttribute("stock",stock.getTicker());
        model.addAttribute("stockPrice", stock.getCurrentPrice());
        return "stock/stock-result";
    }

}
