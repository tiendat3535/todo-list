package com.pyco.coreapplication.repository.impl;

import com.pyco.coreapplication.doimain.Task;
import com.pyco.coreapplication.dto.TaskCriteria;
import com.pyco.coreapplication.repository.TaskRepositoryCustom;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepositoryCustom {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public Page<Task> findAllByTaskCriteriaWithPagingSortingProjection(TaskCriteria taskCriteria, Pageable pageable, String[] fields) {

        Criteria criteria = Criteria.where("personId").is(taskCriteria.getPersonId());
        taskCriteria.getContent().ifPresent(content ->  criteria.andOperator(Criteria.where("content").regex(content)));
        taskCriteria.getStartDate().ifPresent(startDate ->  criteria.andOperator(Criteria.where("createdDate").gte(startDate)));
        taskCriteria.getEndDate().ifPresent(endDate ->  criteria.andOperator(Criteria.where("createdDate").lte(endDate)));

        Query query = new Query();
        query.addCriteria(criteria).with(pageable);

        for (String field: Optional.ofNullable(fields).orElse(ArrayUtils.EMPTY_STRING_ARRAY)) {
            query.fields().include(field);
        }

        return PageableExecutionUtils
                .getPage(mongoOperations.find(query, Task.class), pageable, () -> mongoOperations.count(query, Task.class));
    }
}
