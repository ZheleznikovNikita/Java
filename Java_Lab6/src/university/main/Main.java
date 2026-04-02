package university.main;

import university.models.*;
import university.services.*;
import university.utils.*;

public class Main {
    static void main() throws Exception {
        try {
            Professor prof = new Professor("Данил Валерьевич", "Java language");

            Course math = new Course(StringUtils.capitalize("Java language"), prof);
            Course physics = new Course(StringUtils.capitalize("math"), prof);

            String[] rawNames = {"иванов Иван", "John Doe", "Человек Человеков", "bad-name"};
            Student[] students = new Student[rawNames.length];
            int studentCount = 0;

            for (String name : rawNames) {
                if (StringUtils.isValidName(name)) {
                    students[studentCount++] = new Student(StringUtils.capitalize(name));
                } else {
                    System.out.println("Имя \"" + name + "\" некорректно");
                }
            }

            EnrollmentService.enrollStudentToCourse(students[0], math);
            EnrollmentService.enrollStudentToCourse(students[0], physics);
            EnrollmentService.enrollStudentToCourse(students[1], math);
            EnrollmentService.enrollStudentToCourse(students[2], math);

            EnrollmentService.enrollStudentToCourse(students[2], math);

            System.out.println("\nИтоговая статистика");
            System.out.println("Всего студентов: " + Student.getTotalStudents());
            System.out.println("Всего курсов: " + Course.getTotalCourses());

            System.out.println("\nСтудент " + students[0].getFullName() + " записан на курсов: " + students[0].getEnrolledCoursesCount());
            System.out.println("На курсе " + math.getCourseName() + " студентов: " + math.getEnrolledStudentsCount() + " из " + math.getMaxStudents());
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
