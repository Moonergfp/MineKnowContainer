package com.rabbitmq.test.withspring;

/**
 * Created by wuhuachuan on 17/1/12.
 */

import com.rabbitmq.test.entity.StudentInSend;
import com.rabbitmq.test.entity1.StudentInReceive;
import com.rabbitmq.test.entity1.Teacher;
import lombok.Data;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuhuachuan on 16/3/28.
 */
@EnableRabbit
@Configuration
@Data
public class RabbitMQConfig {

    @Bean(name = "classMapper")
    public ClassMapper createClassMapper(){
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setDefaultType(StudentInSend.class);

        Map<String,Class<?>> map = new HashMap<String, Class<?>>();
        map.put("Student",StudentInSend.class);
        map.put("Teacher",Teacher.class);
        classMapper.setIdClassMapping(map);

        return classMapper;
    }

    @Bean(name = "studentMessageConverter_send")
    public MessageConverter StudentMessageConverter_send(@Qualifier(value = "classMapper") ClassMapper classMapper) {

        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
        messageConverter.setClassMapper(classMapper);

        return messageConverter;
    }

    @Bean(name = "studentMessageConverter_receive")
    public MessageConverter StudentMessageConverter_receive() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setDefaultType(StudentInReceive.class);

        Map<String,Class<?>> map = new HashMap<String, Class<?>>();
        map.put("Student",StudentInReceive.class);
        map.put("Teacher",Teacher.class);
        classMapper.setIdClassMapping(map);

        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
        messageConverter.setClassMapper(classMapper);

        return messageConverter;
    }

    @Bean(name = "simpleRabbitListenerContainerFactory")
    SimpleRabbitListenerContainerFactory createSimpleRabbitListenerContainerFactory(
            @Qualifier(value = "studentMessageConverter_receive") MessageConverter studentMessageConverter,
            ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(studentMessageConverter);

        return factory;
    }

    @Bean(name = "rabbitTemplate")
    RabbitTemplate rabbitTemplate(
            @Qualifier(value = "studentMessageConverter_send") MessageConverter studentMessageConverter,
            ConnectionFactory connectionFactory) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(studentMessageConverter);

        return template;
    }
}
