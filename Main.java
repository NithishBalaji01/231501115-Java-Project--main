package equipment_managment;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int choice = 0;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Signup");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Signup.signupUser(scanner);
                    break;
                case 2:
                	String role = Login.loginUser(scanner); // Fetch the role
                    if (role != null) {
                        if (role.equalsIgnoreCase("member")) {
                            EquipmentSearch.searchEquipment(scanner);
                        } else if (role.equalsIgnoreCase("manager")) {
                            AllOrders.viewOrders();
                        } else {
                            System.out.println("Unknown role. Please contact the administrator.");
                        }
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }
}
