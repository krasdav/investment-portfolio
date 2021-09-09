package org.dav.equitylookup.service.impl.crypto;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.Crypt;
import org.dav.equitylookup.helper.FinancialAnalysis;
import org.dav.equitylookup.model.CryptoShare;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.cache.Crypto;
import org.dav.equitylookup.model.dto.CryptoShareDTO;
import org.dav.equitylookup.service.CryptoApiService;
import org.dav.equitylookup.service.CryptoService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

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
    public List<CryptoShareDTO> groupAndAnalyze(Portfolio portfolio) {
        List<CryptoShareDTO> cryptoShareDTOS = modelMapper.map(portfolio.getCryptocurrencies(), new TypeToken<List<CryptoShareDTO>>() {
        }.getType());
        List<CryptoShareDTO> objectsToRemove = new ArrayList<>();

        for (int i = 0; i < cryptoShareDTOS.size(); i++) {
            for (int j = i + 1; j < cryptoShareDTOS.size(); j++) {
                if( cryptoShareDTOS.get(i).getSymbol().equals(cryptoShareDTOS.get(j).getSymbol())){
                    cryptoShareDTOS.get(i).addBoughtPrice(cryptoShareDTOS.get(j).getBoughtPrice());
                    cryptoShareDTOS.get(i).addAmount(cryptoShareDTOS.get(j).getAmount());
                    objectsToRemove.add(cryptoShareDTOS.get(j));
                }
            }
        }
        cryptoShareDTOS.removeAll(objectsToRemove);
        analyze(cryptoShareDTOS);
        return cryptoShareDTOS;
    }

    @Override
    public void analyze(List<CryptoShareDTO> shares) {
        for (CryptoShareDTO cryptoShareDTO : shares) {
            String currentCryptoPrice = cachedCryptoApiService.getCrypto(cryptoShareDTO.getSymbol()).getCurrentPrice();
            String holdings = String.valueOf(Double.parseDouble(currentCryptoPrice) * cryptoShareDTO.getAmount());
            cryptoShareDTO.setCurrentPrice(currentCryptoPrice);
            cryptoShareDTO.setHoldings(holdings);
            cryptoShareDTO.setValueChange(FinancialAnalysis.getValueChange(cryptoShareDTO.getBoughtPrice(), holdings));
            cryptoShareDTO.setPercentageChange(FinancialAnalysis.getPercentageChange(cryptoShareDTO.getBoughtPrice(), holdings));
        }
    }
}
