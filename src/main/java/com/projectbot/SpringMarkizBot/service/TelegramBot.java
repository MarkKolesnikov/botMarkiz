package com.projectbot.SpringMarkizBot.service;

import com.projectbot.SpringMarkizBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

   final BotConfig botConfig;

   public TelegramBot(BotConfig botConfig) {
       this.botConfig = botConfig;
   }
    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

}
