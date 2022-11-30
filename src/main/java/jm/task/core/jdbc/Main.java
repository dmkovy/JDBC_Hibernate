package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        // Создание таблицы User(ов)
        userService.createUsersTable();
        System.out.println("Таблица создана");
        System.out.println("-----------");

        // Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль (User с именем – name добавлен в базу данных)
        User user1 = new User("Dak", "Prescott", (byte) 29);
        User user2 = new User("Ezekiel", "Elliott", (byte) 27);
        User user3 = new User("Noah", "Brown", (byte) 25);
        User user4 = new User("Dalton", "Schultz", (byte) 26);

        List<User> userListToDB = new ArrayList<>();

        userListToDB.add(user1);
        userListToDB.add(user2);
        userListToDB.add(user3);
        userListToDB.add(user4);

        for (User user : userListToDB) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.println("User с именем " + user.getName() + " добавлен в базу данных");
        }
        System.out.println("-----------");

        // Получение всех User из базы и вывод в консоль (должен быть переопределен toString в классе User)
        List<User> userListFromDB = userService.getAllUsers();
        for (User user : userListFromDB) {
            System.out.println(user);
        }
        System.out.println("Все пользователи из БД получены");
        System.out.println("-----------");

        // Очистка таблицы User(ов)
        userService.cleanUsersTable();
        System.out.println("Таблица очищена");
        System.out.println("-----------");

        // Удаление таблицы
        userService.dropUsersTable();
        System.out.println("Таблица удалена");
        System.out.println("-----------");

        Util.close();
    }
}
