import java.time.*;
public class Employee {
    // Поля
    private String fullName;
    private String position;
    private String department;
    private double salary;
    private LocalDate hireDate;
    private double bonus;

    // Конструкторы
    public Employee(String fullName, String position, String department, double salary, LocalDate hireDate) throws Exception {
        this(fullName, position, department,salary, hireDate, 0);
    }
    public Employee(String fullName, String position, String department, double salary, LocalDate hireDate, double bonus) throws Exception{
        setFullName(fullName);
        setPosition(position);
        setDepartment(department);
        setSalary(salary);
        setHireDate(hireDate);
        setBonus(bonus);
    }

    // Геттеры
    public String getFullName() { return fullName; }
    public String getPosition() { return position; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public LocalDate getHireDate() { return hireDate; }
    public double getBonus() { return bonus; }

    // Сеттеры
    public void setFullName(String fullName) throws Exception {
        if (fullName == null)
            throw new Exception("Имя не должно быть пустым");
        this.fullName = fullName;
    }
    public void setPosition(String position) throws Exception {
        if (position == null)
            throw new Exception("Должность не может быть пустой");
        this.position = position;
    }
    public void setDepartment(String department) throws Exception {
        if (department == null)
            throw new Exception("отдел не может быть пустым");
        this.department = department;
    }
    public void setSalary(double salary) throws Exception {
        if (salary <= 0)
            throw new Exception("Зарплата не может быть неотрицательной");
        this.salary = salary;
    }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
    public void setBonus(double bonus) throws Exception {
        if (bonus < 0)
            throw new Exception("Надбавка не может быть отрицательной");
        this.bonus = bonus;
    }

    // Получение зарплата + надбавка
    public double getTotalSalary() { return getSalary() + getBonus(); }
    // Количество полных лет работы
    public int getExperience() { return Period.between(getHireDate(), LocalDate.now()).getYears(); }
    // Проверка на повышение
    public boolean isEligibleForPromotion() { return getExperience() > 3  && getSalary() < 100000; } // Возможно лучше взять getTotalSalary, по заданию нет уточнения
    // Вывод информации
    public void printInfo() {
        System.out.println("ФИО: " + getFullName());
        System.out.println("Должность: " + getPosition());
        System.out.println("Отдел: " + getDepartment());
        System.out.println("Зарплата: " + getSalary());
        System.out.println("Дата приёма: " + getHireDate());
        System.out.println("Надбавка: " + getBonus());
        System.out.println("Общий доход: " + getTotalSalary());
        System.out.println("Стаж: " +  getExperience());
        System.out.println("Можно повысить? " + (isEligibleForPromotion() ? "Да" : "Нет"));
    }
}
