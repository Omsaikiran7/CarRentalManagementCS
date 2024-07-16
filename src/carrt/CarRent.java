package carrt;

import java.sql.*;
import java.util.Scanner;

public class CarRent {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/car_rental";
    private static final String JDBC_USER = "root";  
    private static final String JDBC_PASSWORD = "Omsai@743";  

    private Connection connection;

    public CarRent() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    public void addCar(String make, String model, int year, double dailyRate) throws SQLException {
        String query = "INSERT INTO Car (make, model, year, daily_rate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, make);
            stmt.setString(2, model);
            stmt.setInt(3, year);
            stmt.setDouble(4, dailyRate);
            stmt.executeUpdate();
            System.out.println("Car added successfully.");
        }
    }

    public void viewCar(int carId) throws SQLException {
        String query = "SELECT * FROM Car WHERE car_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, carId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Car ID: " + rs.getInt("car_id"));
                    System.out.println("Make: " + rs.getString("make"));
                    System.out.println("Model: " + rs.getString("model"));
                    System.out.println("Year: " + rs.getInt("year"));
                    System.out.println("Daily Rate: " + rs.getDouble("daily_rate"));
                } else {
                    System.out.println("Car not found.");
                }
            }
        }
    }

    public void updateCar(int carId, String make, String model, int year, double dailyRate) throws SQLException {
        String query = "UPDATE Car SET make = ?, model = ?, year = ?, daily_rate = ? WHERE car_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, make);
            stmt.setString(2, model);
            stmt.setInt(3, year);
            stmt.setDouble(4, dailyRate);
            stmt.setInt(5, carId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Car updated successfully.");
            } else {
                System.out.println("Car not found.");
            }
        }
    }

    public void deleteCar(int carId) throws SQLException {
        String query = "DELETE FROM Car WHERE car_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, carId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Car deleted successfully.");
            } else {
                System.out.println("Car not found.");
            }
        }
    }

    public void registerCustomer(String name, String email, String phoneNumber, String address) throws SQLException {
        String query = "INSERT INTO Customer (name, email, phone_number, address) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phoneNumber);
            stmt.setString(4, address);
            stmt.executeUpdate();
            System.out.println("Customer registered successfully.");
        }
    }

    public void viewCustomer(int customerId) throws SQLException {
        String query = "SELECT * FROM Customer WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Customer ID: " + rs.getInt("customer_id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Email: " + rs.getString("email"));
                    System.out.println("Phone Number: " + rs.getString("phone_number"));
                    System.out.println("Address: " + rs.getString("address"));
                } else {
                    System.out.println("Customer not found.");
                }
            }
        }
    }

    public void updateCustomer(int customerId, String name, String email, String phoneNumber, String address) throws SQLException {
        String query = "UPDATE Customer SET name = ?, email = ?, phone_number = ?, address = ? WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phoneNumber);
            stmt.setString(4, address);
            stmt.setInt(5, customerId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer updated successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        }
    }

    public void deleteCustomer(int customerId) throws SQLException {
        String query = "DELETE FROM Customer WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        }
    }

    public void rentCar(int carId, int customerId, Date rentalStartDate, Date rentalEndDate) throws SQLException {
        String rentalQuery = "INSERT INTO Rental (car_id, customer_id, rental_start_date, rental_end_date, total_charge) VALUES (?, ?, ?, ?, ?)";
        String carQuery = "SELECT daily_rate FROM Car WHERE car_id = ?";
        try (PreparedStatement carStmt = connection.prepareStatement(carQuery)) {
            carStmt.setInt(1, carId);
            try (ResultSet rs = carStmt.executeQuery()) {
                if (rs.next()) {
                    double dailyRate = rs.getDouble("daily_rate");
                    long rentalDuration = (rentalEndDate.getTime() - rentalStartDate.getTime()) / (1000 * 60 * 60 * 24);
                    double totalCharge = dailyRate * rentalDuration;

                    try (PreparedStatement rentalStmt = connection.prepareStatement(rentalQuery)) {
                        rentalStmt.setInt(1, carId);
                        rentalStmt.setInt(2, customerId);
                        rentalStmt.setDate(3, rentalStartDate);
                        rentalStmt.setDate(4, rentalEndDate);
                        rentalStmt.setDouble(5, totalCharge);
                        rentalStmt.executeUpdate();
                        System.out.println("Car rented successfully. Total charge: " + totalCharge);
                    }
                } else {
                    System.out.println("Car not found.");
                }
            }
        }
    }

    public void returnCar(int rentalId) throws SQLException {
        String query = "DELETE FROM Rental WHERE rental_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, rentalId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Car returned successfully.");
            } else {
                System.out.println("Rental not found.");
            }
        }
    }

    public void viewRental(int rentalId) throws SQLException {
        String query = "SELECT * FROM Rental WHERE rental_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, rentalId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Rental ID: " + rs.getInt("rental_id"));
                    System.out.println("Car ID: " + rs.getInt("car_id"));
                    System.out.println("Customer ID: " + rs.getInt("customer_id"));
                    System.out.println("Rental Start Date: " + rs.getDate("rental_start_date"));
                    System.out.println("Rental End Date: " + rs.getDate("rental_end_date"));
                    System.out.println("Total Charge: " + rs.getDouble("total_charge"));
                } else {
                    System.out.println("Rental not found.");
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
        	Scanner scanner = new Scanner(System.in)
            CarRent system = new CarRent();
            while (true) {
                System.out.println("\nCar Rental Management System");
                System.out.println("1. Add a new car");
                System.out.println("2. View car details");
                System.out.println("3. Update car information");
                System.out.println("4. Delete a car");
                System.out.println("5. Register a new customer");
                System.out.println("6. View customer details");
                System.out.println("7. Update customer information");
                System.out.println("8. Delete a customer");
                System.out.println("9. Rent a car to a customer");
                System.out.println("10. Return a rented car");
                System.out.println("11. View rental details");
                System.out.println("12. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.print("Enter make: ");
                        String make = scanner.next();
                        System.out.print("Enter model: ");
                        String model = scanner.next();
                        System.out.print("Enter year: ");
                        int year = scanner.nextInt();
                        System.out.print("Enter daily rate: ");
                        double dailyRate = scanner.nextDouble();
                        system.addCar(make, model, year, dailyRate);
                        break;
                    case 2:
                        System.out.print("Enter car ID: ");
                        int carId = scanner.nextInt();
                        system.viewCar(carId);
                        break;
                    case 3:
                        System.out.print("Enter car ID: ");
                        carId = scanner.nextInt();
                        System.out.print("Enter new make: ");
                        make = scanner.next();
                        System.out.print("Enter new model: ");
                        model = scanner.next();
                        System.out.print("Enter new year: ");
                        year = scanner.nextInt();
                        System.out.print("Enter new daily rate: ");
                        dailyRate = scanner.nextDouble();
                        system.updateCar(carId, make, model, year, dailyRate);
                        break;
                    case 4:
                        System.out.print("Enter car ID: ");
                        carId = scanner.nextInt();
                        system.deleteCar(carId);
                        break;
                    case 5:
                        System.out.print("Enter name: ");
                        String name = scanner.next();
                        System.out.print("Enter email: ");
                        String email = scanner.next();
                        System.out.print("Enter phone number: ");
                        String phoneNumber = scanner.next();
                        System.out.print("Enter address: ");
                        String address = scanner.next();
                        system.registerCustomer(name, email, phoneNumber, address);
                        break;
                    case 6:
                        System.out.print("Enter customer ID: ");
                        int customerId = scanner.nextInt();
                        system.viewCustomer(customerId);
                        break;
                    case 7:
                        System.out.print("Enter customer ID: ");
                        customerId = scanner.nextInt();
                        System.out.print("Enter new name: ");
                        name = scanner.next();
                        System.out.print("Enter new email: ");
                        email = scanner.next();
                        System.out.print("Enter new phone number: ");
                        phoneNumber = scanner.next();
                        System.out.print("Enter new address: ");
                        address = scanner.next();
                        system.updateCustomer(customerId, name, email, phoneNumber, address);
                        break;
                    case 8:
                        System.out.print("Enter customer ID: ");
                        customerId = scanner.nextInt();
                        system.deleteCustomer(customerId);
                        break;
                    case 9:
                        System.out.print("Enter car ID: ");
                        carId = scanner.nextInt();
                        System.out.print("Enter customer ID: ");
                        customerId = scanner.nextInt();
                        System.out.print("Enter rental start date (yyyy-mm-dd): ");
                        Date rentalStartDate = Date.valueOf(scanner.next());
                        System.out.print("Enter rental end date (yyyy-mm-dd): ");
                        Date rentalEndDate = Date.valueOf(scanner.next());
                        system.rentCar(carId, customerId, rentalStartDate, rentalEndDate);
                        break;
                    case 10:
                        System.out.print("Enter rental ID: ");
                        int rentalId = scanner.nextInt();
                        system.returnCar(rentalId);
                        break;
                    case 11:
                        System.out.print("Enter rental ID: ");
                        rentalId = scanner.nextInt();
                        system.viewRental(rentalId);
                        break;
                    case 12:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
