package jm.task.core.hibernate.dao;

import jm.task.core.hibernate.model.User;
import jm.task.core.hibernate.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = Util.buildSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        //language=MySQL
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                id BIGINT(10) PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(45) NOT NULL ,
                last_name VARCHAR(45) NOT NULL ,
                age TINYINT(3) NOT NULL
                );
                """;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery(sql)
                    .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        //language=MySQL
        String sql = """
                DROP TABLE IF EXISTS users;
                """;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery(sql)
                    .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            User user = User.builder()
                    .name(name)
                    .lastName(lastName)
                    .age(age)
                    .build();
            session.save(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            User user = User.builder()
                    .id(id)
                    .build();
            session.delete(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            List<User> usersList = session.createQuery("from User").getResultList();

            transaction.commit();

            return usersList;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }


    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        //language=MySQL
        String sql = """
                TRUNCATE TABLE users;
                """;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery(sql)
                    .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
