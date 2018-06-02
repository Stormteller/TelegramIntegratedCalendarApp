package com.univ.bot.extractable_entities;

import java.util.Arrays;
import java.util.List;

public class Intent {

    private static List<String> createRegexs = Arrays.asList("create", "set", "make");
    private static List<String> deleteRegexs = Arrays.asList("delete", "remove");
    private static List<String> findRegexs = Arrays.asList("find", "search", "list");

    public static Type getIntent(String msg) {
        List<String> createMatches = Entity.extractValidEntities(createRegexs, msg);
        if (!createMatches.isEmpty()) {
            return Type.CREATE;
        }
        List<String> deleteMatches = Entity.extractValidEntities(deleteRegexs, msg);
        if (!deleteMatches.isEmpty()) {
            return Type.DELETE;
        }
        List<String> findMatches = Entity.extractValidEntities(findRegexs, msg);
        if (!findMatches.isEmpty()) {
            return Type.FIND;
        }
        return Type.UNKNOWN;
    }

    public static String removeMatchedWords(String input) {
        input = Entity.removeRegexFromString(createRegexs, input);
        input = Entity.removeRegexFromString(deleteRegexs, input);
        return Entity.removeRegexFromString(findRegexs, input);
    }

    public enum Type {CREATE, DELETE, FIND, UNKNOWN}
}
