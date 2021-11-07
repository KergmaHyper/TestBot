package com.khome.service;

import com.khome.entity.Currency;
import com.khome.service.imp.NBUCurrencyConvertionService;
import com.khome.service.imp.PRIVATCurrencyConversionService;

public interface CurrencyConvertionService {
    static CurrencyConvertionService getInstance(){return new NBUCurrencyConvertionService();};

    static CurrencyConvertionService getInstance(String bank){ 
        switch (bank){
            case "NBU": {
                return new NBUCurrencyConvertionService();
            }
            case "PRIVAT":{
                return new PRIVATCurrencyConversionService();
            }

        }
        return null;
    };
    double getCurrencyConversion(Currency originCurrency, Currency targetCurrency);

}
