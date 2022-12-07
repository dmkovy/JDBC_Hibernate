package jm.task.core.hibernate.util;

import jm.task.core.hibernate.model.User;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/users_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DIALECT = "org.hibernate.dialect.MySQLDialect";
    private static SessionFactory sessionFactory = null;

    public static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.url", URL)
                    .setProperty("hibernate.connection.username", USERNAME)
                    .setProperty("hibernate.connection.password", PASSWORD)
                    .setProperty("hibernate.connection.driver_class", DRIVER)
                    .setProperty("hibernate.dialect", DIALECT)
//                    .setProperty("hibernate.show_sql", "true")
//                    .setProperty("hibernate.format_sql", "true")
                    .setProperty("hibernate.current_session_context_class", "thread")
//                    .setProperty("hibernate.hbm2ddl.auto", "create-drop")
                    .addAnnotatedClass(User.class);
            return sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
