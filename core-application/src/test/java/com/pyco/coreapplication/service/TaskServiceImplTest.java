package com.pyco.coreapplication.service;

import com.google.common.collect.Lists;
import com.pyco.coreapplication.common.BaseTest;
import com.pyco.coreapplication.configuration.WebSecurityConfigTest;
import com.pyco.coreapplication.domain.Person;
import com.pyco.coreapplication.domain.Task;
import com.pyco.coreapplication.dto.TaskCriteria;
import com.pyco.coreapplication.repository.PersonRepository;
import com.pyco.coreapplication.repository.TaskRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import(value = {WebSecurityConfigTest.class})
public class TaskServiceImplTest extends BaseTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @WithUserDetails("person")
    public void testFindAllTasksOfPerson() {
        List<Task> savedTasks = taskRepository.save(Lists.newArrayList(
                createTask("task1"),
                createTask("task2"),
                createTask("task3")
        ));

        TaskCriteria taskCriteria = new TaskCriteria();
        taskCriteria.setPersonId(getLoggedInPersonId());
        String[] fields = {"id"};

        Page<Task> allTasksOfPerson = taskService.findAllTasksOfPerson(taskCriteria, new PageRequest(0, 20), fields);

        savedTasks.forEach(task -> {
            Optional<Task> foundTask = allTasksOfPerson.getContent().stream().filter(t -> Objects.equals(t.getId(), task.getId())).findFirst();
            assertThat(foundTask.isPresent()).isTrue();
            assertThat(foundTask.get().getContent()).isNull();
            assertThat(foundTask.get().getCreatedDate()).isNull();
            assertThat(foundTask.get().getPersonId()).isNull();
        });

    }

    @Test
    @WithUserDetails("person")
    public void testCreateTask() {
        // Given
        Task task = new Task("content", getLoggedInPersonId());
        // Then
        Task createdTask = taskService.createTask(task);
        // When
        assertThat(createdTask.getContent()).isEqualTo(task.getContent());
        assertThat(createdTask.getCreatedDate()).isEqualTo(LocalDate.now());
        assertThat(createdTask.isDone()).isFalse();
    }

    @Test
    public void testCreateTaskForKafka() {
        // Given
        Person person = new Person("kafkaPerson", "123456");
        personRepository.save(person);
        Task task = new Task();
        task.setContent("content");
        // Then
        Task createdTask = taskService.createTask(task, person.getUsername());
        // When
        assertThat(createdTask.getContent()).isEqualTo(task.getContent());
        assertThat(createdTask.getCreatedDate()).isEqualTo(LocalDate.now());
        assertThat(createdTask.isDone()).isFalse();
        assertThat(createdTask.getPersonId()).isEqualTo(person.getId());
    }

    @Test
    @WithUserDetails("person")
    public void testUpdateTask() {
        // Given
        Task task = new Task("content", getLoggedInPersonId());
        Task createdTask = taskService.createTask(task);

        createdTask.setContent("updatedContent");
        createdTask.setDone(true);
        // When
        Task updatedTask = taskService.updateTask(createdTask);
        // Then
        assertThat(createdTask.getContent()).isEqualTo("updatedContent");
        assertThat(createdTask.isDone()).isTrue();
    }

    @Test
    @WithUserDetails("person")
    public void testDeleteTask() {
        // Given
        Task task = createTask("content");
        // When
        taskService.deleteTask(task.getId());
    }

    private Task createTask(String content) {
        Task task = new Task(content, getLoggedInPersonId());
        Task createdTask = taskService.createTask(task);
        return createdTask;
    }

    private String getLoggedInPersonId() {
        return ((Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
