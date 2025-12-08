package com.example.cs360project.utils;

import java.util.Locale;

public class DateUtils {

    // Returns the desired year-month-day string in the correct format
    public static String getFormattedDate(int year, int month, int day) {
        return year + "-" +
                String.format(Locale.US, "%02d", month + 1) + "-" +
                String.format(Locale.US, "%02d", day);
    }

}
