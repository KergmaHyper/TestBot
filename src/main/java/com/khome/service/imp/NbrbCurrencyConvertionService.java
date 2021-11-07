package com.khome.service.imp;

import com.khome.entity.Currency;
import com.khome.service.CurencyConvertionService;

public class NbrbCurrencyConvertionService  implements CurencyConvertionService{

    @Override
    public double getCurrencyConversion(Currency originCurrency, Currency targetCurrency) {
       if (originCurrency == targetCurrency) {
           return 1;
       } else {

        return 0;
       }
    }
    
}
