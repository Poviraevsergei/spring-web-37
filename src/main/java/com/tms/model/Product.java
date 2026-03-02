package com.tms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

import java.time.Instant;

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
}
