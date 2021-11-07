package com.khome.service;

import com.khome.entity.Currency;
import com.khome.service.imp.NBUCurrencyConvertionService;

public interface CurrencyConvertionService {
    static CurrencyConvertionService getInstance(){return new NBUCurrencyConvertionService();}
    double getCurrencyConversion(Currency originCurrency, Currency targetCurrency);

}
