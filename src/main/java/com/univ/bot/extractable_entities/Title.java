package com.univ.bot.extractable_entities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Title {

    private static String titleRegex = "(\"[^\"]*\")";

    public static String getTitle(String input) {
        final Matcher m = Pattern.compile(titleRegex).matcher(input);
        if (m.find()) {
            return m.group(0);
        } else return null;
    }

    public static String removeMatchedWords(String input) {
        return input.replaceAll(titleRegex, "");
    }

}
