package com.calendar.reminder;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.HashMap;
import java.util.Map;

public class ReminderService {
    public Reminder calculate(String input) throws Exception {
        Container container = parse(input, DateTime.now());

        Reminder reminder = new Reminder();
        reminder.setContent(container.getContent());
        reminder.setDate(container.getReminderTime().getDate());
        return reminder;
    }


    Map<String, Integer> dayOfWeek = new HashMap<String, Integer>() {
        {
            put("mon", 1);
            put("monday", 1);
            put("tue", 2);
            put("tuesday", 2);
            put("wed", 3);
            put("wednesday", 3);
            put("thu", 4);
            put("thursday", 4);
            put("fri", 5);
            put("friday", 5);
            put("sat", 6);
            put("saturday", 6);
            put("sun", 7);
            put("sunday", 7);
        }
    };

    Container parse(String input, DateTime now) throws Exception {
        ReminderTime reminderTime = new ReminderTime(now);
        String[] parts = StringUtils.split(input, " ,");

        boolean alreadySetupTime = false;
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equalsIgnoreCase("next")) {
                // next Sunday
                i++;
                Integer dayOfWeek = this.dayOfWeek.get(parts[i].toLowerCase());
                if (dayOfWeek != null) {
                    reminderTime.setDayOfWeek(dayOfWeek).afterDate(7);
                    if (!alreadySetupTime) {
                        if (dayOfWeek == DateTimeConstants.SATURDAY || dayOfWeek == DateTimeConstants.SUNDAY) {
                            reminderTime.setTime(Const.WEEKEND_HOUR, Const.WEEKEND_MIN);
                        } else {
                            reminderTime.setTime(Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN);
                        }
                    }
                } else {
                    break;
                }
            } else if (this.dayOfWeek.get(parts[i].toLowerCase()) != null) {
                // Monday, Tuesday
                Integer dayOfWeek = this.dayOfWeek.get(parts[i].toLowerCase());
                reminderTime.setDayOfWeek(dayOfWeek);
                if (!alreadySetupTime) {
                    if (dayOfWeek == DateTimeConstants.SATURDAY || dayOfWeek == DateTimeConstants.SUNDAY) {
                        reminderTime.setTime(Const.WEEKEND_HOUR, Const.WEEKEND_MIN);
                    } else {
                        reminderTime.setTime(Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN);
                    }
                }
            } else if (parts[i].toLowerCase().contains("at")) {
                if (parts[i].length() == 2) {
                    // At 10, At 10pm, At 10am
                    i++;
                } else {
                    // at10, at7
                }
            } else if (parts[i].matches("([0-9]|0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(|am|pm|a.m|p.m|a.m.|p.m.|)")) {
                alreadySetupTime = true;
                String[] hourMin = parts[i].split(":");
                if (hourMin[1].length() <= 2) {
                    // 11:00 pm, 11:00 p.m
                    if (parts[i + 1].equalsIgnoreCase("pm") || parts[i + 1].equalsIgnoreCase("p.m")) {
                        reminderTime.setTime(Integer.valueOf(hourMin[0]) + 12, Integer.valueOf(hourMin[1]));
                        i++;
                    } else {
                        // 11:00 am, 11:00 a.m
                        if (parts[i + 1].equalsIgnoreCase("am") || parts[i + 1].equalsIgnoreCase("a.m")) {
                            reminderTime.setTime(Integer.valueOf(hourMin[0]), Integer.valueOf(hourMin[1]));
                            i++;
                        }

                        // 11:00
                        reminderTime.setTime(Integer.valueOf(hourMin[0]), Integer.valueOf(hourMin[1]));
                    }
                } else {
                    // 11:00am, 11:00a.m, 11:00a.m.
                    int min = Integer.valueOf(hourMin[1].substring(0, 2));
                    if (hourMin[1].contains("pm") || hourMin[1].contains("p.m")) {
                        reminderTime.setTime(Integer.valueOf(hourMin[0]) + 12, min);
                    } else if (hourMin[1].contains("am") || hourMin[1].contains("a.m")) {
                        reminderTime.setTime(Integer.valueOf(hourMin[0]), min);
                    }
                }
            }
        }


        // 11:00  2:00
        // At 5
        // 11/20
        // 10 a.m  10 am   10am
        // next Sunday
        // this friday


        Container container = new Container();
        container.setContent("content");
        container.setReminderTime(reminderTime);

        return container;
    }


    // 10min
    // 10hour
    // 10:20
    // at 9 tomorrow
    // at 9am tomorrow == at 9a.m tomorrow
    // tomorrow 10:20
    // sunday 10:20
    // 2 days later 10:20
    // after 2 days == 2 days later
    // 11/31 10:00
    // 11/31 : default time is 7:30AM
}
