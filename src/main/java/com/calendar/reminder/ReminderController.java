package com.calendar.reminder;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReminderController {
    @Autowired
    private ReminderRepository reminderRepository;

    @GetMapping("/get")
    String get() {
        return reminderRepository.findAll().toString();
    }

    @GetMapping("/create/{content}")
    String create(@PathVariable String content) {
        reminderRepository.save(new Reminder().setContent(content).setDate(DateTime.now()));
        return "create";
    }
}
