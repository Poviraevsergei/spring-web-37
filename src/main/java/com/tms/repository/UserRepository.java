package com.tms.repository;

import com.tms.exception.UpdateException;
import com.tms.exception.UserCreateException;
import com.tms.model.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User findUserById(int id) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            user = session.find(User.class, id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return user;
    }

    public List<User> findAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createNativeQuery("SELECT * FROM users", User.class);
            users = query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return users;
    }

    public User saveUser(User user) throws UserCreateException {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.persist(user);
            session.getTransaction().commit();
            return user;
        } catch (Exception e){
            log.error(e.getMessage());
            throw new UserCreateException();
        }
    }

    public User updateUser(User user) throws UpdateException {
        try(Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.merge(user);
            session.getTransaction().commit();
            return user;
        } catch (Exception e){
            log.error(e.getMessage());
            throw new UpdateException();
        }
    }

    public void removeUserById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.remove(session.find(User.class, id));
            session.getTransaction().commit();
        } catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    //TODO: часовой пояс в БД ?
}
