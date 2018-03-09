package com.pyco.coreapplication.doimain;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@QueryEntity
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

    public Task(String content, boolean done, String personId) {
        this.content = content;
        this.done = done;
        this.personId = personId;
    }

}
