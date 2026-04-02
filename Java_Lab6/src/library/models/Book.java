package library.models;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private int year;
    private boolean isAvailable;
    private int borrowCount;

    private static int totalBooks = 0;
    private static long isbn_counter = 978_000_000_000L;

    public Book(String title, String author, int year) throws Exception {
        setIsbn();
        setTitle(title);
        setAuthor(author);
        setYear(year);
        isAvailable = true;
        borrowCount = 0;
        totalBooks++;
    }

    // Геттеры
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return  year; }
    public boolean getIsAvailable() { return isAvailable; }
    public int getBorrowCount() { return borrowCount; }
    public static int getTotalBooks() { return totalBooks; }
    // Сеттеры
    private void setIsbn() { isbn = String.valueOf(++isbn_counter); }
    private void setTitle(String title) throws Exception {
        check_string(title);
        this.title = title;
    }
    private void setAuthor(String author) throws Exception {
        check_string(author);
        this.author = author;
    }
    private void setYear(int year) throws Exception {
        if (year < 0)
            throw new Exception("Год не может быть отрицательным");
        this.year = year;
    }

    public void borrow() {
        if (isAvailable) {
            isAvailable = false;
            borrowCount++;
            System.out.println("Книга выдана");
        }
        else
            System.out.println("Книга уже занята");
    }

    public void returnBook() {
        if (!isAvailable) {
            isAvailable = true;
            System.out.println("Книга возвращена");
        }
        else
            System.out.println("Книга уже доступна");
    }

    private void check_string(String s) throws Exception {
        if (s == null || s.isEmpty())
            throw new Exception("Подана пустая строка");
    }
}
