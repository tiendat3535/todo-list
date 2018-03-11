package com.pyco.coreapplication.service;

import com.pyco.coreapplication.exception.UserAlreadyExistsException;
import com.pyco.coreapplication.domain.Person;

import java.util.Optional;

public interface PersonService {
    /**
     * Register a Person with encryptedUsername and password
     * @param person {@link Person} the information of person want to register (encryptedUsername and password)
     * @return registered person
     */
    Person registerPerson(Person person) throws UserAlreadyExistsException;

    /**
     * Find Person based on its encryptedUsername
     * @param username encryptedUsername of person
     * @return found {@link Person}
     */
    Optional<Person> findPersonByUsername(String username);

}
