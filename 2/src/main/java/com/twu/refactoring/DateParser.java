package com.twu.refactoring;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class DateParser {
    public static final String YEAR_STRING_IS_LESS_THAN_4_CHARACTERS = "Year string is less than 4 characters";
    public static final String YEAR_IS_NOT_AN_INTEGER = "Year is not an integer";
    public static final String YEAR_CANNOT_BE_LESS_THAN_2000_OR_MORE_THAN_2012 = "Year cannot be less than 2000 or more than 2012";
    public static final String MONTH_STRING_IS_LESS_THAN_2_CHARACTERS = "Month string is less than 2 characters";
    public static final String MONTH_IS_NOT_AN_INTEGER = "Month is not an integer";
    public static final String MONTH_CANNOT_BE_LESS_THAN_1_OR_MORE_THAN_12 = "Month cannot be less than 1 or more than 12";
    public static final String DATE_STRING_IS_LESS_THAN_2_CHARACTERS = "Date string is less than 2 characters";
    public static final String DATE_IS_NOT_AN_INTEGER = "Date is not an integer";
    public static final String DATE_CANNOT_BE_LESS_THAN_1_OR_MORE_THAN_31 = "Date cannot be less than 1 or more than 31";
    public static final String HOUR_STRING_IS_LESS_THAN_2_CHARACTERS = "Hour string is less than 2 characters";
    public static final String HOUR_IS_NOT_AN_INTEGER = "Hour is not an integer";
    public static final String HOUR_CANNOT_BE_LESS_THAN_0_OR_MORE_THAN_23 = "Hour cannot be less than 0 or more than 23";
    public static final String MINUTE_STRING_IS_LESS_THAN_2_CHARACTERS = "Minute string is less than 2 characters";
    public static final String MINUTE_IS_NOT_AN_INTEGER = "Minute is not an integer";
    public static final String MINUTE_CANNOT_BE_LESS_THAN_0_OR_MORE_THAN_59 = "Minute cannot be less than 0 or more than 59";
    private final String dateAndTimeString;
    private static final HashMap<String, TimeZone> KNOWN_TIME_ZONES = new HashMap<String, TimeZone>();

    static {
        KNOWN_TIME_ZONES.put("UTC", TimeZone.getTimeZone("UTC"));
    }

    /**
     * Takes a date in ISO 8601 format and returns a date
     *
     * @param dateAndTimeString - should be in format ISO 8601 format
     *                          examples -
     *                          2012-06-17 is 17th June 2012 - 00:00 in UTC TimeZone
     *                          2012-06-17TZ is 17th June 2012 - 00:00 in UTC TimeZone
     *                          2012-06-17T15:00Z is 17th June 2012 - 15:00 in UTC TimeZone
     */
    public DateParser(String dateAndTimeString) {
        this.dateAndTimeString = dateAndTimeString;
    }

    public Date parse() {
        int year, month, date, hour, minute;

        year = parseIntAndCheckValid(0, 4, YEAR_STRING_IS_LESS_THAN_4_CHARACTERS, YEAR_IS_NOT_AN_INTEGER, 2000, 2012, YEAR_CANNOT_BE_LESS_THAN_2000_OR_MORE_THAN_2012);
        month = parseIntAndCheckValid(5, 7, MONTH_STRING_IS_LESS_THAN_2_CHARACTERS, MONTH_IS_NOT_AN_INTEGER, 1, 12, MONTH_CANNOT_BE_LESS_THAN_1_OR_MORE_THAN_12);
        date = parseIntAndCheckValid(8, 10, DATE_STRING_IS_LESS_THAN_2_CHARACTERS, DATE_IS_NOT_AN_INTEGER, 1, 31, DATE_CANNOT_BE_LESS_THAN_1_OR_MORE_THAN_31);

        if (dateAndTimeString.substring(11, 12).equals("Z")) {
            hour = 0;
            minute = 0;
        } else {
            hour = parseIntAndCheckValid(11, 13, HOUR_STRING_IS_LESS_THAN_2_CHARACTERS, HOUR_IS_NOT_AN_INTEGER, 0, 23, HOUR_CANNOT_BE_LESS_THAN_0_OR_MORE_THAN_23);
            minute = parseIntAndCheckValid(14, 16, MINUTE_STRING_IS_LESS_THAN_2_CHARACTERS, MINUTE_IS_NOT_AN_INTEGER, 0, 59, MINUTE_CANNOT_BE_LESS_THAN_0_OR_MORE_THAN_59);
        }

        Calendar calendar = setCalendar(year, month, date, hour, minute);
        return calendar.getTime();
    }

    private int parseIntAndCheckValid(int startIndex, int endIndex, String StringIndexOutOfBoundsString, String NumberFormatExceptionString, int downLimit, int upLimit, String IllegalArgumentExceptionString) {
        int year;
        year = parseInt(startIndex, endIndex, StringIndexOutOfBoundsString, NumberFormatExceptionString);
        checkDateValid(year, downLimit, upLimit, IllegalArgumentExceptionString);
        return year;
    }

    private void checkDateValid(int date, int downLimit, int upLimit, String IllegalArgumentExceptionString) {
        if (date < downLimit || date > upLimit)
            throw new IllegalArgumentException(IllegalArgumentExceptionString);
    }

    private int parseInt(int startIndex, int endIndex, String StringIndexOutOfBoundsString, String NumberFormatExceptionString) {
        int parseResult;
        try {
            String intString = dateAndTimeString.substring(startIndex, endIndex);
            parseResult = Integer.parseInt(intString);
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(StringIndexOutOfBoundsString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NumberFormatExceptionString);
        }
        return parseResult;
    }


    private Calendar setCalendar(int year, int month, int date, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(year, month - 1, date, hour, minute, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
}
