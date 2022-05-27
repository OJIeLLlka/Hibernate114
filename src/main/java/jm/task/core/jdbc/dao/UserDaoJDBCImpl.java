package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = new Util().getConnection();
    private int USER_COUNT;


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String SQL =
                "CREATE TABLE IF NOT EXISTS users (id BIGINT(20) PRIMARY KEY, name VARCHAR(20), lastname VARCHAR(20), age INT)";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String SQL =
                "DROP TABLE IF EXISTS users";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement pS =
                    connection.prepareStatement("INSERT INTO users VALUES(?, ?, ?, ?)");

            pS.setLong(1, ++USER_COUNT);
            pS.setString(2, name);
            pS.setString(3, lastName);
            pS.setByte(4, age);

            pS.executeUpdate();
            System.out.println("User " + name + " is added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement pS =
                    connection.prepareStatement("DELETE FROM users WHERE Id=?");

            pS.setLong(1, id);

            pS.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String SQL = "TRUNCATE TABLE users";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



