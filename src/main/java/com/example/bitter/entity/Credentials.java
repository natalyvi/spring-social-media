package com.example.bitter.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Credentials {

    @Column(unique = true)
    private String username;

    private String password;

}
