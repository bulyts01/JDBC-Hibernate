package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements UserService {
    public void createUsersTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                name VARCHAR(128) NOT NULL,
                last_name VARCHAR(128) NOT NULL,
                age INT NOT NULL)                
                """;
        try (var connection = Util.open();
             var statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Таблица users создана!");
        }
    }

    public void dropUsersTable() throws SQLException {
        String sql = """
                DROP TABLE IF EXISTS users
                """;
        try (var connection = Util.open();
             var statement = connection.createStatement()) {
            System.out.println("Таблица users дропнута!");
            statement.execute(sql);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sql = """
                INSERT INTO users (name, last_name, age)
                VALUES (?, ?, ?)
                """;
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setLong(3, age);

            preparedStatement.executeUpdate();
            System.out.println("Пользователь с именем " + name + " добавлен в базу данных");
        }
    }

    public void removeUserById(long id) throws SQLException {
        String sql = """
                DELETE 
                FROM users
                WHERE id = ?
                """;
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();


//            var resultSet = preparedStatement.executeQuery();
//            System.out.println(resultSet.getLong("id") + " " + resultSet.getString("name"));

        }

    }

    public List<User> getAllUsers() throws SQLException {
        String sql = """
                SELECT * FROM users
                """;
        List<User> result = new ArrayList<>();
        try (var connection = Util.open();
        var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result.add(new User(resultSet.getString("name"),
                        resultSet.getString("last_name"), resultSet.getByte("age")));
            }
        }
        return result;
    }

    public void cleanUsersTable() throws SQLException {
        String sql = """
                DELETE FROM users
                """;
        try( var connection = Util.open();
        var prepareStatmenet = connection.prepareStatement(sql)) {
            prepareStatmenet.executeUpdate();

        }

    }
}
