package com.pyco.coreapplication.service.impl;

import com.pyco.coreapplication.exception.UserAlreadyExistsException;
import com.pyco.coreapplication.repository.PersonRepository;
import com.pyco.coreapplication.domain.Person;
import com.pyco.coreapplication.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Person registerPerson(Person person) throws UserAlreadyExistsException {
        Person registeredPerson;
        try {
            person.setPassword(passwordEncoder.encode(person.getPassword()));
            registeredPerson = personRepository.insert(person);
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExistsException(person.getUsername(), e);
        }
        return registeredPerson;
    }

    @Override
    public Optional<Person> findPersonByUsername(String username) {
        return personRepository.findByUsername(username);
    }
}
