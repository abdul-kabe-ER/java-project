import java.io.*;
import java.util.*;

class Person {
    private String name;
    private int age;
    private String contact;

    public Person(String name, int age, String contact) {
        this.name = name;
        this.age = age;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getContact() {
        return contact;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Contact: " + contact;
    }
}

class Employee extends Person {
    private static int employeeCounter = 0;
    private final int employeeId;
    private String designation;
    private double salary;

    public Employee(String name, int age, String contact, String designation, double salary) {
        super(name, age, contact);
        this.employeeId = ++employeeCounter;
        this.designation = designation;
        this.salary = salary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "ID: " + employeeId + ", " + super.toString() + ", Designation: " + designation + ", Salary: " + salary;
    }
}

class EmployeeManagementSystem {
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(String name, int age, String contact, String designation, double salary) {
        Employee emp = new Employee(name, age, contact, designation, salary);
        employees.add(emp);
        System.out.println("Employee " + name + " added successfully!");
    }

    public void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found!");
        } else {
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        }
    }

    public void searchEmployee(String searchKey) {
        List<Employee> results = new ArrayList<>();
        for (Employee emp : employees) {
            if (String.valueOf(emp.getEmployeeId()).equalsIgnoreCase(searchKey)
                    || emp.getName().equalsIgnoreCase(searchKey)) {
                results.add(emp);
            }
        }
        if (results.isEmpty()) {
            System.out.println("No matching employees found!");
        } else {
            for (Employee emp : results) {
                System.out.println(emp);
            }
        }
    }

    public void updateEmployee(int empId, String designation, Double salary) {
        for (Employee emp : employees) {
            if (emp.getEmployeeId() == empId) {
                if (designation != null) {
                    emp.setDesignation(designation);
                }
                if (salary != null) {
                    emp.setSalary(salary);
                }
                System.out.println("Employee " + empId + " updated successfully!");
                return;
            }
        }
        System.out.println("Employee not found!");
    }

    public void deleteEmployee(int empId) {
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee emp = iterator.next();
            if (emp.getEmployeeId() == empId) {
                iterator.remove();
                System.out.println("Employee " + empId + " deleted successfully!");
                return;
            }
        }
        System.out.println("Employee not found!");
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(employees);
            System.out.println("Data saved to file successfully!");
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            employees = (List<Employee>) ois.readObject();
            System.out.println("Data loaded from file successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }
}

public class EmployeeManagement {
    public static void main(String[] args) {
        EmployeeManagementSystem system = new EmployeeManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Employee Management System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Save to File");
            System.out.println("7. Load from File");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter contact: ");
                    String contact = scanner.nextLine();
                    System.out.print("Enter designation: ");
                    String designation = scanner.nextLine();
                    System.out.print("Enter salary: ");
                    double salary = scanner.nextDouble();
                    system.addEmployee(name, age, contact, designation, salary);
                    break;

                case 2:
                    system.viewEmployees();
                    break;

                case 3:
                    System.out.print("Enter employee ID or name to search: ");
                    String searchKey = scanner.nextLine();
                    system.searchEmployee(searchKey);
                    break;

                case 4:
                    System.out.print("Enter employee ID to update: ");
                    int empId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new designation (leave blank to skip): ");
                    String newDesignation = scanner.nextLine();
                    System.out.print("Enter new salary (leave blank to skip): ");
                    String salaryInput = scanner.nextLine();
                    Double newSalary = salaryInput.isEmpty() ? null : Double.parseDouble(salaryInput);
                    system.updateEmployee(empId, newDesignation.isEmpty() ? null : newDesignation, newSalary);
                    break;

                case 5:
                    System.out.print("Enter employee ID to delete: ");
                    empId = scanner.nextInt();
                    system.deleteEmployee(empId);
                    break;

                case 6:
                    System.out.print("Enter filename to save: ");
                    String saveFile = scanner.next();
                    system.saveToFile(saveFile);
                    break;

                case 7:
                    System.out.print("Enter filename to load: ");
                    String loadFile = scanner.next();
                    system.loadFromFile(loadFile);
                    break;

                case 8:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
