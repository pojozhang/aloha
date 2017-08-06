package io.bayberry.aloha.mqtt;

import io.bayberry.aloha.annotation.Consume;
import org.awaitility.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.awaitility.Awaitility.await;

public class MqttMessageBusTest {

    private static CountDownLatch countDownLatch;
    private static MqttMessageBusOptions options;

    static {
        options = new MqttMessageBusOptions();
        options.setCleanSession(true);
        options.setServerUri("tcp://127.0.0.1:1883");
        options.setQos(2);
    }

    private MqttMessageBus mqttMessageBus;

    @Before
    public void setUp() {
        mqttMessageBus = new MqttMessageBus(options);
        mqttMessageBus.register(new MqttSubscribers());
        mqttMessageBus.start();
    }

    @After
    public void tearDown() {
        mqttMessageBus.stop();
    }

    @Test
    public void the_subscriber_should_be_called_asynchronously_after_single_message_is_post() {
        this.countDownLatch = new CountDownLatch(1);
        this.mqttMessageBus.publish(new MqttMessage());
        await().atMost(Duration.TWO_SECONDS).until(() -> this.countDownLatch.getCount() == 0);
    }

    public static class MqttSubscribers {

        @Consume
        public void onReceive(MqttMessage message) {
            countDownLatch.countDown();
        }
    }

    public static class MqttMessage {

    }
}