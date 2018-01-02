package com.rabbitmq.test.withspring;

import com.rabbitmq.test.entity.StudentInSend;
import com.rabbitmq.test.entity1.Teacher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wuhuachuan on 17/1/12.
 */
@RestController
public class Producer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        rabbitTemplate.convertAndSend("testDirectExchange","key1", new StudentInSend("whc_student"));
        return  "sendok";
    }

    @RequestMapping(value = "/test1",method = RequestMethod.GET)
    public String test1(){
        rabbitTemplate.convertAndSend("testDirectExchange","key1", new Teacher("whc_teacher"));
        return  "sendok";
    }
}
