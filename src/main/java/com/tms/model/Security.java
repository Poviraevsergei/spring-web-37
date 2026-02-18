package com.tms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

import java.time.Instant;

@Entity(name = "security")
@Data
public class Security {
    @Id
    @SequenceGenerator(name = "seqSecurityId", sequenceName = "security_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "seqSecurityId")
    private Integer id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "user_id")
    private Integer userId;
    private Instant created;
    private Instant updated;
}
