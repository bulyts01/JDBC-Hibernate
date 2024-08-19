package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory factory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("""
                            CREATE TABLE IF NOT EXISTS userss (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(128) NOT NULL,
                            last_name VARCHAR(128) NOT NULL,
                            age INT NOT NULL)
                            """)
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table 'userss' created");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS userss").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table 'userss' droped");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User added");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            //Удаление через создание объекта класса:
            User user = session.get(User.class, id);
            session.delete(user);
            System.out.println("User removed: " + user);

            //Удаление через запрос HQL: - нужно разобраться
//            Long idNew = id;
//            session.createQuery("delete User where id=idNew").executeUpdate();
//            System.out.println("User removed");

            session.getTransaction().commit();

        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            users = session.createQuery("FROM User").getResultList();
            session.getTransaction().commit();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("All users deleted");
        }
    }
}
