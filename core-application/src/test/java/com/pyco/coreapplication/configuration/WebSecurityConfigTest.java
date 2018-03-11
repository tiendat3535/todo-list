package com.pyco.coreapplication.configuration;

import com.pyco.coreapplication.domain.Person;
import com.pyco.coreapplication.repository.PersonRepository;
import com.pyco.coreapplication.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;

@Configuration
public class WebSecurityConfigTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return createInformation();
            }
        };
    }

    private Person createInformation() {
        Person person = personRepository.findByUsername(username).orElse(null);
        if (person == null) {
            person = personService.registerPerson(new Person(username, password));
        }
        GrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
        return new Person(person.getId(), person.getUsername(), person.getPassword(), Arrays.asList(authority));
    }

}
