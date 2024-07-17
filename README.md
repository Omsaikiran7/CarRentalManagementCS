# CarRentalManagementCS

A menu-based console application to manage a car rental system using Core Java, MySQL, and JDBC. The application allows users to manage cars, customers, and rentals.

## Functionalities

1. **Car Management:**
   - Add a new car
   - View car details
   - Update car information
   - Delete a car

2. **Customer Management:**
   - Register a new customer
   - View customer details
   - Update customer information
   - Delete a customer

3. **Rental Management:**
   - Rent a car to a customer
   - Return a rented car
   - View rental details
   - Calculate rental charges

## Database Schema

- **Car Table:**
  - car_id (Primary Key)
  - make
  - model
  - year
  - daily_rate

- **Customer Table:**
  - customer_id (Primary Key)
  - name
  - email
  - phone_number
  - address

- **Rental Table:**
  - rental_id (Primary Key)
  - car_id (Foreign Key references Car Table)
  - customer_id (Foreign Key references Customer Table)
  - rental_start_date
  - rental_end_date
  - total_charge

## Requirements

- Java Development Kit (JDK) 
- MySQL Database
- MySQL Connector/J

## Steps
   
## Create MySQL DataBase:
CREATE DATABASE car_rental_db;
USE car_rental_db;

CREATE TABLE Car (
    car_id INT AUTO_INCREMENT PRIMARY KEY,
    make VARCHAR(50),
    model VARCHAR(50),
    year INT,
    daily_rate DOUBLE
);

CREATE TABLE Customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(50),
    phone_number VARCHAR(15),
    address VARCHAR(100)
);

CREATE TABLE Rental (
    rental_id INT AUTO_INCREMENT PRIMARY KEY,
    car_id INT,
    customer_id INT,
    rental_start_date DATE,
    rental_end_date DATE,
    total_charge DOUBLE,
    FOREIGN KEY (car_id) REFERENCES Car(car_id),
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

## Update Database Connection Details:
Update the JDBC_URL, JDBC_USER, and JDBC_PASSWORD constants in the CarRental.java file with your MySQL database connection details.

## Add MySQL Connector/J to Project:
Download the MySQL Connector/J from MySQL Connector/J download page.
Add the JAR file to your project's build path in Eclipse:

Right-click on the project -> "Build Path" -> "Configure Build Path" -> "Libraries" tab -> "Add External JARs..." and select the Connector/J JAR file.

## Run the Application:
Open Eclipse.
Create a new Java project and name it CarRental.
Create a package named carrt.
Add the CarRental.java file to the carrt package.
Ensure the MySQL Connector/J JAR file is in the project's build path.
Run the CarRental class as a Java application.

### Usage:
When you run the application, you will see a menu with the following options:
Car Rental Management System
1. Add a new car
2. View car details
3. Update car information
4. Delete a car
5. Register a new customer
6. View customer details
7. Update customer information
8. Delete a customer
9. Rent a car to a customer
10. Return a rented car
11. View rental details
12. Exit
Choose an option:

Select an option by entering the corresponding number and follow the prompts to perform the desired operation.

### For Example:
Car Rental Management System
1. Add a new car
Choose an option: 1
Enter make: Toyota
Enter model: Corolla
Enter year: 2020
Enter daily rate: 30.00
Car added successfully.
