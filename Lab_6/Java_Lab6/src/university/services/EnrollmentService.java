package university.services;

import university.models.Student;
import university.models.Course;

public class EnrollmentService {
    public static void enrollStudentToCourse(Student student, Course course) throws Exception{
        student.addCourse(course);
        course.addStudentOnCourse(student);
    }
}
