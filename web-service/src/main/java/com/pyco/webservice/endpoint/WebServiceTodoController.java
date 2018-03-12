package com.pyco.webservice.endpoint;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.pyco.webservice.util.ErrorUtil;
import dto.ErrorDto;
import dto.KafkaPayLoad;
import dto.KafkaTaskDto;
import dto.TaskDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
@RequestMapping("public")
public class WebServiceTodoController {

    private static final String FAIL_TOLERANCE_MESSAGE = "Core Application has been down during the call creation of taskDto.";
    private static final String MORE_INFO_MESSAGE = "Please contact admin with errorId to know more information.";
    // The encrypted encryptedUsername of 'dat' when this webservice make an authenciation request to core-application
    // the core-aplication will send this token to this webservice but now that feature has not been implemented
    // just hard code it here
    public static final String ENCRYPTED_USERNAME = "8aec7eb756d17f27f67fec929ce1ce6b3121ee031ffe579f6565bb5999aec1ed";
    public static final String REQUEST_CREATE_TASK_MESSAGE = "Request create your task has been sent";
    private static Logger logger = LoggerFactory.getLogger(WebServiceTodoController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaTemplate<String, KafkaPayLoad> kafkaTemplate;

    @Value("${kafka.topic.create-task-dto}")
    private String createTaskDtoTopic;

    @PostMapping("todos")
    @HystrixCommand(fallbackMethod = "simpleLogAfterFailure")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createTodo(@Validated @RequestBody TaskDto taskDto) {
        return restTemplate.postForEntity("/v1/private/todos", taskDto, TaskDto.class);
    }

    private ResponseEntity<?> simpleLogAfterFailure(TaskDto taskDto, Throwable e) {
        UUID uuid = ErrorUtil.logError(logger, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto(uuid.toString(), FAIL_TOLERANCE_MESSAGE, MORE_INFO_MESSAGE));
    }

    @PostMapping("kafka/todos")
    @ResponseStatus(HttpStatus.OK)
    public String createTodoWithKafka(@Validated @RequestBody TaskDto taskDto) {
        KafkaPayLoad<KafkaTaskDto> kafkaPayLoad = new KafkaPayLoad(new KafkaTaskDto(ENCRYPTED_USERNAME, taskDto));
        kafkaTemplate.send(createTaskDtoTopic, kafkaPayLoad);
        return REQUEST_CREATE_TASK_MESSAGE;
    }
}
