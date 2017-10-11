package io.bayberry.aloha.spring.rabbit;

import io.bayberry.aloha.exception.DeserializationException;
import io.bayberry.aloha.support.serializer.Serializer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

public class RabbitMessageConverterBridge extends AbstractMessageConverter {

    private static final String CLASS_NAME = "@class.name";
    private Serializer<Object, byte[]> serializer;

    public RabbitMessageConverterBridge(Serializer<Object, byte[]> serializer) {
        this.serializer = serializer;
    }

    @Override
    protected Message createMessage(Object object, MessageProperties messageProperties) {
        messageProperties.getHeaders().put(CLASS_NAME, object.getClass().getName());
        return new Message(this.serializer.serialize(object), messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        String targetType = String.valueOf(message.getMessageProperties().getHeaders().get(CLASS_NAME));
        try {
            return this.serializer.deserialize(message.getBody(), (Class<Object>) Class.forName(targetType));
        } catch (ClassNotFoundException e) {
            throw new DeserializationException(message, targetType, e);
        }
    }
}
