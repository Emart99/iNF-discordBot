package com.github.ezeakel.infBot.Utils;

import java.util.ArrayList;
import java.util.List;

public class SplitStringEveryNthChar {
    public static List<String> splitMethod(String text, int n ){
        List<String> results = new ArrayList<>();
        int length = text.length();

        for (int i = 0; i < length; i += n) {
            results.add(text.substring(i, Math.min(length, i + n)));
        }

        return results;
    }
}
