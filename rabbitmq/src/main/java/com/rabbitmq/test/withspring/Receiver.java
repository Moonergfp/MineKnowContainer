package com.rabbitmq.test.withspring;

import com.rabbitmq.test.entity1.StudentInReceive;
import com.rabbitmq.test.entity1.Teacher;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by wuhuachuan on 17/1/12.
 */
@RabbitListener(
        containerFactory = "simpleRabbitListenerContainerFactory",
        bindings = @QueueBinding(
                value = @Queue(value = "testDirectQueue1",durable = "true"),
                exchange = @Exchange(value = "testDirectExchange",type = ExchangeTypes.DIRECT),
                key = "key1")
)
@Component
public class Receiver {
    @RabbitHandler
    public void receiveMessage(StudentInReceive student) {
        System.out.println("Received <" + student + ">");
    }

    @RabbitHandler
    public void receiveMessage(Teacher teacher) {
        System.out.println("Received <" + teacher + ">");
    }
}
