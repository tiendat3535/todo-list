package com.pyco.coreapplication.endpoint;

import com.pyco.coreapplication.CoreApplication;
import com.pyco.coreapplication.common.BaseTest;
import com.pyco.coreapplication.configuration.WebSecurityConfigTest;
import com.pyco.coreapplication.dto.UserDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = WebEnvironment.DEFINED_PORT,
        classes = CoreApplication.class)
@Import(value = {WebSecurityConfigTest.class})
public class PersonEndpointTest extends BaseTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testRegisterPerson() {
        // Given
        UserDto userDto = new UserDto("username", "password");
        // When
        ResponseEntity<UserDto> taskDtoResponseEntity = testRestTemplate.postForEntity("/v1/public/users", userDto, UserDto.class);
        // Then
        assertThat(taskDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(taskDtoResponseEntity.getBody().getUsername()).isEqualTo(userDto.getUsername());
    }

}
