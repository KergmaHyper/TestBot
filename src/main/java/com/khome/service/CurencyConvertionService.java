package com.khome.service;

import com.khome.entity.Currency;
import com.khome.service.imp.NbrbCurrencyConvertionService;

public interface CurencyConvertionService {
    static CurencyConvertionService getInstance(){return new NbrbCurrencyConvertionService();}
    double getCurrencyConversion(Currency originCurrency, Currency targetCurrency);

}
