package com.pyco.coreapplication.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class TaskCriteria {

    private String personId;
    private Optional<String> content = Optional.empty();
    private Optional<LocalDate> startDate = Optional.empty();
    private Optional<LocalDate> endDate = Optional.empty();

    public TaskCriteria(String personId, String content, LocalDate startDate, LocalDate endDate) {
        this.personId = personId;
        this.content = Optional.ofNullable(content);
        this.startDate = Optional.ofNullable(startDate);
        this.endDate = Optional.ofNullable(endDate);
    }
}
