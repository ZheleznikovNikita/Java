package models;

import java.time.*;
import utils.ValidationUtils;

public class Employee {
    // Поля
    private String fullName;
    private String position;
    private String department;
    private double salary;
    private LocalDate hireDate;
    private double bonus;

    private static final int experience_for_promotion = 3;
    private static final int salary_for_promotion = 100000;

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
        ValidationUtils.check_name(fullName, "Имя не должно быть пустым");
        this.fullName = fullName;
    }
    public void setPosition(String position) throws Exception {
        ValidationUtils.check_name(position, "Должность не может быть пустой");
        this.position = position;
    }
    public void setDepartment(String department) throws Exception {
        ValidationUtils.check_name(department, "Отдел не может быть пустым");
        this.department = department;
    }
    public void setSalary(double salary) throws Exception {
        ValidationUtils.check_number_less_or_equal(salary, "Зарплата должна быть положительной");
        this.salary = salary;
    }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
    public void setBonus(double bonus) throws Exception {
        ValidationUtils.check_number_less(bonus, "Надбавка не может быть отрицательной");
        this.bonus = bonus;
    }

    // Получение зарплата + надбавка
    public double getTotalSalary() { return getSalary() + getBonus(); }
    // Количество полных лет работы
    public int getExperience() { return Period.between(getHireDate(), LocalDate.now()).getYears(); }
    // Проверка на повышение
    public boolean isEligibleForPromotion() { return getExperience() > experience_for_promotion  && getSalary() < salary_for_promotion; } // Возможно лучше взять getTotalSalary, по заданию нет уточнения
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

// Добавлены константы для проверки возможности повышения