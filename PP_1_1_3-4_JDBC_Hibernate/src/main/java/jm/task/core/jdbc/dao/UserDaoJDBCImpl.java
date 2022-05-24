package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection;

    {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String table = "CREATE TABLE NewSchema1.users (" +
                " id INTEGER not null AUTO_INCREMENT, " +
                " name VARCHAR(255), " +
                " lastName VARCHAR(255)," +
                " age INTEGER, " + "PRIMARY KEY(id))";
        try (Statement st = connection.createStatement()) {
            st.executeUpdate(table);
            System.out.println("table created");
        } catch(SQLException e) {
        }
    }

    public void dropUsersTable() {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate("DROP TABLE NewSchema1.users");
        } catch(SQLException e) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pst = connection.prepareStatement("INSERT INTO NewSchema1.users (name, lastName, age) VALUES ( '"
                + name + "', " + "'"
                + lastName + "'," + "'"
                + age + "');")) {
            pst.executeUpdate();
            System.out.println("User с именем " + name +" добавлен в базу данных");
        } catch(SQLException e) {
            e.getCause();
            //e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String statement = "DELETE FROM NewSchema1.users WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement("DELETE FROM NewSchema1.users WHERE id = ?")) {
            pst.executeUpdate();
        } catch(SQLException e) {
        }
    }



    public List<User> getAllUsers() {
        List <User> listOfUsers = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet res = st.executeQuery("SELECT * FROM NewSchema1.users");
            while(res.next()) {
                User user = new User();
                user.setId(res.getLong("id"));
                user.setName(res.getString("name"));
                user.setLastName(res.getString("lastName"));
                user.setAge(res.getByte("age"));
                listOfUsers.add(user);
                System.out.println(user.toString());
            }
        } catch(SQLException e) {
        }
        return listOfUsers;
    }

    public void cleanUsersTable() {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate("DELETE FROM users");
        } catch(SQLException e) {
        }
    }
}
