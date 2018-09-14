package com.calendar.reminder;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReminderService {
    public Reminder calculate(String input) throws Exception {
        Container container = parse(input, DateTime.now());

        Reminder reminder = new Reminder();
        reminder.setContent(container.getContent());
        reminder.setDate(container.getReminderTime().getDate());
        return reminder;
    }


    Map<String, Integer> DAY_OF_WEEK = new HashMap<String, Integer>() {
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

    Map<String, Integer> AM_PM = new HashMap<String, Integer>() {
        {
            put("am", 0);
            put("a.m", 0);
            put("a.m.", 0);
            put("pm", 12);
            put("p.m", 12);
            put("p.m.", 12);
        }
    };

    Set<String> HOUR = new HashSet<String>() {
        {
            add("hours");
            add("hour");
            add("h");
        }
    };

    Set<String> MIN = new HashSet<String>() {
        {
            add("minutes");
            add("minute");
            add("min");
            add("m");
        }
    };

    Set<String> DAY = new HashSet<String>() {
        {
            add("days");
            add("day");
            add("d");
        }
    };

    Container parse(String input, DateTime now) throws Exception {
        ReminderTime reminderTime = new ReminderTime(now);
        String[] parts = StringUtils.split(input, " ,");

        boolean alreadySetupTime = false;
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equalsIgnoreCase("at") && parts[i + 1].equalsIgnoreCase("night") || parts[i].equalsIgnoreCase("night")) {
                // night, at night
                reminderTime.setTime(Const.NIGHT, 0);
            } else if (parts[i].equalsIgnoreCase("morning")) {
                // morning, in the morning
                reminderTime.setTime(Const.MORNING, 0);
            } else if (parts[i].equalsIgnoreCase("afternoon")) {
                // afternoon, in the afternoon
                reminderTime.setTime(Const.AFTERNOON, 0);
            } else if (parts[i].equalsIgnoreCase("evening")) {
                // afternoon, in the evening
                reminderTime.setTime(Const.EVENING, 0);
            } else if (parts[i].equalsIgnoreCase("tomorrow")) {
                // tomorrow
                ReminderTime tomorrow = reminderTime.afterDate(1);
                if (!alreadySetupTime) {
                    if (isWeekend(tomorrow.getDate())) {
                        reminderTime.setTime(Const.WEEKEND_HOUR, Const.WEEKEND_MIN);
                    } else {
                        reminderTime.setTime(Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN);
                    }
                }
            } else if (parts[i].equalsIgnoreCase("next")) {
                // next Sunday
                i++;
                Integer dayOfWeek = this.DAY_OF_WEEK.get(parts[i].toLowerCase());
                if (dayOfWeek != null) {
                    reminderTime.setDayOfWeek(dayOfWeek).afterDate(7);
                    if (!alreadySetupTime) {
                        if (isWeekend(dayOfWeek)) {
                            reminderTime.setTime(Const.WEEKEND_HOUR, Const.WEEKEND_MIN);
                        } else {
                            reminderTime.setTime(Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN);
                        }
                    }
                } else {
                    break;
                }
            } else if (this.DAY_OF_WEEK.get(parts[i].toLowerCase()) != null) {
                // Monday, Tuesday
                Integer dayOfWeek = this.DAY_OF_WEEK.get(parts[i].toLowerCase());
                reminderTime.setDayOfWeek(dayOfWeek);
                if (!alreadySetupTime) {
                    if (isWeekend(dayOfWeek)) {
                        reminderTime.setTime(Const.WEEKEND_HOUR, Const.WEEKEND_MIN);
                    } else {
                        reminderTime.setTime(Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN);
                    }
                }
            } else if (parts[i].toLowerCase().startsWith("at")) {
                alreadySetupTime = true;
                String nextPart;
                if (parts[i].length() == 2) {
                    // At 10, At 10pm, At 10am
                    nextPart = parts[++i];
                } else {
                    // at10, at7
                    nextPart = parts[i].substring(2);
                }

                if (isHour(nextPart).size() > 0) {
                    List<Integer> hour = isHour(nextPart);
                    if (hour.get(1) == Const.NON) {
                        // at10 am, at 10 pm
                        if (AM_PM.containsKey(parts[i + 1].toLowerCase())) {
                            reminderTime.setTime(hour.get(0) + AM_PM.get(parts[i + 1]), hour.get(1));
                            i++;
                        } else {
                            // at 10
                            reminderTime.setTime(hour.get(0), hour.get(1));
                        }
                    } else {
                        if (hour.get(1) == Const.PM) {
                            // at10am, at 10pm
                            reminderTime.setTime(hour.get(0) + 12, 0);
                        } else if (hour.get(1) == Const.AM) {
                            // at10 am, at 10pm
                            reminderTime.setTime(hour.get(0), 0);
                        }
                    }
                }
            } else if (isHourMin(parts[i]).size() > 0) {
                List<Integer> hourMin = isHourMin(parts[i]);
                alreadySetupTime = true;
                if (hourMin.get(2) == Const.NON) {
                    if (AM_PM.containsKey(parts[i + 1].toLowerCase())) {
                        // 11:00 pm, 11:00 p.m, 11:00 am, 11:00 a.m
                        reminderTime.setTime(hourMin.get(0) + AM_PM.get(parts[i + 1].toLowerCase()), hourMin.get(1));
                        i++;
                    } else {
                        // 11:00
                        reminderTime.setTime(hourMin.get(0), hourMin.get(1));
                    }
                } else {
                    if (hourMin.get(2) == Const.PM) {
                        // 11:00pm
                        reminderTime.setTime(hourMin.get(0) + 12, hourMin.get(1));
                    } else if (hourMin.get(2) == Const.AM) {
                        // 11:00am
                        reminderTime.setTime(hourMin.get(0), hourMin.get(1));
                    }
                }
            } else if (isNumber(parts[i]) != null) {
                Integer number = isNumber(parts[i]);
                // 10 day
                if (DAY.contains(parts[i + 1])) {
                    reminderTime.afterDate(number);
                    if (!alreadySetupTime) {
                        if (isWeekend(reminderTime.getDate())) {
                            reminderTime.setTime(Const.WEEKEND_HOUR, Const.WEEKEND_MIN);
                        } else {
                            reminderTime.setTime(Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN);
                        }
                    }
                }

                // 10 hour
                if (HOUR.contains(parts[i + 1])) {
                    reminderTime.afterHour(number);
                }

                // 10 min
                if (MIN.contains(parts[i + 1])) {
                    reminderTime.afterMin(number);
                }
            } else if (isAfterDate(parts[i].toLowerCase()) != null) {
                // 10day
                Integer afterDay = isAfterDate(parts[i].toLowerCase());
                reminderTime.afterDate(afterDay);
                if (!alreadySetupTime) {
                    if (isWeekend(reminderTime.getDate())) {
                        reminderTime.setTime(Const.WEEKEND_HOUR, Const.WEEKEND_MIN);
                    } else {
                        reminderTime.setTime(Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN);
                    }
                }
            } else if (isAfterHour(parts[i].toLowerCase()) != null) {
                // 10hour
                Integer afterHour = isAfterHour(parts[i].toLowerCase());
                reminderTime.afterHour(afterHour);
            } else if (isAfterMin(parts[i].toLowerCase()) != null) {
                // 10min
                Integer afterMin = isAfterMin(parts[i].toLowerCase());
                reminderTime.afterMin(afterMin);
            }
        }

        Container container = new Container();
        container.setContent("content");
        container.setReminderTime(reminderTime);

        return container;
    }


    private List<Integer> isHourMin(String input) {
        Matcher matcher = Pattern.compile("(0[0-9]|1[0-9]|2[0-3]|[0-9]):([0-5][0-9])(am|pm|a.m|p.m|a.m.|p.m.|)").matcher(input);
        List<Integer> parts = Lists.newArrayList();
        if (matcher.find()) {
            parts.add(Integer.valueOf(matcher.group(1)));
            parts.add(Integer.valueOf(matcher.group(2)));
            if (matcher.group(3).contains("a")) {
                parts.add(Const.AM);
            } else if (matcher.group(3).contains("p")) {
                parts.add(Const.PM);
            } else {
                parts.add(Const.NON);
            }
        }
        return parts;
    }

    private Integer isNumber(String input) {
        Matcher matcher = Pattern.compile("^([0-9][0-9][0-9]|[0-9][0-9]|[0-9])$").matcher(input);
        if (matcher.find()) {
            return Integer.valueOf(matcher.group(1));
        }
        return null;
    }

    private Integer isAfterDate(String input) {
        Matcher matcher = Pattern.compile("([0-9][0-9]|[0-9])(days|day|d)").matcher(input);
        if (matcher.find()) {
            return Integer.valueOf(matcher.group(1));
        }
        return null;
    }

    private Integer isAfterHour(String input) {
        Matcher matcher = Pattern.compile("([0-9][0-9]|[0-9])(hours|hour|h)").matcher(input);
        if (matcher.find()) {
            return Integer.valueOf(matcher.group(1));
        }
        return null;
    }

    private Integer isAfterMin(String input) {
        Matcher matcher = Pattern.compile("([0-9][0-9][0-9]|[0-9][0-9]|[0-9])(minutes|minute|min|m)").matcher(input);
        if (matcher.find()) {
            return Integer.valueOf(matcher.group(1));
        }
        return null;
    }

    private List<Integer> isHour(String input) {
        Matcher matcher = Pattern.compile("(0[0-9]|1[0-9]|2[0-3]|[0-9])(am|pm|a.m|p.m|a.m.|p.m.|)").matcher(input);
        List<Integer> parts = Lists.newArrayList();
        if (matcher.find()) {
            parts.add(Integer.valueOf(matcher.group(1)));
            if (matcher.group(2).contains("a")) {
                parts.add(Const.AM);
            } else if (matcher.group(2).contains("p")) {
                parts.add(Const.PM);
            } else {
                parts.add(Const.NON);
            }
        }
        return parts;
    }

    private boolean isWeekend(DateTime date) {
        if (date.getDayOfWeek() == DateTimeConstants.SATURDAY || date.getDayOfWeek() == DateTimeConstants.SUNDAY) {
            return true;
        }
        return false;
    }

    private boolean isWeekend(Integer date) {
        if (date == DateTimeConstants.SATURDAY || date == DateTimeConstants.SUNDAY) {
            return true;
        }
        return false;
    }
}
