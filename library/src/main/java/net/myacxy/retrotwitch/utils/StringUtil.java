package net.myacxy.retrotwitch.utils;

import java.util.List;

public class StringUtil {

    private StringUtil() {
        throw new IllegalAccessError();
    }

    public static String joinStrings(List<String> strings, String separator) {
        StringBuilder separatedStrings = new StringBuilder();

        for (int i = 0; i < strings.size(); i++) {
            if (i > 0) {
                separatedStrings.append(separator);
            }
            separatedStrings.append(strings.get(i));
        }
        return separatedStrings.toString();
    }

    public static boolean isEmpty(String string) {
        return string == null || string.equals("");
    }
}
