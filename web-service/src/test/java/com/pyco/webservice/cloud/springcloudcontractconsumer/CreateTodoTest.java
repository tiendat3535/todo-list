package com.pyco.webservice.cloud.springcloudcontractconsumer;

import com.pyco.webservice.WebServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureStubRunner(
        workOffline = true,
        ids = "com.pyco:core-application:+:stubs:8001")
public class CreateTodoTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenWhenPassTaskDtoAsParamThenReturnCreatedTaskDto() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8000/public/todos")
                .contentType(MediaType.APPLICATION_JSON).content("{ \"content\": \"spring-cloud-contract-task\"}"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("{\"content\":\"spring-cloud-contract-task\",\"done\":false}"));
    }

}
