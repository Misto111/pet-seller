package com.project.petSeller.service;

import com.project.petSeller.model.dto.ConvertRequestDTO;
import com.project.petSeller.model.dto.ExchangeRatesDTO;
import com.project.petSeller.model.dto.MoneyDTO;

public interface CurrencyService {

    void refreshRates(ExchangeRatesDTO exchangeRatesDTO);

    MoneyDTO convert(ConvertRequestDTO convertRequestDTO);
}
