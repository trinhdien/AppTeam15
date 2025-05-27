package com.utc.asm_mob_java.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static final String DATE_PATTERN_1 = "dd/MM/yyyy HH:mm";

    public static String convertDateToStringPattern(Date date, String pattern) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }
}
