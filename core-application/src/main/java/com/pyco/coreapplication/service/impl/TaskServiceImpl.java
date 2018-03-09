package com.pyco.coreapplication.service.impl;

import com.pyco.coreapplication.doimain.Person;
import com.pyco.coreapplication.doimain.QTask;
import com.pyco.coreapplication.doimain.Task;
import com.pyco.coreapplication.dto.TaskCriteria;
import com.pyco.coreapplication.exception.PersonNotFoundException;
import com.pyco.coreapplication.repository.PersonRepository;
import com.pyco.coreapplication.repository.TaskRepository;
import com.pyco.coreapplication.service.TaskService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Page<Task> findAllTasksOfPerson(TaskCriteria taskCriteria, Pageable pageable) {
        Predicate predicate = QTask.task.personId.eq(getLoggedInPersonId())
                .and(taskCriteria.getContent().map(content -> QTask.task.content.contains(content)).orElse(null))
                .and(taskCriteria.getStartDate().map(startDate -> QTask.task.createdDate.goe(startDate)).orElse(null))
                .and(taskCriteria.getEndDate().map(endDate -> QTask.task.createdDate.loe(endDate)).orElse(null));
        return taskRepository.findAll(predicate, pageable);
    }

    @Override
    public Task findTaskById(String id) {
        return taskRepository.findOne(id);
    }

    @Override
    public Task createTask(Task task) {
        task.setPersonId(getLoggedInPersonId());
        return taskRepository.insert(task);
    }

    @Override
    public Task createTask(Task task, String username) {
        Person person = personRepository.findByUsername(username).orElseThrow(() -> new PersonNotFoundException(username));
        task.setPersonId(person.getId());
        return taskRepository.insert(task);
    }

    private String getLoggedInPersonId() {
        return ((Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(String taskId) {
        taskRepository.delete(taskId);
    }
}
