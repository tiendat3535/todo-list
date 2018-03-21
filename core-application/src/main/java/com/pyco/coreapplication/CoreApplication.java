package com.pyco.coreapplication;

import com.pyco.coreapplication.configuration.WebSecurityConfig;
import com.pyco.coreapplication.domain.Person;
import com.pyco.coreapplication.endpoint.PersonEndpoint;
import com.pyco.coreapplication.event.TodoEventHandler;
import com.pyco.coreapplication.global.GlobalHandleExceptionHandler;
import com.pyco.coreapplication.repository.PersonRepository;
import com.pyco.coreapplication.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableSpringDataWebSupport
@ComponentScan(basePackageClasses = {TodoEventHandler.class, PersonService.class, WebSecurityConfig.class, PersonEndpoint.class,
		PersonService.class, PersonRepository.class, GlobalHandleExceptionHandler.class})
public class CoreApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		personRepository.deleteAll();
		Person person = new Person("dat", passwordEncoder.encode("123456"));
		personRepository.insert(person);
	}
}
