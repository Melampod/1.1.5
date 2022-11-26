package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        dropUsersTable();
        try (Connection connection = Util.getConnection();
             Statement createTable = connection.createStatement()) {
            createTable.executeUpdate("CREATE TABLE `my_db`.`users` (\n" +
                    "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastName` VARCHAR(45) NOT NULL,\n" +
                    "  `age` TINYINT(3) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        try (Connection connection = Util.getConnection();
             Statement dropUsersTable = connection.createStatement()) {
            dropUsersTable.executeUpdate("DROP TABLE IF EXISTS `my_db`.`users`;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement saveUser = connection.prepareStatement("INSERT into users(name, lastName, age) values(?,?,?)")) {
            saveUser.setString(1, name);
            saveUser.setString(2, lastName);
            saveUser.setByte(3, age);
            saveUser.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.printf("User с именем – %s добавлен в базу данных\n", name);
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement removeUserById = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            removeUserById.setLong(1, id);
            removeUserById.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement getAllUsers = connection.prepareStatement("SELECT * FROM users");
             ResultSet resultSet = getAllUsers.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement removeUserById = connection.prepareStatement("TRUNCATE TABLE users;")) {
            removeUserById.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

