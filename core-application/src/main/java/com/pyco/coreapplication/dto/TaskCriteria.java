package com.pyco.coreapplication.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class TaskCriteria {

    private Optional<String> content = Optional.empty();
    private Optional<LocalDate> startDate = Optional.empty();
    private Optional<LocalDate> endDate = Optional.empty();

    public TaskCriteria(String content, LocalDate startDate, LocalDate endDate) {
        this.content = Optional.ofNullable(content);
        this.startDate = Optional.ofNullable(startDate);
        this.endDate = Optional.ofNullable(endDate);
    }
}
