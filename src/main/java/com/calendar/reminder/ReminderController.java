package com.calendar.reminder;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReminderController {
    @Autowired
    private ReminderRepository reminderRepository;

    @GetMapping("/")
    String get() {
        return reminderRepository.findAll().toString();
    }

    @GetMapping("/create")
    String create(@RequestParam String content) {
        reminderRepository.save(new Reminder().setContent(content).setDate(DateTime.now()));
        return "create";
    }

}
