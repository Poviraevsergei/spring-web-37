package com.tms.repository;

import com.tms.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ProductRepository {
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public ProductRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Product findProductById(int id) {
        Product product = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            product = entityManager.find(Product.class, id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return product;
    }
}
