package equipment_managment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class EquipmentSearch {
    public static void searchEquipment(Scanner scanner) {
        try (Connection connection = DatabaseConnection.getConnection()){

            System.out.println("Enter equipment name to search:");
            String name = scanner.nextLine();

            String query = "SELECT * FROM Equipment WHERE name LIKE ? AND quantity > threshold";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + name + "%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int equipmentId = resultSet.getInt("equipment_id");
                String equipmentName = resultSet.getString("name");
                int quantity = resultSet.getInt("quantity");
                int threshold = resultSet.getInt("threshold");
                
                System.out.println("Equipment ID: " + equipmentId);
                System.out.println("Name: " + equipmentName);
                System.out.println("Quantity: " + quantity);
                System.out.println("Threshold: " + threshold);
                System.out.println("-----");
                
                System.out.print("Enter quantity to order: ");
                int quantityOrdered = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                System.out.print("Enter room requested (if applicable): ");
                String roomRequested = scanner.nextLine();
                
                System.out.print("Enter requested by (user ID or name): ");
                String requestedBy = scanner.nextLine();

                

                // Insert the equipment into the Orders table
                String insertQuery = "INSERT INTO Orders (equipment_id, quantity_ordered, room_requested, approval_status, order_date, requested_by) " +
                        "VALUES (?, ?, ?, 'Pending', CURRENT_DATE, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, equipmentId);
                insertStatement.setInt(2, quantityOrdered);
                insertStatement.setString(3, roomRequested);
                insertStatement.setString(4, requestedBy);
                insertStatement.executeUpdate();

                System.out.println("Equipment added to the Orders table with Pending status.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

