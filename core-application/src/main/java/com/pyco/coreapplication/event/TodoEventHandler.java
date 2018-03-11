package com.pyco.coreapplication.event;

import com.pyco.coreapplication.domain.Task;
import com.pyco.coreapplication.dto.TaskDtoPayLoad;
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

    @KafkaListener(
        topics = CREATE_TASK_DTO_TOPIC,
        containerFactory = "kafkaListenerContainerFactory")
    public void receiveCreateTaskDto(TaskDtoPayLoad taskDtoPayLoad) {
        Validate.notNull(taskDtoPayLoad.getEncryptedUsername());
        Validate.notNull(taskDtoPayLoad.getTaskDto().getContent());
        Task task = TaskDtoMapper.INSTANCE.toTask(taskDtoPayLoad.getTaskDto());
        taskService.createTask(task, textEncryptor.decrypt(taskDtoPayLoad.getEncryptedUsername()));
    }

}
