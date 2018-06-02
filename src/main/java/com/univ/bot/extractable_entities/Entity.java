package com.univ.bot.extractable_entities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Entity {

    public static List<String> extractValidEntities(List<String> validRegexs, String input) {
        List<String> validEntities = new ArrayList<>();
        for (String regex : validRegexs) {
            final Matcher m = Pattern.compile(regex).matcher(input);
            while (m.find()) {
                validEntities.add(m.group(0));
            }
        }
        return validEntities;
    }

    public static String removeRegexFromString(List<String> validRegexs, String input) {
        String res = input;
        for (String regex : validRegexs) {
            res = res.replaceAll(regex, "");
        }
        return res;
    }
}