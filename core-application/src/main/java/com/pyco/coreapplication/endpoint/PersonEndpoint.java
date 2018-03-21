package com.pyco.coreapplication.endpoint;

import com.pyco.coreapplication.exception.UserAlreadyExistsException;
import com.pyco.coreapplication.domain.Person;
import com.pyco.coreapplication.dto.ErrorDto;
import com.pyco.coreapplication.dto.UserDto;
import com.pyco.coreapplication.mapper.PersonMapper;
import com.pyco.coreapplication.mapper.UserDtoMapper;
import com.pyco.coreapplication.service.PersonService;
import com.pyco.coreapplication.util.ErrorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("v1/public")
public class PersonEndpoint {

    public static final String USERNAME_ALREADY_EXISTS_USER_MESSAGE = "This username: %s has already existed. Please choose another one.";
    private static Logger logger = LoggerFactory.getLogger(PersonEndpoint.class);

    @Autowired
    private PersonService personService;

    @PostMapping("users")
    public ResponseEntity<?> registerPerson(@Validated @RequestBody UserDto userDto) {
        Person person = UserDtoMapper.INSTANCE.toPerson(userDto);
        try {
            personService.registerPerson(person);
        } catch (UserAlreadyExistsException e) {
            UUID uuid = ErrorUtil.logError(logger, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDto(uuid.toString(), String.format(USERNAME_ALREADY_EXISTS_USER_MESSAGE, e.getUsername()),null));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(PersonMapper.INSTANCE.toUserDto(person));
    }

}
