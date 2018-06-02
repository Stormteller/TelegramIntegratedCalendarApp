package com.univ.bot;

import com.univ.bot.data.Event;
import com.univ.bot.data.Reminder;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReminderThread implements Runnable {

    private final Map<Long, List<Event>> events;
    private final TelegramLongPollingBot bot;

    public ReminderThread(Map<Long, List<Event>> events, TelegramLongPollingBot bot) {
        this.events = events;
        this.bot = bot;
    }

    @Override
    public void run() {

        while (true) {
            for (Map.Entry<Long, List<Event>> entry : events.entrySet()) {
                for (Event event : entry.getValue()) {
                    for (Iterator<Reminder> iterator = event.getReminders().iterator(); iterator.hasNext(); ) {
                        Reminder reminder = iterator.next();
                        if (event.getStartAt().isBefore(LocalDateTime.now().plusMinutes(reminder.getMinutesBeforeEvent()))) {
                            SendMessage message = new SendMessage() // Create a message object object
                                    .setChatId(entry.getKey())
                                    .setText("Reminder: event " + event.getTitle() + " will start in "
                                            + reminder.getMinutesBeforeEvent() + " minutes");
                            try {
                                bot.execute(message); // Sending our message object to user
                                iterator.remove();
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
