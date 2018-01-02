package com.rabbitmq.test.entity1;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by wuhuachuan on 17/1/12.
 */

@Data
@ToString
@NoArgsConstructor
public class StudentInReceive {

    private String name;

    public StudentInReceive(final String name){
        this.name = name;
    }
}
