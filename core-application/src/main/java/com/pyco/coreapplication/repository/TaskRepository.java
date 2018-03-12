package com.pyco.coreapplication.repository;

import com.pyco.coreapplication.domain.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String>, TaskRepositoryCustom {

}
