package io.bayberry.aloha.test.spring;

import io.bayberry.aloha.test.BaseTestCase;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public abstract class BaseSpringTest extends BaseTestCase {

    @Autowired
    protected ApplicationContext applicationContext;
}
