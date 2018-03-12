package com.pyco.coreapplication.repository;

import com.pyco.coreapplication.domain.Task;
import com.pyco.coreapplication.dto.TaskCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskRepositoryCustom {

    Page<Task> findAllByTaskCriteriaWithPagingSortingProjection(TaskCriteria taskCriteria, Pageable pageable, String[] fields);

}
