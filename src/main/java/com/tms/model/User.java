package com.tms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "users")
@Data
@NamedQueries(value = {
        @NamedQuery(name = "getAllUsers", query = "FROM User")
})
public class User {
    @Id
    @SequenceGenerator(name = "seqUserId", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "seqUserId")
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private Integer age;
    private Instant created;
    private Instant updated;
}
