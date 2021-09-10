package org.dav.equitylookup.service.impl.crypto;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.Crypt;
import org.dav.equitylookup.helper.FinancialAnalysis;
import org.dav.equitylookup.model.CryptoShare;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.StockShare;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.cache.Crypto;
import org.dav.equitylookup.model.dto.CryptoShareDTO;
import org.dav.equitylookup.model.dto.GroupedCryptoSharesDTO;
import org.dav.equitylookup.model.dto.GroupedStockSharesDTO;
import org.dav.equitylookup.service.CryptoApiService;
import org.dav.equitylookup.service.CryptoService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService {

    private final CryptoApiService cachedCryptoApiService;
    private final ModelMapper modelMapper;

    @Override
    public Crypto getCoinInfo(String symbol) {
        return cachedCryptoApiService.getCrypto(symbol);
    }

    @Override
    public String getCoinPrice(String symbol) {
        return getCoinInfo(symbol).getCurrentPrice();
    }

    @Override
    public CryptoShare obtainCryptoShare(double fraction, String symbol, User user) {
        Crypto crypto = cachedCryptoApiService.getCrypto(symbol);
        return new CryptoShare(fraction, crypto, user);
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
            }
            for ( GroupedCryptoSharesDTO groupedCryptoSharesDTO : groupedCryptoSharesDTOS){
                if ( groupedCryptoSharesDTO.getSymbol().equals(cryptoShare.getSymbol())){
                    groupedCryptoSharesDTO.addToPurchasePrice(new BigDecimal(cryptoShare.getBoughtPrice()));
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
            String currentCryptoPrice = cachedCryptoApiService.getCrypto(groupedCryptoSharesDTO.getSymbol()).getCurrentPrice();
            BigDecimal holdings = BigDecimal.valueOf(Double.parseDouble(currentCryptoPrice) * groupedCryptoSharesDTO.getAmount());
            groupedCryptoSharesDTO.setCurrentPrice(new BigDecimal(currentCryptoPrice));
            groupedCryptoSharesDTO.setHoldings(holdings);
            groupedCryptoSharesDTO.setValueChange(FinancialAnalysis.getValueChange(groupedCryptoSharesDTO.getTotalPurchasePrice(), holdings));
            groupedCryptoSharesDTO.setPercentageChange(FinancialAnalysis.getPercentageChange(groupedCryptoSharesDTO.getTotalPurchasePrice(), holdings));
        }
    }
}
