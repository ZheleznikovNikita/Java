import java.time.Year;
import utils.ValueChecker;

public class Employee {
    private int id;
    private String fullName;
    private String department;
    private double salary;
    private int hireYear;

    private static int nextId = 1;

    private Employee(int id, String fullName, String department, double salary, int hireYear) {
        this.id = id;
        this.fullName = fullName;
        this.department =  department;
        this.salary = salary;
        this.hireYear = hireYear;
        ++nextId;
    }

    public static Employee createEmployee(String fullName, String department, double salary, int hireYear) throws IllegalArgumentException {
        ValueChecker.check_string(fullName);
        ValueChecker.check_string(department);
        ValueChecker.check_low_equal_zero_double(salary);
        if (hireYear < 2000 || hireYear > 2026)
            throw new IllegalArgumentException("Некорректный год");
        String name = fullName;
        String dep = department;
        double s = salary;
        int date = hireYear;
        return new Employee(nextId, fullName, department, salary, hireYear);
    }

    public static Employee createIntern(String fullName, String department) throws IllegalArgumentException {
        return createEmployee(fullName, department, 30000, Year.now().getValue());
    }

    // Геттеры
    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public int getHireYear() { return hireYear; }
    public static int getNextId() { return nextId; }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nName: " + fullName +
                "\nDepartment: " + department +
                "\nSalary: " + salary +
                "\nDate of hiring: " + hireYear;
}

static class Task2 {
    static void main() {
        Employee emp1 = Employee.createEmployee("Иван Иванов", "Разработка", 120000.0, 2024);
        System.out.println("Создан сотрудник: " + emp1);

        Employee intern1 = Employee.createIntern("Валерий Абрамов", "Аналитика");
        System.out.println("Создан стажёр: " + intern1);

        createWithValidation("Пётр Петров", "Логистика", -5000.0, 2025);
        createWithValidation("John Doe", "Маркетинг", 80000.0, 2030);
    }

    // Вспомогательный метод для демонстрации обработки исключений
    private static void createWithValidation(String name, String dept, double sal, int year) {
        try {
            Employee e = Employee.createEmployee(name, dept, sal, year);
            System.out.println("Успешно создан: " + e);
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Ошибка: " + ex.getMessage());
        }
    }
    }
}