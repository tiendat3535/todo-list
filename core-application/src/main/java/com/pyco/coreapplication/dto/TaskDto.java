package com.pyco.coreapplication.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class TaskDto extends BaseDto {

    @NotNull
    private String content;
    private boolean done;

}
