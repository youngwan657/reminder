package com.calendar.reminder

import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import spock.lang.Specification
import spock.lang.Unroll

import java.util.regex.Matcher
import java.util.regex.Pattern

class ReminderServiceTest extends Specification {
    static ReminderService service = new ReminderService()

    static DateTime now = DateTime.now()
    static DateTime monday = DateTime.now().withDayOfWeek(DateTimeConstants.MONDAY)
    static DateTime friday = DateTime.now().withDayOfWeek(DateTimeConstants.FRIDAY)

    @Unroll
    def "parse"() {
        expect:
        def reminder = service.parse(input, inputDate)
        reminder.getContent() == content
        reminder.getReminderTime().getDate() == outputDate

        where:
        input                       | inputDate || content   | outputDate
//        "10min content"             | now       || "content" | new ReminderTime(now).addMinute(10).getDate()
//        "10 min content"            | now       || "content" | service.truncate(now.plusMinutes(10))
//        "10minute content"          | now       || "content" | service.truncate(now.plusMinutes(10))
//        "10minutes content"         | now       || "content" | service.truncate(now.plusMinutes(10))
//        "After 10min content"       | now       || "content" | new ReminderTime(now).addMinute(10).getDate()
//        "10min later content"       | now       || "content" | new ReminderTime(now).addMinute(10).getDate()
//
//        "10hour content"            | now       || "content" | service.truncate(now.plusHours(10))
//        "10 hour content"           | now       || "content" | service.truncate(now.plusHours(10))
//        "10hours content"           | now       || "content" | service.truncate(now.plusHours(10))
//        "10 hours content"          | now       || "content" | service.truncate(now.plusHours(10))

        "10:20 content"             | now       || "content" | new ReminderTime(now).setTime(10, 20).getDate()
        "10:20am content"           | now       || "content" | new ReminderTime(now).setTime(10, 20).getDate()
        "10:20 am content"          | now       || "content" | new ReminderTime(now).setTime(10, 20).getDate()
        "10:20a.m content"          | now       || "content" | new ReminderTime(now).setTime(10, 20).getDate()
        "10:20 a.m content"         | now       || "content" | new ReminderTime(now).setTime(10, 20).getDate()
//
        "10:20pm content"           | now       || "content" | new ReminderTime(now).setTime(22, 20).getDate()
        "10:20 pm content"          | now       || "content" | new ReminderTime(now).setTime(22, 20).getDate()
        "10:20p.m content"          | now       || "content" | new ReminderTime(now).setTime(22, 20).getDate()
        "10:20 p.m content"         | now       || "content" | new ReminderTime(now).setTime(22, 20).getDate()
//
//        "At 9 content"              | now       || "content" | service.setHourAndMinute(now, 9, 0)
//        "At 9am content"            | now       || "content" | service.setHourAndMinute(now, 9, 0)
//        "At 9 am content"           | now       || "content" | service.setHourAndMinute(now, 9, 0)
//        "At 9a.m content"           | now       || "content" | service.setHourAndMinute(now, 9, 0)
//        "At 9 a.m content"          | now       || "content" | service.setHourAndMinute(now, 9, 0)
//
//        "At 9pm content"            | now       || "content" | service.setHourAndMinute(now, 21, 0)
//        "At 9 pm content"           | now       || "content" | service.setHourAndMinute(now, 21, 0)
//        "At 9p.m content"           | now       || "content" | service.setHourAndMinute(now, 21, 0)
//        "At 9 p.m content"          | now       || "content" | service.setHourAndMinute(now, 21, 0)
//
//        "Tomorrow content"          | friday    || "content" | service.setHourAndMinute(now.plusDays(1), WEEKEND_HOUR, WEEKEND_MIN)
//        "At 9 tomorrow content"     | friday    || "content" | service.setHourAndMinute(now.plusDays(1), WEEKEND_HOUR, WEEKEND_MIN)
//        "At 9am tomorrow content"   | friday    || "content" | service.setHourAndMinute(now.plusDays(1), WEEKEND_HOUR, WEEKEND_MIN)
//        "At 9 am tomorrow content"  | friday    || "content" | service.setHourAndMinute(now.plusDays(1), WEEKEND_HOUR, WEEKEND_MIN)
//        "At 9a.m tomorrow content"  | friday    || "content" | service.setHourAndMinute(now.plusDays(1), WEEKEND_HOUR, WEEKEND_MIN)
//        "At 9 a.m tomorrow content" | friday    || "content" | service.setHourAndMinute(now.plusDays(1), WEEKEND_HOUR, WEEKEND_MIN)
//
//        "Tomorrow content"          | monday    || "content" | service.setHourAndMinute(now.plusDays(1), WEEKDAY_HOUR, WEEKDAY_MIN)
//        "At 9 tomorrow content"     | monday    || "content" | service.setHourAndMinute(now.plusDays(1), WEEKDAY_HOUR, WEEKDAY_MIN)
//        "At 9am tomorrow content"   | monday    || "content" | service.setHourAndMinute(now.plusDays(1), WEEKDAY_HOUR, WEEKDAY_MIN)
//        "At 9 am tomorrow content"  | monday    || "content" | service.setHourAndMinute(now.plusDays(1), WEEKDAY_HOUR, WEEKDAY_MIN)
//        "At 9a.m tomorrow content"  | monday    || "content" | service.setHourAndMinute(now.plusDays(1), WEEKDAY_HOUR, WEEKDAY_MIN)
//        "At 9 a.m tomorrow content" | monday    || "content" | service.setHourAndMinute(now.plusDays(1), WEEKDAY_HOUR, WEEKDAY_MIN)
//
//        "At 9pm tomorrow content"   | monday    || "content" | service.setHourAndMinute(now.plusDays(1), WEEKDAY_HOUR, WEEKDAY_MIN)
//        "At 9 pm tomorrow content"  | monday    || "content" | service.setHourAndMinute(now.plusDays(1), WEEKDAY_HOUR, WEEKDAY_MIN)
//        "At 9p.m tomorrow content"  | monday    || "content" | service.setHourAndMinute(now.plusDays(1), WEEKDAY_HOUR, WEEKDAY_MIN)
//        "At 9 p.m tomorrow content" | monday    || "content" | service.setHourAndMinute(now.plusDays(1), WEEKDAY_HOUR, WEEKDAY_MIN)
//
        "On Monday content"         | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.MONDAY, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN).getDate()
        "On Mon content"            | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.MONDAY, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN).getDate()
        "Mon content"               | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.MONDAY, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN).getDate()

        "On Tuesday content"        | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.TUESDAY, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN).getDate()
        "On Tue content"            | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.TUESDAY, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN).getDate()
        "Tue content"               | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.TUESDAY, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN).getDate()

        "On Wednesday content"      | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.WEDNESDAY, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN).getDate()
        "On Wed content"            | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.WEDNESDAY, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN).getDate()
        "Wed content"               | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.WEDNESDAY, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN).getDate()

        "On Thursday content"       | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.THURSDAY, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN).getDate()
        "On Thu content"            | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.THURSDAY, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN).getDate()
        "Thu content"               | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.THURSDAY, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN).getDate()

        "On Friday content"         | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.FRIDAY, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN).getDate()
        "On Fri content"            | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.FRIDAY, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN).getDate()
        "Fri content"               | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.FRIDAY, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN).getDate()

        "On Saturday content"       | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.SATURDAY, Const.WEEKEND_HOUR, Const.WEEKEND_MIN).getDate()
        "On Sat content"            | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.SATURDAY, Const.WEEKEND_HOUR, Const.WEEKEND_MIN).getDate()
        "Sat content"               | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.SATURDAY, Const.WEEKEND_HOUR, Const.WEEKEND_MIN).getDate()

        "On Sunday content"         | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.SUNDAY, Const.WEEKEND_HOUR, Const.WEEKEND_MIN).getDate()
        "On Sun content"            | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.SUNDAY, Const.WEEKEND_HOUR, Const.WEEKEND_MIN).getDate()
        "Sun content"               | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.SUNDAY, Const.WEEKEND_HOUR, Const.WEEKEND_MIN).getDate()

        "On Sunday 10:20 content"   | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.SUNDAY, 10, 20).getDate()
        "10:20 on Sunday content"   | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.SUNDAY, 10, 20).getDate()

        "Next Sunday 10:20 content" | now       || "content" | new ReminderTime(now).setDayOfWeekWithTime(DateTimeConstants.SUNDAY, 10, 20).afterDate(7).getDate();
