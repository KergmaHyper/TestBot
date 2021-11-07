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

public class PRIVATCurrencyConversionService  implements CurrencyConvertionService{

    @SneakyThrows
    @Override
    public double getCurrencyConversion(Currency originCurrency, Currency targetCurrency) {
        if (originCurrency == targetCurrency){ 
            return 1;
        } else {

            System.out.println("URL https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5");
            URL url = new URL("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5");
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
            System.out.println(respStr.toString());

            JSONArray jsonArray  = new JSONArray(respStr.toString());
            JSONObject json = new JSONObject();
            String ccyKey = "";
            final String nameKey = "ccy";
            Double originRate = 1.0;
            Double targetRate = 1.0;
            
            for (Object cur : jsonArray) {
                json = (JSONObject) cur;
                ccyKey = json.getString(nameKey);

                if (ccyKey.equals(originCurrency.toString())) {
                    originRate = json.getDouble("buy");
                    System.out.println("set originRate = " + originRate);
                }
                if (ccyKey.equals(targetCurrency.toString())) {
                    targetRate = json.getDouble("sale");
                    System.out.println("set targetRate = " + targetRate);
                }
            }
    
    
            System.out.println("return ratio: " + originRate / targetRate);
    
            return originRate / targetRate;
        }

        
    }
    
}
