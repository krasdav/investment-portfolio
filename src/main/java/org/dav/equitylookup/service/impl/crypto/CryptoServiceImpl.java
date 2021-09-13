package org.dav.equitylookup.service.impl.crypto;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.helper.FinancialAnalysis;
import org.dav.equitylookup.model.CryptoShare;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.cache.Crypto;
import org.dav.equitylookup.model.dto.GroupedCryptoSharesDTO;
import org.dav.equitylookup.service.CryptoApiService;
import org.dav.equitylookup.service.CryptoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService {

    private final CryptoApiService cachedCryptoApiService;

    @Override
    public Crypto getCoinInfo(String symbol) {
        return cachedCryptoApiService.getCrypto(symbol);
    }

    @Override
    public BigDecimal getCoinPrice(String symbol) {
        return getCoinInfo(symbol).getCurrentPrice();
    }

    @Override
    public CryptoShare obtainCryptoShare(double fraction, String symbol, BigDecimal price, User user) {
        Crypto crypto = cachedCryptoApiService.getCrypto(symbol);
        return new CryptoShare(fraction, price, crypto, user);
    }

    @Override
    public List<GroupedCryptoSharesDTO> obtainGroupedAnalyzedDTO(Portfolio portfolio) {
        List<GroupedCryptoSharesDTO> groupedCryptoSharesDTOS = new ArrayList<>();
        List<String> coinsGrouped = new ArrayList<>();

        //iterate over all shares and group them in the list by ticker
        for( CryptoShare cryptoShare : portfolio.getCryptoShares()){
            if( !coinsGrouped.contains(cryptoShare.getSymbol())){
                groupedCryptoSharesDTOS.add(new GroupedCryptoSharesDTO(cryptoShare));
                coinsGrouped.add(cryptoShare.getSymbol());
                continue;
            }
            for ( GroupedCryptoSharesDTO groupedCryptoSharesDTO : groupedCryptoSharesDTOS){
                if ( groupedCryptoSharesDTO.getSymbol().equals(cryptoShare.getSymbol())){
                    groupedCryptoSharesDTO.addToPurchasePrice(cryptoShare.getBoughtPrice());
                    groupedCryptoSharesDTO.addToAmount(cryptoShare.getFraction());
                }
            }

        }

        analyze(groupedCryptoSharesDTOS);
        return groupedCryptoSharesDTOS;
    }

    @Override
    public void analyze(List<GroupedCryptoSharesDTO> shares) {
        for (GroupedCryptoSharesDTO groupedCryptoSharesDTO : shares) {
            BigDecimal currentCryptoPrice = cachedCryptoApiService.getCrypto(groupedCryptoSharesDTO.getSymbol()).getCurrentPrice();
            BigDecimal holdings = currentCryptoPrice.multiply(BigDecimal.valueOf(groupedCryptoSharesDTO.getAmount()));
            groupedCryptoSharesDTO.setCurrentPrice(currentCryptoPrice);
            groupedCryptoSharesDTO.setHoldings(holdings);
            groupedCryptoSharesDTO.setValueChange(FinancialAnalysis.getValueChange(groupedCryptoSharesDTO.getTotalPurchasePrice(), holdings));
            groupedCryptoSharesDTO.setPercentageChange(FinancialAnalysis.getPercentageChange(groupedCryptoSharesDTO.getTotalPurchasePrice(), holdings));
        }
    }
}
