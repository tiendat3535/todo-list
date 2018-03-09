package com.pyco.coreapplication.service;

import com.google.common.collect.Lists;
import com.pyco.coreapplication.CoreApplication;
import com.pyco.coreapplication.common.BaseTest;
import com.pyco.coreapplication.configuration.WebSecurityConfigTest;
import com.pyco.coreapplication.doimain.Person;
import com.pyco.coreapplication.doimain.Task;
import com.pyco.coreapplication.dto.TaskCriteria;
import com.pyco.coreapplication.repository.PersonRepository;
import com.pyco.coreapplication.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
                taskService.createTask(new Task("task1")),
                taskService.createTask(new Task("task2")),
                taskService.createTask(new Task("task3"))
        ));

        Page<Task> allTasksOfPerson = taskService.findAllTasksOfPerson(new TaskCriteria(), new PageRequest(0, 20));

        savedTasks.forEach(task -> {
            boolean isTaskExist = allTasksOfPerson.getContent().stream().anyMatch(t -> Objects.equals(t.getId(), task.getId()));
            assertThat(isTaskExist).isTrue();
        });

    }

    @Test
    @WithUserDetails("person")
    public void testCreateTask() {
        // Given
        Task task = new Task();
        task.setContent("content");
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
        Task task = new Task();
        task.setContent("content");
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
        Task task = new Task();
        task.setContent("content");
        Task createdTask = taskService.createTask(task);
        // When
        taskService.deleteTask(task.getId());
    }
}
