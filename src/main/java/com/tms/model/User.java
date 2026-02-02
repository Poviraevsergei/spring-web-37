package com.tms.model;

import lombok.Data;

import java.time.Instant;

@Data
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private Instant created;
    private Instant updated;
    private boolean active;
}
