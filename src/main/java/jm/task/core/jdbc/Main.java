package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.hibernate.SessionFactory;


import javax.security.auth.login.Configuration;
import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь

        UserServiceImpl userService = new UserServiceImpl();

//        userService.createUsersTable();
//        userService.dropUsersTable();
//        userService.cleanUsersTable();
        userService.removeUserById(9);

// Добавление юзеров через JDBC
//        User user1 = new User("Semen","Bulytov",(byte) 36);
//        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
//        User user2 = new User("Aleksei","Kupriynov",(byte) 30);
//        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
//        User user3 = new User("Sevak","Martirosyan",(byte) 31);
//        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());

        userService.getAllUsers().forEach(System.out::println);


    }
}
