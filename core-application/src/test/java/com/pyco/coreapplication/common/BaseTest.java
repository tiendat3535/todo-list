package com.pyco.coreapplication.common;

import com.pyco.coreapplication.CoreApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ActiveProfiles(value = {"test"})
@RunWith(SpringRunner.class)
@DirtiesContext
@EnableWebMvc
@AutoConfigureJsonTesters
@SpringBootTest(classes = CoreApplication.class)
public abstract class BaseTest {
}
