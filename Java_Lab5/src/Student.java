import java.util.Arrays;

public class Student {
    // Поля
    private String fullName;
    private String groupId;
    private int studentId;
    private int[] grades;
    private boolean hasScholarship;

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
        if (fullName == null)
            throw new Exception("Пустое имя");
        this.fullName = fullName;
    }
    public void setGroupId(String groupId) throws Exception {
        if (groupId == null)
            throw new Exception("Номер группы не может быть отрицательным");
        this.groupId = groupId;
    }
    public void setStudentId(int studentId) throws Exception {
        if (studentId <= 0)
            throw new Exception("Номер студенческого билета не может быть отрицательным");
        this.studentId =  studentId;
    }
    public void setGrades(int[] grades) throws Exception {
        if (grades.length != 5)
            throw new Exception("Массив неверного размера");
        for (var elem : grades)
            if (elem < 2 || elem > 5)
                throw new Exception("Оценка не может быть меньше двух или больше 5");
        this.grades = grades;
    }
    public void setGrade(int grade, int index) throws Exception {
        if (grade < 2 || grade > 5)
            throw new Exception("Оценка не может быть меньше двух или больше 5");
        if (index < 0 || index > 4)
            throw new Exception("Индекс за пределами массива");
        grades[index] = grade;
    }
    public void updateScholarshipStatus() {
        var average = calculateAverage();
        hasScholarship = average >= 4.5;
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
        return Arrays.stream(grades).allMatch(x -> x == 5);
    }
}
