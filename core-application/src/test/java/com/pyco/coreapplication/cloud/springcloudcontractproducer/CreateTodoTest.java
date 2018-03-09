package com.pyco.coreapplication.cloud.springcloudcontractproducer;

import com.pyco.coreapplication.common.BaseTest;
import com.pyco.coreapplication.configuration.WebSecurityConfigTest;
import com.pyco.coreapplication.endpoint.TaskEndpoint;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

@AutoConfigureMessageVerifier
@Import(value = {WebSecurityConfigTest.class})
@WithUserDetails("person")
public abstract class CreateTodoTest extends BaseTest {

    @Autowired
    private TaskEndpoint taskEndpoint;

    @Before
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder
                = MockMvcBuilders.standaloneSetup(taskEndpoint);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }

}
