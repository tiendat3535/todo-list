package com.pyco.coreapplication.repository;

import com.pyco.coreapplication.doimain.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface TaskRepository extends MongoRepository<Task, String>, TaskRepositoryCustom {

}
