package com.pyco.coreapplication.service.impl;

import com.pyco.coreapplication.doimain.Person;
import com.pyco.coreapplication.doimain.Task;
import com.pyco.coreapplication.dto.TaskCriteria;
import com.pyco.coreapplication.exception.PersonNotFoundException;
import com.pyco.coreapplication.repository.PersonRepository;
import com.pyco.coreapplication.repository.TaskRepository;
import com.pyco.coreapplication.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Page<Task> findAllTasksOfPerson(TaskCriteria taskCriteria, Pageable pageable, String[] fields) {
        return taskRepository.findAllByTaskCriteriaWithPagingSortingProjection(taskCriteria, pageable, fields);
    }

    @Override
    public Task findTaskById(String id) {
        return taskRepository.findOne(id);
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.insert(task);
    }

    @Override
    public Task createTask(Task task, String username) {
        Person person = personRepository.findByUsername(username).orElseThrow(() -> new PersonNotFoundException(username));
        task.setPersonId(person.getId());
        return taskRepository.insert(task);
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
