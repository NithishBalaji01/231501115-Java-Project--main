package equipment_managment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {
    public static String loginUser(String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM user1 WHERE name = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("role"); // Return role if login successful
            } else {
                return null; // Invalid credentials
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
