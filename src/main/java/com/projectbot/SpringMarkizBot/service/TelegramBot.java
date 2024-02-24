package com.projectbot.SpringMarkizBot.service;

import com.projectbot.SpringMarkizBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

   final BotConfig config;

   public TelegramBot(BotConfig botConfig) {
       this.config = botConfig;
   }
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
       if(update.hasMessage() && update.getMessage().hasText()) {
           String messageText = update.getMessage().getText();

           switch (messageText) {
               case "/start":

           }
       }
    }
}
