package com.khome.testbot;



import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.khome.entity.Currency;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import lombok.SneakyThrows;


public class TestBot extends TelegramLongPollingBot
{
  
    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            handleMessage(update.getMessage());
            
            }
        }        
    
    @SneakyThrows
    private void handleMessage(Message message){
        if(message.hasText() && message.hasEntities()){
            Optional<MessageEntity> commandEntity =
            message.getEntities().stream().filter(e->"bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()) {
               String command = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
               
               switch (command){
                   case "/set_currency":
                   List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
                   for (Currency currency : Currency.values()) {
                       buttons.add(
                           Arrays.asList(
                               InlineKeyboardButton.builder().text("orig "+currency.name()).callbackData("ORIGIN " + currency).build(),
                               InlineKeyboardButton.builder().text("targ "+currency.name()).callbackData("TARGET " + currency).build()
                           )
                       );
                   };
                    execute(SendMessage.builder()
                    .chatId(message.getChatId().toString())
                    .text("Select ORIGIN and TARGET currency")
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                    .build());
                   return;
               }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "KergmaHyper_bot";
    }
    
    interface Shifer {   char calc(int n1); }
    
    @Override
    public String getBotToken() {
        String tokStr =":8;1<:0;:<2IIMaAr{@KBPk:pXJC~~B;_aq}F%JRbc^:iA";
        int keyI = 8;
        Shifer xor = a-> (char)(a ^ keyI);
        for(int intCh: tokStr.getBytes()){
            tokStr += xor.calc(intCh);
        }
        return tokStr.substring(tokStr.length() / 2);
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
