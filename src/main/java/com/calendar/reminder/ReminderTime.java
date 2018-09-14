package com.calendar.reminder;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

@Getter
@Setter
public class ReminderTime {
    private DateTime date;

    public ReminderTime(DateTime date) {
        this.date = date;
    }

    ReminderTime setMonthDay(int month, int day) throws Exception {
        DateTime setTime = date.withMonthOfYear(month).withDayOfMonth(day);
        if (!isValid(setTime)) {
            throw new Exception();
        }
        date = setTime;
        return this;
    }

    ReminderTime setDayOfWeekWithTime(int day, int hour, int min) throws Exception {
        setDayOfWeek(day);
        setTime(hour, min);
        return this;
    }

    ReminderTime setDayOfWeek(int day) {
        DateTime dateChanged = date.withDayOfWeek(day);
        if (date.isAfter(dateChanged)) {
            date = dateChanged.plusWeeks(1);
        } else {
            date = dateChanged;
        }
        return this;
    }

    ReminderTime afterDateWithTime(int date, int hour, int min) throws Exception {
        this.date = this.date.plusDays(date);
        setTime(hour, min);
        truncate();
        return this;
    }

    ReminderTime afterDate(int date) throws Exception {
        this.date = this.date.plusDays(date);
        truncate();
        return this;
    }

    ReminderTime afterHour(int hour) throws Exception {
        date = date.plusHours(hour);
        truncate();
        return this;
    }

    ReminderTime afterMin(int min) throws Exception {
        date = date.plusMinutes(min);
        truncate();
        return this;
    }

    ReminderTime setTime(int hour, int minute) throws Exception {
        date = date.withHourOfDay(hour).withMinuteOfHour(minute);
        truncate();
        if (DateTime.now().isAfter(date)) {
            throw new Exception();
        }
        return this;
    }

    void truncate() {
        date = date.withSecondOfMinute(0).withMillisOfSecond(0);
    }

    boolean isValid(DateTime now) {
        if (now.isAfter(date)) {
            return false;
        }
        return true;
    }
}
