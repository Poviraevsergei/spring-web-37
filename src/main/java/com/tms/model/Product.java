package com.tms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@ToString(exclude = {"users"})
@EqualsAndHashCode(exclude = {"users"})
@Entity(name = "product")
@Data
public class Product {
    @Id
    @SequenceGenerator(name = "seqProductId", sequenceName = "product_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "seqProductId")
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Instant created;
    private Instant updated;

    @JsonBackReference
    @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();
}
