package com.univ.bot.extractable_entities;

import com.univ.bot.DateValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class Date {

    private static List<String> dateRegexs = Arrays.asList(
            "(\\d\\d)\\.(\\d\\d)",
            "today", "tomorrow");

    public static LocalDate getDate(String msg) {
        List<String> dateMatches = Entity.extractValidEntities(dateRegexs, msg);
        if (dateMatches.size() == 1) {
            switch (dateMatches.get(0)) {
                case "today":
                    return LocalDate.now();
                case "tomorrow":
                    return LocalDate.now().plus(1, ChronoUnit.DAYS);
                default:
                    String date = dateMatches.get(0) + ".2018";
                    if (DateValidator.isDateValid(date, "dd.MM.yyyy")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

                        return LocalDate.parse(date, formatter);
                    } else {
                        return null;
                    }
            }
        }
        return null;
    }

    public static String removeMatchedWords(String input) {
        return Entity.removeRegexFromString(dateRegexs, input);
    }
}
