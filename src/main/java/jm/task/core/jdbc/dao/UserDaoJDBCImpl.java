package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.exception.DaoException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;
    public static final String SAVE_USER_SQL = """
            INSERT INTO users (name, last_name, age)
            VALUES (?, ?, ?)
            """;
    public static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS users
            """;
    public static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS users (
            id SERIAL PRIMARY KEY,
            name VARCHAR(128) NOT NULL,
            last_name VARCHAR(128) NOT NULL,
            age INT NOT NULL)                
            """;
    public static final String GET_ALL_USER = """            
            SELECT * FROM users
            """;
    private static final String DELETE_ALL_USERS_SQL = """
            TRUNCATE TABLE users
            """;
    private static final String UPDATE_SQL = """
            UPDATE users
            SET name = ?,
            last_name = ?,
            age = ?
            """;

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (var connection = Util.open();
             var statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE);
            System.out.println("Таблица users создана!");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void dropUsersTable() {
        try (var connection = Util.open();
             var statement = connection.createStatement()) {
            statement.execute(DROP_TABLE_SQL);
            System.out.println("Таблица users дропнута!");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(SAVE_USER_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User added");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void removeUserById(long id) {
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(GET_ALL_USER)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                result.add(user);
            }
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
        return result;
    }

    public void cleanUsersTable() {
        try (var connection = Util.open();
             var statement = connection.prepareStatement(DELETE_ALL_USERS_SQL)) {
            statement.executeUpdate();
            System.out.println("Все записи удалены!");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
