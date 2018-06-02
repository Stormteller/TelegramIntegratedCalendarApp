package com.univ.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TelegramBot extends TelegramLongPollingBot {


    private static TelegramController telegramController;
    private static ResponseHandler responseHandler;

    public static void init(@NotNull TelegramController handler) {

        telegramController = handler;
        responseHandler = new ResponseHandler(telegramController);

        // Initialize Api Context
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try {
            TelegramLongPollingBot bot = new TelegramBot();
            botsApi.registerBot(bot);
            ReminderThread reminderThread = new ReminderThread(responseHandler.getEvents(), bot);
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(reminderThread);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            String response = responseHandler.doActionFromMsg(message_text, chat_id);

            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(response);
            try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    public String getBotUsername() {
        // Return bot username
        // If bot username is @TelegramBot, it must return 'TelegramBot'
        return "IAI_calendar_bot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "123456789:not_a_real_token";
    }
}
