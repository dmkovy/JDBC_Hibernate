package jm.task.core.hibernate.util;

import jm.task.core.hibernate.model.User;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class Util {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(User.class)
                .configure();
        return configuration.buildSessionFactory();
    }


}
