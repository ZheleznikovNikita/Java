package models;
import utils.ValidationUtils;

import java.time.Year;

public class Book {
    // Поля класса
    private String title;
    private String author;
    private int year;
    private int pages;
    private String publisher;
    private boolean isAvailable;

    private static final int old_year = 1450;
    private static final int possible_age = 50;

    // using VU = ValidationUtils
    // Хотелось бы

    // Конструкторы
    public Book(String title, String author, int year, int pages, String publisher, boolean isAvailable) throws Exception {
        setTitle(title);
        setAuthor(author);
        setYear(year);
        setPages(pages);
        setPublisher(publisher);
        setIsAvailable(isAvailable);
    }
    public Book(String title, String author, int year, int pages, boolean isAvailable) throws Exception {
        this(title, author, year, pages, "Неизвестно", isAvailable);
    }

    // Геттеры
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public int getPages() { return pages; }
    public String getPublisher() { return publisher; }
    public boolean getIsAvailable() { return isAvailable; }
    // Сеттеры
    public void setTitle(String title) throws Exception {
        ValidationUtils.check_name(title, "Пустое название заголовка");
        this.title = title;
    }
    public void setAuthor(String author) throws Exception {
        ValidationUtils.check_name(author, "Пустое имя автора");
        this.author = author;
    }
    public void setYear(int year) throws Exception {
        if (year < old_year)
            throw new Exception("Год не может быть меньше 1450");
        this.year = year;
    }
    public void setPages(int pages) throws Exception {
        ValidationUtils.check_number_less_or_equal(pages, "Количество страниц не можети быть отрицательным или нулевым");
        this.pages = pages;
    }
    public void setPublisher(String publisher) throws Exception {
        ValidationUtils.check_name(publisher, "Издательство не может быть пустым");
        this.publisher = publisher;
    }
    public void setIsAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }
    // Информация о книге
    public void printInfo() {
        System.out.println("Название: " + getTitle());
        System.out.println("Автор: " + getAuthor());
        System.out.println("Год издания: " + getYear());
        System.out.println("Количество страниц: " + getPages());
        System.out.println("Издательство: " + getPublisher());
        System.out.println("Статус: " + (getIsAvailable() ? "Доступна" : "Занята"));
    }
    // Занимает книгу
    public void borrow() {
        if (isAvailable)
            isAvailable = false;
        else
            System.out.println("Книга уже занята");
    }
    // Возвращает книгу
    public void returnBook() {
        if (!isAvailable)
            isAvailable = true;
        else
            System.out.println("Книга уже доступна");
    }
    // Проверка возрастра книги
    public boolean isOld() { return (Year.now().getValue() - year) > possible_age; }
}

// Добавлены константы для проверки возрастов, проверки вынесены в отдельный класс,
// в дальнейшем наличие проверок не будет учитываться при составлении комментария