//
        "10:20p.m content" | now || "content" | new ReminderTime(now).setTime(22, 20).getDate()
//        "In the morning content"    | now       || "content" | service.setHourAndMinute(now, MORNING, 0)
//        "Morning content"           | now       || "content" | service.setHourAndMinute(now, MORNING, 0)
//
//        "In the afternoon content"  | now       || "content" | service.setHourAndMinute(now, AFTERNOON, 0)
//        "Afternoon content"         | now       || "content" | service.setHourAndMinute(now, AFTERNOON, 0)
//
//        "In the evening content"    | now       || "content" | service.setHourAndMinute(now, EVENING, 0)
//        "Evening content"           | now       || "content" | service.setHourAndMinute(now, EVENING, 0)
//
//        "At night content"          | now       || "content" | service.setHourAndMinute(now, NIGHT, 0)
//        "Night content"             | now       || "content" | service.setHourAndMinute(now, NIGHT, 0)
    }

    def "ASdf"() {
        when:
        String input = "12:00";
        Matcher matcher = Pattern.compile("([0-9]|0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])(am|pm|a.m|p.m|a.m.|p.m.|)").matcher(input);

        then:
        if (matcher.find()) {
            println matcher.group(0)
            println matcher.group(1)
            println matcher.group(2)
            println matcher.group(3)
        }
    }
}
