package net.myacxy.retrotwitch.utils;

import java.util.List;

public class StringUtil
{
    public static String joinStrings(List<String> strings, String separator)
    {
        StringBuilder separatedStrings = new StringBuilder();
        boolean firstEntry = true;
        for (String string : strings)
        {
            if(firstEntry) firstEntry = false;
            else separatedStrings.append(separator);
            separatedStrings.append(string);
        }
        return separatedStrings.toString();
    }

    public static boolean isBlank(String string) {
        return string == null || string.equals("");
    }
}
