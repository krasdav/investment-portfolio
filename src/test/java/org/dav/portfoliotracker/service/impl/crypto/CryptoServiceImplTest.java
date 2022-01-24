package org.dav.portfoliotracker.service.impl.crypto;

import org.dav.portfoliotracker.service.CryptoApiService;
import org.dav.portfoliotracker.service.CryptoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class CryptoServiceImplTest {

    @Mock
    private CryptoApiService cachedCryptoApiService;

    private final ModelMapper modelMapper = new ModelMapper();

    private CryptoService cryptoService;

    @BeforeEach
    void setup(){
        cryptoService = new CryptoServiceImpl(cachedCryptoApiService, modelMapper);
    }

    @Test
    void getAnalyzedCryptoDTOS() {

    }

    @Test
    void setDynamicData() {

    }

    @Test
    void getAndRemoveSoldOutCryptos() {
    }
}