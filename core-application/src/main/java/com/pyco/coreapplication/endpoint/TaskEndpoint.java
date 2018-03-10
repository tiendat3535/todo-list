package com.pyco.coreapplication.endpoint;

import com.pyco.coreapplication.doimain.Person;
import com.pyco.coreapplication.doimain.Task;
import com.pyco.coreapplication.dto.TaskCriteria;
import com.pyco.coreapplication.dto.TaskDto;
import com.pyco.coreapplication.mapper.TaskDtoMapper;
import com.pyco.coreapplication.mapper.TaskMapper;
import com.pyco.coreapplication.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "v1/private", produces = "application/json")
public class TaskEndpoint {

    @Autowired
    private TaskService taskService;

    @GetMapping("todos")
    public Page<TaskDto> getTodos(Pageable pageable,
                               @RequestParam(value = "fields", required = false) String[] fields,
                               @RequestParam(value = "content", required = false) String content,
                               @RequestParam(value = "startDate", required = false) LocalDate startDate,
                               @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return taskService.findAllTasksOfPerson(new TaskCriteria(getLoggedInPersonId(), content, startDate, endDate), pageable, fields)
                .map(TaskMapper.INSTANCE::toTaskDto);
    }

    @PostMapping("todos")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTodo(@Validated @RequestBody TaskDto taskDto) {
        Task task = TaskDtoMapper.INSTANCE.toTask(taskDto);
        task.setPersonId(getLoggedInPersonId());
        return TaskMapper.INSTANCE.toTaskDto(taskService.createTask(task));
    }

    @PutMapping("todos")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto updateTodo(@Validated @RequestBody TaskDto taskDto) {
        Task foundTask = taskService.findTaskById(taskDto.getId());
        TaskDtoMapper.INSTANCE.updateTask(taskDto, foundTask);
        return TaskMapper.INSTANCE.toTaskDto(taskService.updateTask(foundTask));
    }

    @DeleteMapping("todos/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable("taskId") String taskId) {
        taskService.deleteTask(taskId);
    }


    private String getLoggedInPersonId() {
        return ((Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

}
