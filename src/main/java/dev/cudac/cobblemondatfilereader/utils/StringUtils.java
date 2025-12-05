package dev.cudac.cobblemondatfilereader.utils;

import java.text.DecimalFormat;

public class StringUtils {

    private static final DecimalFormat format = new DecimalFormat("#.##");

    public static String formatDouble(double value) {
        return format.format(value);
    }

    public static String capitalizeString(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
    }

}
