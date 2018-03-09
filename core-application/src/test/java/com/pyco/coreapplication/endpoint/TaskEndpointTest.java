package com.pyco.coreapplication.endpoint;

import com.pyco.coreapplication.CoreApplication;
import com.pyco.coreapplication.common.BaseTest;
import com.pyco.coreapplication.configuration.WebSecurityConfigTest;
import com.pyco.coreapplication.dto.TaskDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithUserDetails;
import wiremock.com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = WebEnvironment.DEFINED_PORT,
        classes = CoreApplication.class)
@Import(value = {WebSecurityConfigTest.class})
@WithUserDetails("person")
public class TaskEndpointTest extends BaseTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    private TestRestTemplate getTestRestTemplate() {
        return testRestTemplate.withBasicAuth(username, password);
    }

    private ResponseEntity<TaskDto> createTaskDtoWithContent(String content) {
        TaskDto taskDto = new TaskDto();
        taskDto.setContent(content);
        return getTestRestTemplate().postForEntity("/v1/private/todos", taskDto, TaskDto.class);
    }

    @Test
    public void testGetTodos() {
        // Given
        List<TaskDto> createdTasks = Lists.newArrayList(
                                            createTaskDtoWithContent("content1"),
                                            createTaskDtoWithContent("content2"),
                                            createTaskDtoWithContent("content3"))
                                        .stream()
                                        .map(ResponseEntity::getBody)
                                        .collect(Collectors.toList());

        // Then
        ParameterizedTypeReference<RestResponsePage<TaskDto>> responseType = new ParameterizedTypeReference<RestResponsePage<TaskDto>>() { };
        ResponseEntity<RestResponsePage<TaskDto>> responseEntity = getTestRestTemplate().exchange("/v1/private/todos", HttpMethod.GET, null, responseType);

        // When
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        createdTasks.forEach(taskDto -> {
            boolean isTaskDtoExist = responseEntity.getBody().getContent().stream().anyMatch(t -> Objects.equals(t.getId(), taskDto.getId()));
            assertThat(isTaskDtoExist).isTrue();
        });

    }

    @Test
    public void testCreateTodo() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto();
        taskDto.setContent("content");
        // When
        ResponseEntity<TaskDto> taskDtoResponseEntity = getTestRestTemplate().postForEntity("/v1/private/todos", taskDto, TaskDto.class);
        // Then
        assertThat(taskDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(taskDtoResponseEntity.getBody().getContent()).isEqualTo(taskDto.getContent());
        assertThat(taskDtoResponseEntity.getBody().isDone()).isFalse();
    }

    @Test
    public void testUpdateTodo() throws Exception {
        // Given
        ResponseEntity<TaskDto> taskDtoResponseEntity = createTaskDtoWithContent("content");
        TaskDto updateTaskDto = taskDtoResponseEntity.getBody();
        updateTaskDto.setContent("updatedContent");
        updateTaskDto.setDone(true);
        // When
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<TaskDto> entity = new HttpEntity<>(updateTaskDto, headers);
        ResponseEntity<TaskDto> updatedTaskDtoResponseEntity = getTestRestTemplate().exchange("/v1/private/todos", HttpMethod.PUT, entity, TaskDto.class);
        // Then
        assertThat(updatedTaskDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(taskDtoResponseEntity.getBody().isDone()).isTrue();
    }

    @Test
    public void testDeleteTodo() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto();
        taskDto.setContent("content");
        ResponseEntity<TaskDto> taskDtoResponseEntity = getTestRestTemplate().postForEntity("/v1/private/todos", taskDto, TaskDto.class);
        // When
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<?> deletedTaskDtoResponseEntity = getTestRestTemplate().exchange("/v1/private/todos/" + taskDtoResponseEntity.getBody().getId(), HttpMethod.DELETE, entity, String.class);
        // Then
        assertThat(deletedTaskDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
