package com.khome.service.imp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.khome.entity.Currency;
import com.khome.service.CurrencyConvertionService;

import org.json.JSONArray;
import org.json.JSONObject;

import lombok.SneakyThrows;

public class NBUCurrencyConvertionService  implements CurrencyConvertionService{

    @SneakyThrows
    @Override
    public double getCurrencyConversion(Currency originCurrency, Currency targetCurrency) {
       if (originCurrency == targetCurrency) {
           return 1;
       } else {
        System.out.println("URL https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json");
        URL url = new URL("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        System.out.println("Connect");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inStr;
        StringBuilder respStr = new StringBuilder();
        while ((inStr = in.readLine()) != null ) {
            respStr.append(inStr+"\n");
        }
        in.close();
        con.disconnect();

        JSONArray jsonArray  = new JSONArray(respStr.toString());
        JSONObject json = new JSONObject();
        String ccKey = "";
        Double originRate = 1.0;
        Double targetRate = 1.0;
        
        for (Object cur : jsonArray) {
            json = (JSONObject) cur;
            ccKey = json.getString("cc");
            if (ccKey.equals(originCurrency.toString())) {
                originRate = json.getDouble("rate");
                System.out.println("set originRate = " + originRate);
            }
            if (ccKey.equals(targetCurrency.toString())) {
                targetRate = json.getDouble("rate");
                System.out.println("set targetRate = " + targetRate);
            }
        }


        System.out.println("return ratio: " + originRate / targetRate);

        return originRate / targetRate;
       }
    }
    
}
