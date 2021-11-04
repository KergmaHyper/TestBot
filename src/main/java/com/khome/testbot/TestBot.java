package com.khome.testbot;



import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import lombok.SneakyThrows;


public class TestBot extends TelegramLongPollingBot
{
  
    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()){
                System.out.println(
                    message.getChat().getId() + " " +
                    message.getMessageId()+" "
                    );

                execute(
                    SendMessage.builder()
                    .chatId(message.getChatId().toString())
                    .text(
                        "you send: \n\n"+
                        "\"" + 
                        message.getText() + "\n"+ 
                        "\""+
                        "Your name: "+message.getChat().getFirstName()
                        )
                    .replyToMessageId(message.getMessageId())
                    .build());
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
       Shifer xor = a-> (char) (a ^ keyI);
    //    String tempStr = "";

       for(int intCh: tokStr.getBytes() ){
        // tempStr += xor.calc(intCh);
        tokStr += xor.calc(intCh);
       }
    //    System.out.println(tokStr + "\n" + tokStr.substring( tokStr.length() / 2) + "\n");
       return tokStr.substring( tokStr.length() / 2);
    //    return tempStr;

      

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
