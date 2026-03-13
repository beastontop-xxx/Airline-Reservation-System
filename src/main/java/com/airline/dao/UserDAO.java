package com.airline.dao;

import com.airline.model.User;
import com.airline.util.DBConnection;

import java.sql.*;

/**
 * Data Access Object for the `users` table.
 */
public class UserDAO {

    /**
     * Inserts a new user record into the database.
     *
     * @param user the User to persist
     * @return true if insert succeeded
     */
    public boolean registerUser(User user) {
        final String SQL = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());   // store plain-text; hash in production
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates login credentials.
     *
     * @param email    the user's email
     * @param password the user's password
     * @return the matching User object, or null if not found
     */
    public User validateUser(String email, String password) {
        final String SQL = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setName(rs.getString("name"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("password"));
                    return u;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
