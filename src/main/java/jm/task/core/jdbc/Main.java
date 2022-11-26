package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<User> usersList;
        UserDao userDao = new UserDaoHibernateImpl();

        Util.getSessionFactory();
        try {
            userDao.createUsersTable();
            userDao.saveUser("Name_1", "lastName_1", (byte) 10);
            userDao.saveUser("Name_2", "lastName_2", (byte) 20);
            userDao.saveUser("Name_3", "lastName_3", (byte) 30);
            userDao.saveUser("Name_4", "lastName_4", (byte) 40);
            usersList = userDao.getAllUsers();
            for (User user : usersList)
                System.out.println(user);
            userDao.removeUserById(3);
            userDao.cleanUsersTable();
            userDao.dropUsersTable();
        } finally {
            Util.closeSessionFactory();
        }
    }
}
