package com.calendar.reminder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private DateTime date;
}
