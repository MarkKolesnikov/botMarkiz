package com.projectbot.SpringMarkizBot.service;

import com.projectbot.SpringMarkizBot.config.BotConfig;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
       if(update.hasMessage() && update.getMessage().hasText()) {
           String messageText = update.getMessage().getText();
           long chatId = update.getMessage().getChatId();

           switch (messageText) {
               case "/start":

                       startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                       break;
               default:

                       sendMessage(chatId, "Sorry :( ");
           }
       }
    }

    private void startCommandReceived(long chatId, String name) {

       String answer = "Hi, " + name + ", nice to meet you";

       sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        }
        catch (TelegramApiException e) {

        }
    }
}
