package com.pyco.coreapplication;

import com.pyco.coreapplication.common.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles(value = {"test"})
@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest(classes = CoreApplication.class)
public class CoreApplicationTests extends BaseTest {

	@Test
	public void contextLoads() {
	}

}
