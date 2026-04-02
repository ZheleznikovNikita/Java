package university.models;

import university.utils.StringUtils;

public class Professor {
    private String _name;
    private String _subject;

    public Professor(String name, String subject) throws Exception {
        this.name(name);
        this.subject(subject);
    }

    // Геттеры
    public String name() { return _name; }
    public String subject() { return _subject; }

    // Сеттеры
    public void name(String name) throws Exception {
        StringUtils.check_string(name, "Имя не может быть пустым");
        _name = name;
    }
    public void subject(String subject) throws Exception {
        StringUtils.check_string(subject, "Предмет не может быть пустым");
        _subject = subject;
    }
}
