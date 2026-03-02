package com.tms.repository;

import com.tms.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ProductRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public ProductRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Product findProductById(int id) {
        Product product = null;
        try (Session session = sessionFactory.openSession()) {
            product = session.find(Product.class, id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return product;
    }
}
