package com.tms.repository;

import com.tms.exception.UpdateException;
import com.tms.exception.UserCreateException;
import com.tms.model.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.MutationQuery;
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
            Query<User> userQuery = session.createQuery("from User where id = :id", User.class);
            userQuery.setParameter("id", id);
            return userQuery.getSingleResultOrNull();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return user;
    }

    public List<User> findAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM User", User.class);
            users = query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return users;
    }

    // Не предназначен для добавления строк(только из других таблиц)
    public User saveUser(User user) throws UserCreateException {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            MutationQuery query = session.createQuery("INSERT INTO User(firstName, lastName) SELECT firstName, lastName FROM UserSecond");
            query.executeUpdate();
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UserCreateException();
        }
    }

    public User updateUser(User user) throws UpdateException {
        try (Session session = sessionFactory.openSession()) {
            MutationQuery query = session.createMutationQuery("UPDATE User SET firstName =:firstName, " +
                    "lastName =:lastName, email=:email, age=:age, updated=:updated WHERE id =:id");
            query.setParameter("firstName", user.getFirstName());
            query.setParameter("lastName", user.getLastName());
            query.setParameter("email", user.getEmail());
            query.setParameter("age", user.getAge());
            query.setParameter("updated", user.getUpdated());
            query.setParameter("id", user.getId());
            session.getTransaction().begin();
            query.executeUpdate();
            session.getTransaction().commit();

            Query<User> queryFindUser = session.createQuery("FROM User WHERE id =:id", User.class);
            queryFindUser.setParameter("id", user.getId());
            return queryFindUser.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UpdateException();
        }
    }

    public void removeUserById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            MutationQuery query = session.createMutationQuery("DELETE FROM User WHERE id =:id");
            query.setParameter("id", id);
            session.getTransaction().begin();
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    //TODO: часовой пояс в БД ?
}
