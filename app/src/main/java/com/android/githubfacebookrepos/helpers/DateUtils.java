package com.android.githubfacebookrepos.helpers;

/*
 * Created by Arafin Mahtab on 6/21/20.
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateUtils {

    public static final String DATE_FORMAT = "MMM dd, ''yy 'at' HH:mm:ss";

    public static String getDateStringByFormat(long dateFormatted, String format) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
            return simpleDateFormat.format(new Date(dateFormatted));
        } catch (Exception e) {
            return "";
        }
    }
}
