package equipment_managment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Signup {
    public static void signupUser(Scanner scanner) {
        try (Connection connection = DatabaseConnection.getConnection()){

            System.out.println("Enter name:");
            String name = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();
            System.out.println("Enter role (member/manager):");
            String role = scanner.nextLine();

            String query = "INSERT INTO user1 (name, password, role) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, password);
            statement.setString(3, role);
            statement.executeUpdate();

            System.out.println("Signup successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

