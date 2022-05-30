package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;
    private static int userCount;

    public UserDaoJDBCImpl() {
        connection = getConnection();
    }

    @Override
    public void createUsersTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS users (id bigint(20) primary key, name varchar(20), lastname varchar(20), age int)";
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("\nUsers table is created.\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql =
                "DROP TABLE IF EXISTS users";
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("\nUsers table is deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users values(?, ?, ?, ?)";
        try (PreparedStatement pS = connection.prepareStatement(sql)) {

            pS.setLong(1, ++userCount);
            pS.setString(2, name);
            pS.setString(3, lastName);
            pS.setByte(4, age);

            pS.executeUpdate();
            connection.commit();
            System.out.println("User " + name + " is added to list.");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE Id=?";
        try (PreparedStatement pS = connection.prepareStatement(sql)) {

            pS.setLong(1, id);

            pS.executeUpdate();
            System.out.println("\nUser № " + id + " is deleted.");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        String sql = "SELECT * FROM users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

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
        System.out.println("\nList of all users:");
        return list;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("\nUsers list is cleaned up.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



