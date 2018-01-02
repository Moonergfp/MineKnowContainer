package com.springoauth2.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by wuhuachuan on 16/5/11.
 */

@Entity
@Table(name = "tb_user")
@Data
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private boolean enable = true;
}
