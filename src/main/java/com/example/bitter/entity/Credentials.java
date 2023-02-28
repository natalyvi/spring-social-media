package com.example.bitter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
public class Credentials {

    @Column(unique = true)
    private String username;

    private String password;

}
