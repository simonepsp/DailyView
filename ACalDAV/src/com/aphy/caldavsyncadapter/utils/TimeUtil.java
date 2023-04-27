package com.aphy.caldavsyncadapter.utils;

import android.app.Activity;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ch.punkt.mp02.dailyview.R;

public class TimeUtil {

    private static final String TAG = "TimeUtil";

    public static int getYear(long time) {
        Calendar cd = Calendar.getInstance();
        Date calendarDate = new Date(time);
        cd.setTime(calendarDate);

        return cd.get(Calendar.YEAR);
    }

    public static String getMonth(Activity activity, long time) {
        Date calendarDate = new Date(time);
        return DateFormat.format("MMMM", calendarDate).toString();
    }

    public static String getWeek(Activity activity, long time) {
        if (isToday(time)) {
            return activity.getString(R.string.today_label);
        }
        Date calendarDate = new Date(time);
        return DateFormat.format("EEEE", calendarDate).toString();
    }

    public static int getDay(long time) {
        Calendar cd = Calendar.getInstance();
        Date calendarDate = new Date(time);
        cd.setTime(calendarDate);

        return cd.get(Calendar.DAY_OF_MONTH);
    }

    public static String getHour(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date calendarDate = new Date(time);
        String hour = sdf.format(calendarDate);
        return hour;
    }

    public static boolean isToday(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date calendarDate = new Date(time);
        String calendarTime = sdf.format(calendarDate);

        Date currentDate = new Date(System.currentTimeMillis());
        String currentTime = sdf.format(currentDate);

        return currentTime.equals(calendarTime);
    }

    public static String getDate(long dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(dateTime);
        return sdf.format(date);
    }

    public static long getTodayStamp() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        Date currentDate = new Date(System.currentTimeMillis());
        String currentTime = sdf1.format(currentDate);

        String todayTime = currentTime + " 00:00:00";

        long todayStamp = 0;
        try {
            todayStamp = sdf2.parse(todayTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return todayStamp;
    }

    public static String getTodaySyncTime() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        Date currentDate = new Date(System.currentTimeMillis());
        String currentTime = sdf1.format(currentDate);

        currentTime = currentTime + "T000000Z";
        return currentTime;
    }

    public static String getFourDaysSyncTime() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        long fourDaysStamp = getTodayStamp() + 1000 * 60 * 60 * 24 * 4;
        Date currentDate = new Date(fourDaysStamp);
        String currentTime = sdf1.format(currentDate);

        currentTime = currentTime + "T000000Z";
        return currentTime;
    }

    public static long getFourDaysStamp() {
        long fourDaysStamp = getTodayStamp() + 1000 * 60 * 60 * 24 * 4 - 1;
        return fourDaysStamp;
    }

    public static long getdailyStamp(String datestamp, boolean isdaystart) {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String daytime = datestamp;
        if (isdaystart) {
            daytime  = datestamp + " 00:00:00";
        } else {
            daytime  = datestamp + " 23:59:59";
        }

        long todayStamp= 0;
        try {
            if (isdaystart) {
                todayStamp = sdf2.parse(daytime).getTime();
            } else {
                todayStamp = sdf2.parse(daytime).getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return todayStamp;
    }
}
