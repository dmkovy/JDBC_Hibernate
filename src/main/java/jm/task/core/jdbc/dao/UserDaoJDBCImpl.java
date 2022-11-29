package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

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
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        //language=MySQL
        String sql = """
                DROP TABLE IF EXISTS users;
                """;
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        //language=MySQL
        String sql = """
                INSERT INTO users (name, last_name, age)
                VALUES (?, ?, ?);
                """;
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        String sql = """
                DELETE
                FROM users
                WHERE id=?;
                """;
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        //language=MySQL
        String sql = """
                SELECT id, name, last_name, age
                FROM users;
                """;
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge((byte) resultSet.getInt("age"));

                usersList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usersList;
    }

    public void cleanUsersTable() {
        String sql = """
                TRUNCATE TABLE users;
                """;
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
