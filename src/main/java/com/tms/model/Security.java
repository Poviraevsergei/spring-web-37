package com.tms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Instant created;
    private Instant updated;

    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @OneToOne
    private User user;
}
