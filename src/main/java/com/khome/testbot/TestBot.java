package com.khome.testbot;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.swing.plaf.synth.SynthStyle;

import com.khome.entity.Currency;
import com.khome.service.CurrencyModeService;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import lombok.SneakyThrows;


public class TestBot extends TelegramLongPollingBot
{
 private CurrencyModeService currencyModeService = CurrencyModeService.getInstance();

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
       
        if(update.hasCallbackQuery()){
            handleCallback(update.getCallbackQuery());
        } else   if (update.hasMessage()) {
            handleMessage(update.getMessage());
            
            }
        }        
    @SneakyThrows
    private void handleCallback(CallbackQuery callbackQuery){
        System.out.println("Begin callback handle");
        Message message = callbackQuery.getMessage();
        String[] param = callbackQuery.getData().split(":");
        
        String action = param[0];
       
         Currency newCurrency = Currency.valueOf(param[1]);
        switch(action){
            case "ORIGIN":
            System.out.println(action +"->"+ newCurrency);
            currencyModeService.setOriginalCurrency(message.getChatId(), newCurrency);
            break;

            case "TARGET":
            System.out.println(action +"->"+ newCurrency);
            currencyModeService.setTargetCurrency(message.getChatId(), newCurrency);
            break;
        }

// buuton create

                    List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
                   Currency originCurrency = currencyModeService.getOriginalCurrency(message.getChatId());
                   Currency targetCurrency = currencyModeService.getTargetCurrency(message.getChatId());
                   
                   for (Currency currency : Currency.values()) {
                       buttons.add(
                        Arrays.asList(
                         InlineKeyboardButton.builder()
                            .text(getCurrencyButton(originCurrency, currency))
                            .callbackData("ORIGIN:" + currency)
                            .build(),
                         InlineKeyboardButton.builder()
                            .text(getCurrencyButton(targetCurrency, currency))
                            .callbackData("TARGET:" + currency)
                            .build()
                           )
                       );
                       System.out.println("buttons added " + currency);
                   };

// stop button create
        execute(EditMessageReplyMarkup.builder()
        .chatId(message.getChatId().toString())
        .messageId(message.getMessageId())
        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
        .build());
        System.out.println("keyboard buttons updated");
        
    }

    @SneakyThrows
    private void handleMessage(Message message){
        System.out.println("Begin message handle");
        if(message.hasText() && message.hasEntities()){
            Optional<MessageEntity> commandEntity =
            message.getEntities().stream().filter(e->"bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()) {
               String command = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
               
               switch (command){
                   case "/set_currency":
                   System.out.println("case '/set_currency' command");
                   
                   List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
                   Currency originCurrency = currencyModeService.getOriginalCurrency(message.getChatId());
                   Currency targetCurrency = currencyModeService.getTargetCurrency(message.getChatId());
                   
                   for (Currency currency : Currency.values()) {
                       buttons.add(
                        Arrays.asList(
                         InlineKeyboardButton.builder()
                            .text(getCurrencyButton(originCurrency, currency))
                            .callbackData("ORIGIN:" + currency)
                            .build(),
                         InlineKeyboardButton.builder()
                            .text(getCurrencyButton(targetCurrency, currency))
                            .callbackData("TARGET:" + currency)
                            .build()
                           )
                       );
                       System.out.println("buttons added " + currency);
                   };
                    execute(SendMessage.builder()
                    .chatId(message.getChatId().toString())
                    .text("Select ORIGIN and TARGET currency")
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                    .build());
                    System.out.println("message sended");
                   return;
               }
            }
        }
    }
    private String getCurrencyButton(Currency saved, Currency current) {
        return saved == current ? current + " ✅": current.name();
    }
    @Override
    public String getBotUsername() {
        return "KergmaHyper_bot";
    }
    
    interface Shifer {   char calc(int n1); }
    
    @Override
    public String getBotToken() {
<<<<<<< HEAD

       String tokStr =":8;1<:0;:<2IIMaAr{@KBPk:pXJC~~B;_aq}F%JRbc^:iA";
       int keyI = 8;

       Shifer xor = (a)-> (char) (a ^ keyI);
       
       String tempStr = "";

       for(int intCh: tokStr.getBytes() ){
        tempStr += xor.calc(intCh);
       }

       return tempStr;

      //  return "2039428324:AAEiIzsHCJXc2xPBKvvJ3WiyuN-BZjkV2aI";


=======
        String tokStr =":8;1<:0;:<2IIMaAr{@KBPk:pXJC~~B;_aq}F%JRbc^:iA";
        int keyI = 8;
        Shifer xor = a-> (char)(a ^ keyI);
        for(int intCh: tokStr.getBytes()){
            tokStr += xor.calc(intCh);
        }
        return tokStr.substring(tokStr.length() / 2);
>>>>>>> 7c68d86bca36c8787804061ce09c368db2992451
    }

   

    @SneakyThrows
    public static void main( String[] args ) 
    {
       TestBot bot = new TestBot();
       TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
       telegramBotsApi.registerBot(bot);
        System.out.println("Bot init: " + bot.getBaseUrl());
    }

}
