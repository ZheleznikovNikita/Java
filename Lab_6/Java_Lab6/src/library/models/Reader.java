package library.models;

import java.util.ArrayList;

public class Reader {
    private String fullName;
    private int readerId;
    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    private static int readerId_counter = 100;
    private static int totalReaders = 0;

    public Reader(String fullName) throws Exception {
        setFullName(fullName);
        setReaderId();
        totalReaders++;
    }

    // Геттеры
    public String getFullName() { return fullName; }
    public int getReaderId() { return readerId; }
    public ArrayList<Book> getBorrowedBooks() { return borrowedBooks; }
    public static int getTotalReaders() { return totalReaders; }
    public int getBookCount() { return borrowedBooks.size(); }
    // Сеттеры
    public void setFullName(String fullName) throws Exception {
        check_string(fullName);
        this.fullName = fullName;
    }
    private void setReaderId() { readerId = ++readerId_counter; }

    public void borrowBook(Book book) throws Exception {
        if (book == null)
            throw new Exception("Передан пустой элемент");
        if (!book.getIsAvailable()) {
            System.out.println("Книга уже была выдана");
            return;
        }
        book.borrow();
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) throws Exception {
        if (book == null)
            throw new Exception("Передан пустой элемент");
        if (book.getIsAvailable()) {
            System.out.println("Книга уже была возвращена");
            return;
        }
        if (!borrowedBooks.contains(book)){
            System.out.println("Читатель не брал эту книгу");
            return;
        }
        book.returnBook();
        borrowedBooks.remove(book);
    }

    private void check_string(String s) throws Exception {
        if (s == null || s.isEmpty())
            throw new Exception("Передана пустая строка");
    }
}
