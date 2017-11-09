package io.bayberry.aloha;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SystemConfigFactoryTest {

    @Test
    public void should_load_system_config_from_yaml() {
        SystemConfig systemConfig = SystemConfigFactory.load(this.getClass().getClassLoader().getResourceAsStream("aloha-test.yml"));
        assertTrue(!systemConfig.getMessageBusConfigs().isEmpty());
    }
}