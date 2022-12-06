package jm.task.core.hibernate.dao;

import jm.task.core.hibernate.model.User;
import jm.task.core.hibernate.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        //language=MySQL
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                id BIGINT(10) PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(45) NOT NULL ,
                last_name VARCHAR(45) NOT NULL ,
                age TINYINT(3) NOT NULL
                );
                """;
        try (SessionFactory sessionFactory = Util.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createSQLQuery(sql)
                    .addEntity(User.class)
                    .executeUpdate();

            session.getTransaction().commit();
        }

    }

    @Override
    public void dropUsersTable() {
        //language=MySQL
        String sql = """
                DROP TABLE IF EXISTS users;
                """;

        try (SessionFactory sessionFactory = Util.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createSQLQuery(sql)
                    .executeUpdate();

            session.getTransaction().commit();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (SessionFactory sessionFactory = Util.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .name(name)
                    .lastName(lastName)
                    .age(age)
                    .build();
            session.save(user);
            session.getTransaction().commit();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (SessionFactory sessionFactory = Util.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .id(id)
                    .build();

            if (user != null) {
                session.delete(user);
                System.out.println("Пользователь с id " + id + " удален из БД");
            }
            System.out.println("Пользователь с id " + id + " не существует");
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.buildSessionFactory().openSession()) {
            session.beginTransaction();
            List<User> usersList = session.createQuery("from User").list();
            session.getTransaction().commit();
            return usersList;
        }
    }


    @Override
    public void cleanUsersTable() {
        //language=MySQL
        String sql = """
                TRUNCATE TABLE users;
                """;
        try (SessionFactory sessionFactory = Util.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createSQLQuery(sql)
                    .executeUpdate();

            session.getTransaction().commit();
        }
    }
}
