package com.pyco.coreapplication.service;

import com.pyco.coreapplication.domain.Task;
import com.pyco.coreapplication.dto.TaskCriteria;
import com.pyco.coreapplication.exception.PersonNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    /**
     * Get all Tasks that a Person has
     * @param taskCriteria {@link TaskCriteria} including personId, content, startDate, endDate
     * @param pageable The {@link Pageable} Spring Data use to apply pagination and sorting
     * @param fields Fields user want to take
     * @return {@link List <Task>} that a Person has
     */
    Page<Task> findAllTasksOfPerson(TaskCriteria taskCriteria, Pageable pageable, String[] fields);

    /**
     *
     * @param id id of that task
     * @return {@link List <Task>} by its id
     */
    Task findTaskById(String id);

    /**
     * Create a new Task
     * @param task The {@link Task} person want to add into task list
     * @return created {@link Task}
     */
    Task createTask(Task task);

    /**
     * Create a new Task (this service is just used with kafka)
     * @param task The {@link Task} person want to add into task list
     * @param username The encryptedUsername of person want to add this task
     * @return created {@link Task}
     * @throws {@link PersonNotFoundException}
     */
     Task createTask(Task task, String username);

    /**
     * Update a Task
     * @param task The {@link Task} need to be updated
     * @return updated {@link Task}
     */
    Task updateTask(Task task);

    /**
     * Delete a Task based on its taskId
     * @param taskId
     */
    void deleteTask(String taskId);

}
