package com.khome.service.imp;

import java.util.HashMap;
import java.util.Map;

import com.khome.entity.Currency;
import com.khome.service.CurrencyModeService;

public class HashMapCurrencyModeService  implements CurrencyModeService {
    private final Map<Long, Currency> originCurrency = new HashMap<>();
    private final Map<Long, Currency> targetCurrency = new HashMap<>();

public HashMapCurrencyModeService(){
    System.out.println("HashMap created");
}

    @Override
    public Currency getOriginalCurrency(long chatId) {
        
        return originCurrency.getOrDefault(chatId, Currency.USD);
    }

    @Override
    public Currency getTargetCurrency(long chatId) {
       
        return targetCurrency.getOrDefault(chatId, Currency.UAH);
    }

    @Override
    public void setOriginalCurrency(long chatId, Currency currency) {
        originCurrency.put(chatId, currency);
        
    }

    @Override
    public void setTargetCurrency(long chatId, Currency currency) {
        targetCurrency.put(chatId, currency);
        
    }}
