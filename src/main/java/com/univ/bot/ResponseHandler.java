package com.univ.bot;

import com.univ.bot.data.Event;
import com.univ.bot.data.Reminder;
import com.univ.bot.data.TimeInterval;
import com.univ.bot.extractable_entities.Date;
import com.univ.bot.extractable_entities.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResponseHandler {

    private final String helpMsg =
            "You can manage your events and reminders, keywords: \"event\", \"reminder\""
                    + "\n\n Supported intent: "
                    + "\n Create: \"create\", \"set\", \"make\""
                    + "\n Delete: \"delete\", \"remove\""
                    + "\n Find: \"find\", \"search\", \"list\""

                    + "\n\n Examples of usage: "
                    + "\n \"Create event Buy milk 02.06 from 14:00 to 15:00\""
                    + "\n \"List all events\""
                    + "\n \"Search for event \"go to mall\"\""
                    + "\n \"Set reminder for \"buy some milk\" 5 minutes before\""
                    + "\n \"Find events 02.06\"";

    private final TelegramController telegramController;

    private Map<Long, List<Event>> events = new HashMap<>();

    public ResponseHandler(TelegramController telegramController) {
        this.telegramController = telegramController;
    }

    public Map<Long, List<Event>> getEvents() {
        return events;
    }

    String doActionFromMsg(String msg, long chatID) {

        if (msg.equals("/help")) {
            return helpMsg;
        }

        String inputMsg = msg.toLowerCase();
        Intent.Type intent = Intent.getIntent(inputMsg);
        Subject.Type subject = Subject.getSubject(inputMsg);

        switch (intent) {
            case CREATE:
                switch (subject) {
                    case REMINDER:
                        return createReminder(inputMsg, chatID);
                    case EVENT:
                    default:
                        return createEvent(inputMsg, chatID);
                }
            case DELETE:
                switch (subject) {
                    case EVENT:
                        return deleteEvent(inputMsg, chatID);
                    case REMINDER:
                        return deleteReminder(inputMsg, chatID);
                    default:
                        return "I don't understand what do you want to delete. Please, try another wording";
                }
            case FIND:
                Matcher m = Pattern.compile(" all ").matcher(inputMsg);
                if (m.find()) {
                    return findAllEvents(inputMsg, chatID);
                }
                m = Pattern.compile(" nearest ").matcher(inputMsg);
                if (m.find()) {
                    return findNearestEvent(chatID);
                }
                LocalDate date = Date.getDate(inputMsg);
                if (date != null) {
                    return findEventsDate(date, chatID);
                }
                switch (subject) {
                    case EVENT:
                        return findEvent(inputMsg, chatID);
                    default:
                        return "I don't understand what do you want to find. Please, try another wording";
                }
            default:
                return "Sorry, I don't understand the intent";
        }
    }

    private String createEvent(String inputMsg, long chatID) {
        LocalDate date = Date.getDate(inputMsg);
        if (date == null) {
            return "I don't understand at which date do you want to create, please, try another wording";
        } else {
            TimeInterval interval = Interval.getTimeInterval(inputMsg);
            if (interval == null) {
                return "I don't understand time interval for your event, please, try another wording";
            } else {
                inputMsg = Intent.removeMatchedWords(inputMsg);
                inputMsg = Subject.removeMatchedWords(inputMsg);
                inputMsg = Date.removeMatchedWords(inputMsg);
                inputMsg = Interval.removeMatchedWords(inputMsg);
                inputMsg = inputMsg.replaceAll("  ", " ");
                LocalDateTime start = LocalDateTime.of(date, interval.getStart());
                LocalDateTime end = LocalDateTime.of(date, interval.getEnd());
                Event event = new Event(inputMsg.trim(), start, end, chatID, new ArrayList<>());
                if (!events.containsKey(chatID)) {
                    events.put(chatID, new ArrayList<>());
                }
                events.get(chatID).add(event);
                return "Event \"" + inputMsg.trim() + "\" successfully created";
            }
        }
    }

    private String deleteEvent(String inputMsg, long chatID) {
        String title = Title.getTitle(inputMsg);
        if (title == null) {
            return "Please, enter title of event to delete in quotes";
        } else {
            String titleWithoutQuotes = title.substring(1, title.length() - 1);
            if (events.containsKey(chatID)) {
                Optional<Event> optionalEvent = eventByTitle(titleWithoutQuotes, chatID);
                if (optionalEvent.isPresent()) {
                    events.get(chatID).remove(optionalEvent.get());
                    return "Event " + title + " was removed successfully";
                } else {
                    return "There is no event with such title";
                }
            } else {
                return "You didn't create any events yet";
            }
        }
    }

    private String findEvent(String inputMsg, long chatID) {
        String title = Title.getTitle(inputMsg);
        if (title == null) {
            return "Please, enter title of event to find in quotes";
        } else {
            String titleWithoutQuotes = title.substring(1, title.length() - 1);
            if (events.containsKey(chatID)) {
                Optional<Event> optionalEvent = eventByTitle(titleWithoutQuotes, chatID);
                if (optionalEvent.isPresent()) {
                    Event foundEvent = optionalEvent.get();
                    return "Here is details about your event: "
                            + eventString(foundEvent);
                } else {
                    return "There is no event with such title";
                }
            } else {
                return "You didn't create any events yet";
            }
        }
    }

    private String findAllEvents(String inputMsg, long chatID) {
        StringBuilder output = new StringBuilder("Here is your events:");
        if (events.containsKey(chatID)) {
            for (Event e : events.get(chatID)) {
                output.append(eventString(e));
                output.append("\n\n");
            }
            return output.toString();
        } else {
            return "You didn't create any events yet";
        }
    }

    private String createReminder(String inputMsg, long chatID) {
        String title = Title.getTitle(inputMsg);
        if (title == null) {
            return "Please, enter title of event you want to add reminder to in quotes";
        } else {
            String titleWithoutQuotes = title.substring(1, title.length() - 1);
            if (events.containsKey(chatID)) {
                Optional<Event> optionalEvent = eventByTitle(titleWithoutQuotes, chatID);
                if (optionalEvent.isPresent()) {
                    Event foundEvent = optionalEvent.get();
                    String minutesRegex = "([1-9]\\d*)";
                    final Matcher m = Pattern.compile(minutesRegex).matcher(inputMsg);
                    if (m.find()) {
                        foundEvent.getReminders().add(new Reminder(foundEvent, Integer.parseInt(m.group(0))));
                        return "Reminder to event " + foundEvent.getTitle() + " created successfully";
                    } else {
                        return "Please, provide number of minutes before event";
                    }
                } else {
                    return "There is no event with such title";
                }
            } else {
                return "You didn't create any events yet";
            }
        }
    }

    private String deleteReminder(String inputMsg, long chatID) {
        String title = Title.getTitle(inputMsg);
        if (title == null) {
            return "Please, enter title of event to from which you want to delete reminder in quotes";
        } else {
            String titleWithoutQuotes = title.substring(1, title.length() - 1);
            if (events.containsKey(chatID)) {
                Optional<Event> optionalEvent = eventByTitle(titleWithoutQuotes, chatID);
                if (optionalEvent.isPresent()) {
                    optionalEvent.get().getReminders().clear();
                    return "Reminders from event " + title + " was removed successfully";
                } else {
                    return "There is no event with such title";
                }
            } else {
                return "You didn't create any events yet";
            }
        }
    }

    private String findEventsDate(LocalDate date, long chatID) {
        StringBuilder output = new StringBuilder();
        if (events.containsKey(chatID)) {
            for (Event e : events.get(chatID)) {
                if (e.getStartAt().toLocalDate().equals(date)) {
                    output.append(eventString(e));
                    output.append("\n\n");
                }
            }
            if (output.toString().equals("")) {
                return "You don't have events for this date";
            }
            return "Here is your events for " + date.toString() + " :" + output;
        } else {
            return "You didn't create any events yet";
        }
    }

    private String findNearestEvent(long chatID) {
        if (events.containsKey(chatID)) {
            if (!events.get(chatID).isEmpty()) {
                Event minDateEvent = events.get(chatID).get(0);
                for (Event e : events.get(chatID)) {
                    if (minDateEvent.getStartAt().isAfter(e.getStartAt())) {
                        minDateEvent = e;
                    }
                }
                return "Here is your nearest event: " + eventString(minDateEvent);
            } else {
                return "You don't have any events";
            }
        } else {
            return "You didn't create any events yet";
        }
    }

    private Optional<Event> eventByTitle(String title, long chatID) {
        return events.get(chatID)
                .stream()
                .filter(p -> p.getTitle()
                        .equals(title))
                .findFirst();
    }

    private String eventString(Event e) {
        return "\ntitle: " + e.getTitle()
                + "\nstart at: " + e.getStartAt().toString()
                + "\nfinish at: " + e.getFinishAt().toString();
    }
}
