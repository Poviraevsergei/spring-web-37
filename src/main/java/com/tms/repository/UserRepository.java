package com.tms.repository;

import com.tms.exception.UpdateException;
import com.tms.exception.UserCreateException;
import com.tms.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
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
            //Создание CriteriaBuilder
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);

            //Добавляем ограничение
            criteriaQuery.select(root).where(cb.equal(root.get("id"), id));
            user = session.createQuery(criteriaQuery).getSingleResultOrNull();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return user;
    }

    public List<User> findAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            //Создание CriteriaBuilder
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);

            //Добавляем ограничение
            criteriaQuery.select(root);
            users = session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return users;
    }

    // Не предназначен для добавления строк
    public User saveUser(User user) throws UserCreateException {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.persist(user);
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UserCreateException();
        }
    }

    public User updateUser(User user) throws UpdateException {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaUpdate<User> criteriaUpdate = cb.createCriteriaUpdate(User.class);
            CriteriaQuery<User> criteriaGet = cb.createQuery(User.class);
            Root<User> rootUpdate = criteriaUpdate.from(User.class);
            Root<User> rootGet = criteriaGet.from(User.class);

            //Устанавливает поля
            criteriaUpdate.set(rootUpdate.get("firstName"), user.getFirstName());
            criteriaUpdate.set(rootUpdate.get("lastName"), user.getLastName());
            criteriaUpdate.set(rootUpdate.get("email"), user.getEmail());
            criteriaUpdate.set(rootUpdate.get("age"), user.getAge());
            criteriaUpdate.set(rootUpdate.get("updated"), user.getUpdated());

            //Добавляем ограничение
            criteriaUpdate.where(cb.equal(rootUpdate.get("id"), user.getId()));

            //Выполняем запрос
            session.getTransaction().begin();
            MutationQuery query = session.createMutationQuery(criteriaUpdate);
            query.executeUpdate();
            session.getTransaction().commit();

            criteriaGet.select(rootGet).where(cb.equal(rootGet.get("id"), user.getId()));
            return session.createQuery(criteriaGet).getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UpdateException();
        }
    }

    public void removeUserById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaDelete<User> criteriaQuery = cb.createCriteriaDelete(User.class);
            Root<User> root = criteriaQuery.from(User.class);

            criteriaQuery.where(cb.equal(root.get("id"), id));

            MutationQuery query = session.createMutationQuery(criteriaQuery);

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
