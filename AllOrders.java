package equipment_managment;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AllOrders {
    // Method to view all pending orders
	public static void approveOrder(int orderId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Retrieve the order details (equipment_id and quantity_ordered)
            String query = "SELECT equipment_id, quantity_ordered FROM Orders WHERE order_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int equipmentId = resultSet.getInt("equipment_id");
                int quantityOrdered = resultSet.getInt("quantity_ordered");

                // Update the order's approval status to 'Approved'
                String updateOrderQuery = "UPDATE Orders SET approval_status = 'Approved' WHERE order_id = ?";
                PreparedStatement updateOrderStatement = connection.prepareStatement(updateOrderQuery);
                updateOrderStatement.setInt(1, orderId);
                updateOrderStatement.executeUpdate();

                // Deduct the quantity from the Equipment table
                String updateEquipmentQuery = "UPDATE Equipment SET quantity = quantity - ? WHERE equipment_id = ?";
                PreparedStatement updateEquipmentStatement = connection.prepareStatement(updateEquipmentQuery);
                updateEquipmentStatement.setInt(1, quantityOrdered);
                updateEquipmentStatement.setInt(2, equipmentId);
                updateEquipmentStatement.executeUpdate();

                System.out.println("Order " + orderId + " has been approved!");
            } else {
                System.out.println("Order ID not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	 // Method to reject an order
    public static void rejectOrder(int orderId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE Orders SET approval_status = 'Rejected' WHERE order_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Order " + orderId + " has been rejected!");
            } else {
                System.out.println("Order ID not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void viewOrders() {
    	Scanner scanner = new Scanner(System.in);
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT o.order_id, o.equipment_id, e.name AS equipment_name, o.quantity_ordered, o.order_date, o.room_requested, o.approval_status " +
                    "FROM Orders o " +
                    "JOIN Equipment e ON o.equipment_id = e.equipment_id " +
                    "WHERE o.approval_status = 'Pending'";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

             if (resultSet.next()) {
                System.out.println("Order ID: " + resultSet.getInt("order_id"));
                System.out.println("Equipment Name: " + resultSet.getString("equipment_name"));
                System.out.println("Quantity Ordered: " + resultSet.getInt("quantity_ordered"));
                System.out.println("Order Date: " + resultSet.getDate("order_date"));
                System.out.println("Room Requested: " + resultSet.getString("room_requested"));
                System.out.println("Approval Status: " + resultSet.getString("approval_status"));
                System.out.println("-----");

	            // Ask for user input to approve or reject an order
	            System.out.print("Enter action (approve/reject): ");
	            String action = scanner.next();
	            int orderId = resultSet.getInt("order_id");
	
	            if (action.equalsIgnoreCase("approve")) {
	                approveOrder(orderId);
	            } else if (action.equalsIgnoreCase("reject")) {
	                rejectOrder(orderId);
	            } else {
	                System.out.println("Invalid action. Please type 'approve' or 'reject'.");
	            }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    
   