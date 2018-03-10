package com.pyco.coreapplication.repository;


import com.pyco.coreapplication.doimain.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, String> {

    Optional<Person> findByUsername(String username);

}
