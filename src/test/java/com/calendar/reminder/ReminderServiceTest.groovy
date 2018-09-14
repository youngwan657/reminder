package com.calendar.reminder

import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import spock.lang.Specification
import spock.lang.Unroll

class ReminderServiceTest extends Specification {
    static ReminderService service = new ReminderService()

    static DateTime now = DateTime.parse("2020-09-12T09:00-07:00");
    static DateTime monday = DateTime.now().withDayOfWeek(DateTimeConstants.MONDAY)
    static DateTime friday = DateTime.now().withDayOfWeek(DateTimeConstants.FRIDAY)

    def day(int year, int month, int day, int hour, int min) {
        return String.format("%04d-%02d-%02dT%02d:%02d:00-07:00", year, month, day, hour, min);
    }

    @Unroll('#input')
    def "parse"() {
        expect:
        def reminder = service.parse(input, inputDate)
        reminder.getContent() == content
        reminder.getReminderTime().getDate() == expectedDate

        where:
        input                       | inputDate || content   | expectedDate
        "10min content"             | now       || "content" | DateTime.parse(day(2020, 9, 12, 9, 10))
        "10 min content"            | now       || "content" | DateTime.parse(day(2020, 9, 12, 9, 10))
        "10minute content"          | now       || "content" | DateTime.parse(day(2020, 9, 12, 9, 10))
        "10minutes content"         | now       || "content" | DateTime.parse(day(2020, 9, 12, 9, 10))
        "After 10min content"       | now       || "content" | DateTime.parse(day(2020, 9, 12, 9, 10))
        "10min later content"       | now       || "content" | DateTime.parse(day(2020, 9, 12, 9, 10))

        "10hour content"            | now       || "content" | DateTime.parse(day(2020, 9, 12, 19, 0))
        "10 hour content"           | now       || "content" | DateTime.parse(day(2020, 9, 12, 19, 0))
        "10hours content"           | now       || "content" | DateTime.parse(day(2020, 9, 12, 19, 0))
        "10 hours content"          | now       || "content" | DateTime.parse(day(2020, 9, 12, 19, 0))

        "at 3pm in 2 days content"  | now       || "content" | DateTime.parse(day(2020, 9, 14, 15, 0))
        "3 days later content"      | now       || "content" | DateTime.parse(day(2020, 9, 15, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN))

        "10:20 content"             | now       || "content" | DateTime.parse(day(2020, 9, 12, 10, 20))
        "10:20am content"           | now       || "content" | DateTime.parse(day(2020, 9, 12, 10, 20))
        "10:20 am content"          | now       || "content" | DateTime.parse(day(2020, 9, 12, 10, 20))
        "10:20a.m content"          | now       || "content" | DateTime.parse(day(2020, 9, 12, 10, 20))
        "10:20 a.m content"         | now       || "content" | DateTime.parse(day(2020, 9, 12, 10, 20))
        "10:20pm content"           | now       || "content" | DateTime.parse(day(2020, 9, 12, 22, 20))
        "10:20 pm content"          | now       || "content" | DateTime.parse(day(2020, 9, 12, 22, 20))
        "10:20p.m content"          | now       || "content" | DateTime.parse(day(2020, 9, 12, 22, 20))
        "10:20 p.m content"         | now       || "content" | DateTime.parse(day(2020, 9, 12, 22, 20))

        "At 22 content"             | now       || "content" | DateTime.parse(day(2020, 9, 12, 22, 0))
        "At 9am content"            | now       || "content" | DateTime.parse(day(2020, 9, 12, 9, 0))
        "At 9 am content"           | now       || "content" | DateTime.parse(day(2020, 9, 12, 9, 0))
        "At 9a.m content"           | now       || "content" | DateTime.parse(day(2020, 9, 12, 9, 0))
        "At 9 a.m content"          | now       || "content" | DateTime.parse(day(2020, 9, 12, 9, 0))

        "At 9pm content"            | now       || "content" | DateTime.parse(day(2020, 9, 12, 21, 0))
        "At 9 pm content"           | now       || "content" | DateTime.parse(day(2020, 9, 12, 21, 0))
        "At 9p.m content"           | now       || "content" | DateTime.parse(day(2020, 9, 12, 21, 0))
        "At 9 p.m content"          | now       || "content" | DateTime.parse(day(2020, 9, 12, 21, 0))

        "Tomorrow content"          | now       || "content" | DateTime.parse(day(2020, 9, 13, Const.WEEKEND_HOUR, Const.WEEKEND_MIN))
        "At 9 tomorrow content"     | now       || "content" | DateTime.parse(day(2020, 9, 13, 9, 0))
        "At 9am tomorrow content"   | now       || "content" | DateTime.parse(day(2020, 9, 13, 9, 0))
        "At 9 am tomorrow content"  | now       || "content" | DateTime.parse(day(2020, 9, 13, 9, 0))
        "At 9a.m tomorrow content"  | now       || "content" | DateTime.parse(day(2020, 9, 13, 9, 0))
        "At 9 a.m tomorrow content" | now       || "content" | DateTime.parse(day(2020, 9, 13, 9, 0))

        "At 9pm tomorrow content"   | now       || "content" | DateTime.parse(day(2020, 9, 13, 21, 0))
        "At 9 pm tomorrow content"  | now       || "content" | DateTime.parse(day(2020, 9, 13, 21, 0))
        "At 9p.m tomorrow content"  | now       || "content" | DateTime.parse(day(2020, 9, 13, 21, 0))
        "At 9 p.m tomorrow content" | now       || "content" | DateTime.parse(day(2020, 9, 13, 21, 0))


        "On Monday content"         | now       || "content" | DateTime.parse(day(2020, 9, 14, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN))
        "On Mon content"            | now       || "content" | DateTime.parse(day(2020, 9, 14, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN))
        "Mon content"               | now       || "content" | DateTime.parse(day(2020, 9, 14, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN))

        "On Tuesday content"        | now       || "content" | DateTime.parse(day(2020, 9, 15, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN))
        "On Tue content"            | now       || "content" | DateTime.parse(day(2020, 9, 15, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN))
        "Tue content"               | now       || "content" | DateTime.parse(day(2020, 9, 15, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN))

        "On Wednesday content"      | now       || "content" | DateTime.parse(day(2020, 9, 16, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN))
        "On Wed content"            | now       || "content" | DateTime.parse(day(2020, 9, 16, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN))
        "Wed content"               | now       || "content" | DateTime.parse(day(2020, 9, 16, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN))

        "On Thursday content"       | now       || "content" | DateTime.parse(day(2020, 9, 17, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN))
        "On Thu content"            | now       || "content" | DateTime.parse(day(2020, 9, 17, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN))
        "Thu content"               | now       || "content" | DateTime.parse(day(2020, 9, 17, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN))

        "On Friday content"         | now       || "content" | DateTime.parse(day(2020, 9, 18, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN))
        "On Fri content"            | now       || "content" | DateTime.parse(day(2020, 9, 18, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN))
        "Fri content"               | now       || "content" | DateTime.parse(day(2020, 9, 18, Const.WEEKDAY_HOUR, Const.WEEKDAY_MIN))

        "On Saturday content"       | now       || "content" | DateTime.parse(day(2020, 9, 12, Const.WEEKEND_HOUR, Const.WEEKEND_MIN))
        "On Sat content"            | now       || "content" | DateTime.parse(day(2020, 9, 12, Const.WEEKEND_HOUR, Const.WEEKEND_MIN))
        "Sat content"               | now       || "content" | DateTime.parse(day(2020, 9, 12, Const.WEEKEND_HOUR, Const.WEEKEND_MIN))

        "On Sunday content"         | now       || "content" | DateTime.parse(day(2020, 9, 13, Const.WEEKEND_HOUR, Const.WEEKEND_MIN))
        "On Sun content"            | now       || "content" | DateTime.parse(day(2020, 9, 13, Const.WEEKEND_HOUR, Const.WEEKEND_MIN))
        "Sun content"               | now       || "content" | DateTime.parse(day(2020, 9, 13, Const.WEEKEND_HOUR, Const.WEEKEND_MIN))

        "On Sunday 10:20 content"   | now       || "content" | DateTime.parse(day(2020, 9, 13, 10, 20))
        "10:20 on Sunday content"   | now       || "content" | DateTime.parse(day(2020, 9, 13, 10, 20))

        "Next Sunday 10:20 content" | now       || "content" | DateTime.parse(day(2020, 9, 20, 10, 20))

        "In the morning content"    | now       || "content" | DateTime.parse(day(2020, 9, 12, Const.MORNING, 0))
        "Morning content"           | now       || "content" | DateTime.parse(day(2020, 9, 12, Const.MORNING, 0))

        "In the afternoon content"  | now       || "content" | DateTime.parse(day(2020, 9, 12, Const.AFTERNOON, 0))
        "Afternoon content"         | now       || "content" | DateTime.parse(day(2020, 9, 12, Const.AFTERNOON, 0))

        "In the evening content"    | now       || "content" | DateTime.parse(day(2020, 9, 12, Const.EVENING, 0))
        "Evening content"           | now       || "content" | DateTime.parse(day(2020, 9, 12, Const.EVENING, 0))

        "At night content"          | now       || "content" | DateTime.parse(day(2020, 9, 12, Const.NIGHT, 0))
        "Night content"             | now       || "content" | DateTime.parse(day(2020, 9, 12, Const.NIGHT, 0))
    }
}
