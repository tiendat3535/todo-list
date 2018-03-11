package com.pyco.coreapplication.repository;


import com.pyco.coreapplication.domain.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, String> {

    Optional<Person> findByUsername(String username);

}
