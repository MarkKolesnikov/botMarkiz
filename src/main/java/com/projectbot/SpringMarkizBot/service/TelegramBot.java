package com.projectbot.SpringMarkizBot.service;

import com.projectbot.SpringMarkizBot.config.BotConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
               case "/help":

                       helpCommandLine(chatId);
                       break;

               default:

                       sendMessage(chatId, "Я не понимаю твоей команды! ");
           }
       } else if (update.hasCallbackQuery()) {
           String callbackData = update.getCallbackQuery().getData();
           Long chatId = update.getCallbackQuery().getMessage().getChatId();

           if ("help".equals(callbackData)) {
               helpCommandLine(chatId);
           }
       }
    }

    private void helpCommandLine(Long chatId) {
        String helpText = "/start - начало работы\n/help - команды бота";


        sendMessage(chatId, helpText);
    }

    private void startCommandReceived(long chatId, String name) {

       String answer = "Hi, " + name + ", nice to meet you";
       log.info("Replied to user: " + name);

       sendMessage(chatId, answer);
    }

    private void answerCallbackQuery(String callbackQueryId) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackQueryId);
        try {
            execute(answerCallbackQuery);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Long chatId, String textToSend) {

        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(textToSend);

        List<InlineKeyboardButton> keyboardButtonRow1 = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Посмотреть команды");
        button1.setCallbackData("help");

        keyboardButtonRow1.add(button1);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(keyboardButtonRow1);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(inlineKeyboardMarkup);


        try {
            execute(message);
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Error occurred: " + e.getMessage());
        }
    }
}
