package payrorll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Employee {
    private String name;
    private int id;

    public Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public abstract double calculateSalary();

    @Override
    public String toString() {
        return "Employee [name=" + name + ", id=" + id + ", salary=" + calculateSalary() + "]";
    }
}

class FullTimeEmployee extends Employee {
    private double monthlySalary;

    public FullTimeEmployee(String name, int id, double monthlySalary) {
        super(name, id);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary;
    }
}

class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(String name, int id, int hoursWorked, double hourlyRate) {
        super(name, id);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateSalary() {
        return hoursWorked * hourlyRate;
    }
}

class PayrollSystem {
    private List<Employee> employeeList;

    public PayrollSystem() {
        employeeList = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employeeList.add(employee);
        System.out.println("Employee added successfully.");
    }

    public void removeEmployee(int id) {
        Employee employeeToRemove = findEmployeeById(id);
        if (employeeToRemove != null) {
            employeeList.remove(employeeToRemove);
            System.out.println("Employee with ID " + id + " removed.");
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    public Employee findEmployeeById(int id) {
        for (Employee employee : employeeList) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    public void displayEmployees() {
        if (employeeList.isEmpty()) {
            System.out.println("No employees to display.");
        } else {
            for (Employee employee : employeeList) {
                System.out.println(employee);
            }
        }
    }

    public double calculateTotalPayroll() {
        double totalPayroll = 0;
        for (Employee employee : employeeList) {
            totalPayroll += employee.calculateSalary();
        }
        return totalPayroll;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PayrollSystem payrollSystem = new PayrollSystem();
        boolean exit = false;

        while (!exit) {
            System.out.println("\nPayroll System Menu:");
            System.out.println("1. Add Employee");
            System.out.println("2. Remove Employee");
            System.out.println("3. Display All Employees");
            System.out.println("4. Search Employee by ID");
            System.out.println("5. Calculate Total Payroll");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addEmployee(scanner, payrollSystem);
                    break;
                case 2:
                    System.out.print("Enter employee ID to remove: ");
                    int removeId = scanner.nextInt();
                    payrollSystem.removeEmployee(removeId);
                    break;
                case 3:
                    payrollSystem.displayEmployees();
                    break;
                case 4:
                    System.out.print("Enter employee ID to search: ");
                    int searchId = scanner.nextInt();
                    Employee foundEmployee = payrollSystem.findEmployeeById(searchId);
                    if (foundEmployee != null) {
                        System.out.println("Employee found: " + foundEmployee);
                    } else {
                        System.out.println("Employee with ID " + searchId + " not found.");
                    }
                    break;
                case 5:
                    System.out.println("Total Payroll Cost: " + payrollSystem.calculateTotalPayroll());
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting Payroll System.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void addEmployee(Scanner scanner, PayrollSystem payrollSystem) {
        System.out.print("Enter employee type (1 for Full-Time, 2 for Part-Time): ");
        int empType = scanner.nextInt();

        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();

        if (empType == 1) {
            System.out.print("Enter monthly salary: ");
            double monthlySalary = scanner.nextDouble();
            payrollSystem.addEmployee(new FullTimeEmployee(name, id, monthlySalary));
        } else if (empType == 2) {
            System.out.print("Enter hours worked: ");
            int hoursWorked = scanner.nextInt();
            System.out.print("Enter hourly rate: ");
            double hourlyRate = scanner.nextDouble();
            payrollSystem.addEmployee(new PartTimeEmployee(name, id, hoursWorked, hourlyRate));
        } else {
            System.out.println("Invalid employee type. Employee not added.");
        }
    }
}
