package com.rabbitmq.test.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by wuhuachuan on 17/1/12.
 */

@Data
@ToString
@NoArgsConstructor
public class StudentInSend {

    private String name;

    public StudentInSend(final String name){
        this.name = name;
    }
}
