package com.univ.bot.extractable_entities;

import java.util.Arrays;
import java.util.List;

public class Subject {

    private static List<String> eventRegexs = Arrays.asList("event", "meeting");
    private static List<String> reminderRegexs = Arrays.asList("reminder");

    public static Subject.Type getSubject(String msg) {

        List<String> eventMatches = Entity.extractValidEntities(eventRegexs, msg);
        if (eventMatches.size() == 1) {
            return Subject.Type.EVENT;
        }
        List<String> reminderMatches = Entity.extractValidEntities(reminderRegexs, msg);
        if (reminderMatches.size() == 1) {
            return Subject.Type.REMINDER;
        }

        return Subject.Type.UNKNOWN;
    }

    public static String removeMatchedWords(String input) {
        input = Entity.removeRegexFromString(eventRegexs, input);
        return Entity.removeRegexFromString(reminderRegexs, input);
    }

    public enum Type {EVENT, REMINDER, UNKNOWN}
}
