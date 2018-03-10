package com.pyco.coreapplication.doimain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Document
public class Task extends BaseEntity {

    @NotNull
    private String content;
    private boolean done;
    @NotNull
    private String personId;

    public Task() {
    }

    public Task(String content) {
        this.content = content;
    }

    public Task(String content, String personId) {
        this.content = content;
        this.personId = personId;
    }

    public Task(String content, boolean done, String personId) {
        this.content = content;
        this.done = done;
        this.personId = personId;
    }

}
