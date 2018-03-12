package com.pyco.coreapplication.service;

import com.pyco.coreapplication.common.BaseTest;
import com.pyco.coreapplication.domain.Person;
import com.pyco.coreapplication.exception.UserAlreadyExistsException;
import com.pyco.coreapplication.repository.PersonRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class PersonServiceImplTest extends BaseTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() throws Exception {
        cleanUpDatabase();
    }

    @Test
    public void testRegisterPersonSuccessfully() throws UserAlreadyExistsException {
        // Given
        Person person = new Person("person", "123456");
        String password = person.getPassword();
        // When
        Person registeredPerson = personService.registerPerson(person);
        // Then
        assertThat(registeredPerson.getUsername()).isEqualTo(person.getUsername());
        assertThat(passwordEncoder.matches(password, registeredPerson.getPassword())).isTrue();
    }

    @Test(expected = UserAlreadyExistsException.class)
    @Ignore(value = "Do not know why embeded mongodb does not check duplicate indexed username of person")
    public void testRegisterPersonThrowException() {
        // Given
        Person person1 = new Person("person1", "123456");
        personRepository.save(person1);
        Person duplicatedUsernamePerson = new Person("person1", "123456");
        // When
        personService.registerPerson(duplicatedUsernamePerson);
    }

    @Test
    public void testFindByUsername() {
        // Given
        Person expectedPerson = new Person("expectedPerson", "123456");
        personRepository.save(expectedPerson);
        // When
        Optional<Person> actualPerson = personService.findPersonByUsername(expectedPerson.getUsername());
        // Then
        assertThat(actualPerson.isPresent()).isTrue();
        assertThat(actualPerson.get().getUsername()).isEqualTo(expectedPerson.getUsername());
    }

    @After
    public void tearDown() throws Exception {
        cleanUpDatabase();
    }

    private void cleanUpDatabase() {
        mongoTemplate.getDb().dropDatabase();
    }
}
