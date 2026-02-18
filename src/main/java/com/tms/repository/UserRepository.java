package com.tms.repository;

import com.tms.exception.UpdateException;
import com.tms.exception.UserCreateException;
import com.tms.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class UserRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public UserRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public User findUserById(int id) {
        User user = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            user = entityManager.find(User.class, id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return user;
    }

    public List<User> findAllUsers() {
        List<User> users = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Query query = entityManager.createNativeQuery("SELECT * FROM users", User.class);
            users = query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return users;
    }

    public User saveUser(User user) throws UserCreateException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e){
            log.error(e.getMessage());
            throw new UserCreateException();
        }
    }

    public User updateUser(User user) throws UpdateException {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e){
            log.error(e.getMessage());
            throw new UpdateException();
        }
    }

    public void removeUserById(Integer id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(User.class, id));
            entityManager.getTransaction().commit();
        } catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    //TODO: часовой пояс в БД ?
}
