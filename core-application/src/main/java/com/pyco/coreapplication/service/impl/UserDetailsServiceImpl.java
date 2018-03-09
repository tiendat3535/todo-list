package com.pyco.coreapplication.service.impl;

import com.pyco.coreapplication.repository.PersonRepository;
import com.pyco.coreapplication.doimain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository.findByUsername(username)
                .map(person -> new Person(person.getId(), person.getUsername(), person.getPassword(),
                    AuthorityUtils.createAuthorityList("USER")))
                .orElseThrow(() -> new UsernameNotFoundException("Could not find the user '" + username + "'"));
    }
}
