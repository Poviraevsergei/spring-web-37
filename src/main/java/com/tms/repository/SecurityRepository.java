package com.tms.repository;

import com.tms.model.Security;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository
public class SecurityRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public SecurityRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Security findSecurityById(int id) {
        Security security = null;
        try (Session session = sessionFactory.openSession()) {
            security = session.find(Security.class, id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return security;
    }
}
