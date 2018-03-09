package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class TaskDto extends BaseDto {

    @NotNull
    private String content;
    private boolean done;

    public TaskDto(String content) {
        this.content = content;
    }
}
