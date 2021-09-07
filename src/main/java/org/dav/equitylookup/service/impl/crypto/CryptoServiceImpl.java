package org.dav.equitylookup.service.impl.crypto;

import lombok.RequiredArgsConstructor;
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

import java.util.List;

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
    public List<CryptoShareDTO> getCoinDTO(Portfolio portfolio) {
        List<CryptoShareDTO> cryptoShareDTOS = modelMapper.map(portfolio.getCryptocurrencies(), new TypeToken<List<CryptoShareDTO>>() {
        }.getType());
        addAnalysisDetails(cryptoShareDTOS);
        return cryptoShareDTOS;
    }

    public void addAnalysisDetails(List<CryptoShareDTO> object) {
        for (CryptoShareDTO cryptoShareDTO : object) {
            String currentCryptoPrice = cachedCryptoApiService.getCrypto(cryptoShareDTO.getSymbol()).getCurrentPrice();
            String currentPriceWithAmount = String.valueOf(Double.parseDouble(currentCryptoPrice) * cryptoShareDTO.getAmount());
            cryptoShareDTO.setCurrentPrice(currentPriceWithAmount);
            cryptoShareDTO.setValueChange(FinancialAnalysis.getValueChange(cryptoShareDTO.getBoughtPrice(), currentPriceWithAmount));
            cryptoShareDTO.setPercentageChange(FinancialAnalysis.getPercentageChange(cryptoShareDTO.getBoughtPrice(), currentPriceWithAmount));
        }
    }
}
