package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь

        UserServiceImpl userService = new UserServiceImpl();

//        userService.saveUser("Semen", "Bulytov", (byte) 36);
//        userService.saveUser("Aleksei", "Kupriynov", (byte) 30);
//        userService.saveUser("Sevak", "Martirosyan", (byte) 31);

//        userService.createUsersTable();
//        userService.dropUsersTable();
//        userService.removeUserById(7);
//        userService.cleanUsersTable();

        userService.getAllUsers().forEach(System.out::println);
    }
}
