package com.univ.bot.extractable_entities;

import com.univ.bot.data.TimeInterval;

import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interval {

    private static String timeRegex = "(([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9])";
    private static String timeIntervalRegex = "from " + timeRegex + " to " + timeRegex;

    public static TimeInterval getTimeInterval(String input) {
        final Matcher m = Pattern.compile(timeIntervalRegex).matcher(input);
        if (m.find()) {
            LocalTime startTime = LocalTime.parse(m.group(1));
            LocalTime endTime = LocalTime.parse(m.group(3));
            if (startTime.isBefore(endTime)) {
                return new TimeInterval(LocalTime.parse(m.group(1)), LocalTime.parse(m.group(3)));
            }
            return null;
        } else return null;
    }

    public static String removeMatchedWords(String input) {
        return input.replaceAll(timeIntervalRegex, "");
    }
}
