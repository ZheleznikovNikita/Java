package university.models;

import university.utils.StringUtils;
import java.util.ArrayList;
import java.util.Random;

public class Student {
    private String fullName;
    private long studentId;
    private ArrayList<Course> courses;

    private static int totalStudents = 0;
    private static final int max_courses = 5;

    public Student(String fullName) throws Exception {
        setFullName(fullName);
        setStudentId();
        courses = new ArrayList<>();
        totalStudents++;
    }

    // Геттеры
    public String getFullName() { return fullName; }
    public long getStudentId() { return studentId; }
    public ArrayList<Course> getCourses() { return courses; }
    public int getEnrolledCoursesCount() { return courses.size(); }
    public static int getTotalStudents() { return totalStudents;  }

    // Сеттеры
    public void setFullName(String fullName) throws Exception {
        StringUtils.check_string(fullName, "Имя не может быть пустым");
        this.fullName = fullName;
    }
    public void setStudentId() throws Exception { studentId = new Random().nextLong(1000000, 10000000); }
    public void setCourses(ArrayList<Course> courses) throws Exception {
        if (courses.isEmpty())
            throw new Exception("Массив курсов не может быть пустым");
        this.courses = courses;
    }

    // Добавление курса
    public void addCourse(Course course) throws Exception {
        if (course == null)
            throw new Exception("Передано пустое значение");
        if (courses.size() == max_courses) {
            System.out.println("Студент записан на максимальное количество курсов");
        return;
        }
        if (courses.contains(course)) {
            System.out.println("Студент уже записан на этот курс");
            return;
        }
        courses.add(course);
    }
}
