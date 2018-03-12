package com.pyco.coreapplication.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pyco.coreapplication.dto.KafkaTaskDto;
import com.pyco.coreapplication.dto.KafkaPayLoad;
import com.pyco.coreapplication.domain.Task;
import com.pyco.coreapplication.mapper.TaskDtoMapper;
import com.pyco.coreapplication.service.TaskService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class TodoEventHandler {

    public static final String CREATE_TASK_DTO_TOPIC = "createTaskDto.t";

    @Autowired
    private TaskService taskService;

    @Autowired
    private TextEncryptor textEncryptor;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(
        topics = CREATE_TASK_DTO_TOPIC,
        containerFactory = "kafkaListenerContainerFactory")
    public void receiveCreateTaskDto(KafkaPayLoad<KafkaTaskDto> kafkaPayLoad) {
        KafkaTaskDto payLoad = objectMapper.convertValue(kafkaPayLoad.getPayLoad(), KafkaTaskDto.class);
        handlePayLoad(payLoad);
    }

    private void handlePayLoad(KafkaTaskDto payLoad) {
        Validate.notNull(payLoad.getEncryptedUsername());
        Validate.notNull(payLoad.getTaskDto().getContent());
        Task task = TaskDtoMapper.INSTANCE.toTask(payLoad.getTaskDto());
        taskService.createTask(task, textEncryptor.decrypt(payLoad.getEncryptedUsername()));
    }

}
