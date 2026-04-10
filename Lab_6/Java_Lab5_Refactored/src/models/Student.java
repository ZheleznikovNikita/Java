package models;

import utils.ValidationUtils;

import java.util.Arrays;

public class Student {
    // Поля
    private String fullName;
    private String groupId;
    private int studentId;
    private int[] grades;
    private boolean hasScholarship;

    private static final int grades_count = 5;
    private static final int min_grade = 2;
    private static final int max_grade = 5;
    private static final double scholarship_average = 4.5;

    // Конструктор
    public Student(String fullName, String groupId, int studentId, int[] grades) throws Exception {
        setFullName(fullName);
        setGroupId(groupId);
        setStudentId(studentId);
        setGrades(grades);
        updateScholarshipStatus();
    }
    // Геттеры
    public String getFullName() { return fullName; }
    public String getGroupId() { return groupId; }
    public int getStudentId() { return studentId; }
    public int[] getGrades() { return grades; }
    public int getGrade(int index) throws Exception {
        if (index < 0 || index > 4)
            throw new Exception("Неверный индекс");
        return grades[index];
    }
    public boolean getHasScholarship() { return hasScholarship; }
    // Сеттеры
    public void setFullName(String fullName) throws Exception {
        ValidationUtils.check_name(fullName, "Пустое имя");
        this.fullName = fullName;
    }
    public void setGroupId(String groupId) throws Exception {
        ValidationUtils.check_name(groupId, "Номер группы не может быть пустым");
        this.groupId = groupId;
    }
    public void setStudentId(int studentId) throws Exception {
        ValidationUtils.check_number_less_or_equal(studentId, "Номер студенческого билета должен быть положительным");
        this.studentId =  studentId;
    }
    public void setGrades(int[] grades) throws Exception {
        if (grades.length != grades_count)
            throw new Exception("Массив неверного размера");
        for (var elem : grades)
            if (elem < min_grade || elem > max_grade)
                throw new Exception("Оценка не может быть меньше двух или больше 5");
        this.grades = grades;
    }
    public void setGrade(int grade, int index) throws Exception {
        if (grade < min_grade || grade > max_grade)
            throw new Exception("Оценка не может быть меньше двух или больше 5");
        if (index < 0 || index > 4)
            throw new Exception("Индекс за пределами массива");
        grades[index] = grade;
    }
    public void updateScholarshipStatus() {
        var average = calculateAverage();
        hasScholarship = average >= scholarship_average;
    }
    // Вычисление среднего балла
    public double calculateAverage() {
        int sum = 0;
        for (var elem : getGrades())
            sum += elem;
        return (double)sum / getGrades().length;
    }
    // Вывод информации
    public void printInfo() {
        System.out.println("ФИО: " + getFullName());
        System.out.println("Номер группы: " + getGroupId());
        System.out.println("Номер студенческого билета: " + getStudentId());
        System.out.println("Оценки: ");
        for (int i = 0; i < getGrades().length; ++i){
            if (i == 4) {
                System.out.print(getGrades()[i] + "\n");
                break;
            }
            System.out.print(getGrades()[i] + ", ");
        }
        System.out.printf("Средний балл: %.2f\n", calculateAverage());
        System.out.println((getHasScholarship() ? "Получает " : "Не получает ") + "стипендию");
    }
    // Проверка на количество 5
    public boolean isExcellent() {
        return Arrays.stream(grades).allMatch(x -> x == max_grade);
    }
}

// Добавлены константы для оценок и стипендии