package org.dav.equitylookup.service.impl.crypto;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.helper.FinancialAnalysis;
import org.dav.equitylookup.model.Coin;
import org.dav.equitylookup.model.Portfolio;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.model.cache.CoinInfo;
import org.dav.equitylookup.model.dto.CoinDTO;
import org.dav.equitylookup.service.CryptoApiService;
import org.dav.equitylookup.service.CryptoService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService{

    private final CryptoApiService cachedCryptoApiService;
    private final ModelMapper modelMapper;

    @Override
    public CoinInfo getCoinInfo(String symbol) {
        return cachedCryptoApiService.getCoinInfo(symbol);
    }

    @Override
    public String getCoinPrice(String symbol) {
        return getCoinInfo(symbol).getCurrentPrice();
    }

    @Override
    public Coin obtainCoin(String symbol, User user) {
        CoinInfo coinInfo = cachedCryptoApiService.getCoinInfo(symbol);
        return new Coin(coinInfo.getCurrentPrice(), coinInfo, user);
    }

    @Override
    public List<CoinDTO> getCoinDTO(Portfolio portfolio) {
        List<CoinDTO> coinDTOS = modelMapper.map(portfolio.getCoins(), new TypeToken<List<CoinDTO>>() {
        }.getType());
        addAnalysisDetails(coinDTOS);
        return coinDTOS;
    }

    public void addAnalysisDetails(List<CoinDTO> object) {
        for (CoinDTO coinDTO : object) {
            String currentPrice = cachedCryptoApiService.getCoinInfo(coinDTO.getSymbol()).getCurrentPrice();
            coinDTO.setCurrentPrice(currentPrice);
            coinDTO.setValueChange(FinancialAnalysis.getValueChange(coinDTO.getBoughtPrice(), currentPrice));
            coinDTO.setPercentageChange(FinancialAnalysis.getPercentageChange(coinDTO.getBoughtPrice(), currentPrice));
        }
    }
}
