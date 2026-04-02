package university.models;

import university.utils.StringUtils;
import java.util.ArrayList;

public class Course {
    private String courseName;
    private Professor teacher;
    private ArrayList<Student> students;

    private static int totalCourses;
    private static final int max_students = 5;

    public Course(String courseName, Professor teacher) throws Exception {
        setCourseName(courseName);
        setTeacher(teacher);
        students = new ArrayList<>();
        totalCourses++;
    }

    // Геттеры
    public String getCourseName() { return courseName; }
    public Professor getTeacher() { return teacher; }
    public ArrayList<Student> getStudents() { return students; }
    public int getEnrolledStudentsCount() { return students.size(); }
    public int getMaxStudents() { return max_students; }
    public static int getTotalCourses() { return totalCourses; }

    // Сеттеры
    public void setCourseName(String courseName) throws Exception {
        StringUtils.check_string(courseName, "Имя не может быть пустым");
        this.courseName = courseName;
    }
    public void setTeacher(Professor teacher) throws Exception {
        if (teacher == null)
            throw new Exception("Объёкт пуст");
        this.teacher = teacher;
    }
    public void setStudents(ArrayList<Student> students) throws Exception {
        if (students.isEmpty())
            throw new Exception("Передан пустой массив");
        this.students = students;
    }

    // Добавление студента
    public void addStudentOnCourse(Student student) throws Exception {
        if (student == null)
            throw new Exception("Передано пустое значение");
        if (students.contains(student)) {
            System.out.println("Студент уже записан на этот курс");
            return;
        }
        if (students.size() == max_students) {
            System.out.println("На курсе максимальное количество студентов");
            return;
        }
        students.add(student);
    }
}
