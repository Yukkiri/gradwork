package ru.puchkova.gradwork;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Deadlines {

    private static final int RED = 1;
    private static final int YELLOW = 0;
    private static final int NORMAL = -1;

    private LocalDate deadlineDate;
    private LocalTime deadlineTime;
    private LocalDateTime dateOfDeath;
    private long time;


    //где-то сжирается 30 минут от времени, надо понять, где
    public String deadlineToDate(String deadline) {
        time = Long.parseLong(deadline);
        dateOfDeath = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

        return formatter.format(dateOfDeath);
    }

    public int deadlineImportance(String deadline) {
        time = Long.parseLong(deadline);

        LocalDate currentDate = LocalDate.now();
        deadlineDate = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();

        LocalTime currentTime = LocalTime.now();
        deadlineTime = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalTime();

        if (currentDate.compareTo(deadlineDate) == YELLOW) {
            if (currentTime.compareTo(deadlineTime) < YELLOW)
                return YELLOW;
            else if (currentTime.compareTo(deadlineTime) > YELLOW)
                return RED;
        } else if (currentDate.compareTo(deadlineDate) > YELLOW) {
            return RED;
        }

        return NORMAL;
    }
}
