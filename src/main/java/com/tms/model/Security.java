package com.tms.model;

import lombok.Data;

import java.time.Instant;

@Data
public class Security {
    private Integer id;
    private String username;
    private String password;
    private Role role;
    private Integer userId;
    private Instant created;
    private Instant updated;
}
