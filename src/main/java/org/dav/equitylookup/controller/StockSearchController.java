package org.dav.equitylookup.controller;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.service.StockService;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class StockSearchController {

    private final StockService stockService;
//
//    @GetMapping("/stock")
//    public String stockForm(Model model) {
//        model.addAttribute("stockForm", new StockForm());
//        return "stock/stock-query";
//    }
//
//    @PostMapping("/stock")
//    public String stockPrice(@ModelAttribute StockForm stockForm, Model model) throws IOException {
//        Stock stock = stockService.getStock(stockForm.getTicker());
//        model.addAttribute("stock", stock.getTicker());
//        model.addAttribute("stockPrice", stock.getCurrentPrice());
//        return "stock/stock-result";
//    }

}
