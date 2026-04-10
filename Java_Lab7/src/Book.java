import utils.ValueChecker;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private int totalCopies;
    private int borrowedCopies;

    private static int totalBooks;

    // Конструкторы
    public Book(String title, String author) throws IllegalArgumentException {
        setTitle(title);
        setAuthor(author);
        setIsbn();
        setTotalCopies(1);
        borrowedCopies = 0;
        totalBooks++;
    }

    public Book(String title, String author, int totalCopies) throws IllegalArgumentException {
        setTitle(title);
        setAuthor(author);
        setIsbn();
        setTotalCopies(totalCopies);
        borrowedCopies = 0;
        totalBooks++;
    }

    public Book(Book other) {
        title = other.title;
        author = other.author;
        isbn = other.isbn;
        totalCopies = other.totalCopies;
        borrowedCopies = other.borrowedCopies;
        totalBooks++; // раз создаётся новый объект класса, то количество растёт
    }

    // Геттеры
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public int getTotalCopies() { return totalCopies; }
    public int getBorrowedCopies() { return borrowedCopies; }
    public static int getTotalBooks() { return totalBooks; }

    // Сеттеры
    public void setTitle(String title) throws IllegalArgumentException {
        ValueChecker.check_string(title);
        this.title = title;
    }
    public void setAuthor(String author) throws IllegalArgumentException {
        ValueChecker.check_string(author);
        this.author = author;
    }
    private void setIsbn() { isbn = generateIsbn(); }
    public void setTotalCopies(int totalCopies) throws IllegalArgumentException {
        ValueChecker.check_low_zero_int(totalCopies);
        this.totalCopies = totalCopies;
    }

    // Генератор ISBN
    private static String generateIsbn() { return "ISBN-" + (totalBooks + 1); }

    // Берёт одну книгу
    public void borrowCopy() {
        if (totalCopies > 0) {
            borrowedCopies++;
            totalCopies--;
            System.out.println("Книга взята");
            return;
        }
        System.out.println("Нет свободных книг");
    }

    // Возвращает одну книгу
    public void returnCopy() {
        if (borrowedCopies > 0) {
            borrowedCopies--;
            totalCopies++;
            System.out.println("Книга возвращена");
            return;
        }
        System.out.println("Все книги на месте");
    }

    public boolean isAvailable() { return (totalCopies - borrowedCopies) > 0; }

    @Override
    public String toString() {
        return "Title: " + title +
                "\nAuthor: " + author +
                "\nISBN: " + isbn +
                "\nTotal copies: " + totalCopies +
                "\nBorrowed copies: " + borrowedCopies +
                "\nAvailable: " + (isAvailable() ? "Yes\n" : "No\n");
    }

    static void main() {
        try {
            Book book1 = new Book("Война и мир", "Лев Толстой");
            System.out.println("Создана книга:\n" + book1);
            Book book2 = new Book("1984", "Джордж Оруэлл", 3);
            System.out.println("Создана книга:\n" + book2);
            Book book3 = new Book(book1);
            System.out.println("Создана копия:\n" + book3);

            System.out.println("Всего создано объектов Book: " + Book.getTotalBooks());

            System.out.println("Доступна ли book2? " + (book2.isAvailable() ? "Да" : "Нет"));

            book2.borrowCopy();
            book2.borrowCopy();
            System.out.println("После 2 взятий: " + book2);

            book2.returnCopy();
            System.out.println("После 1 возврата: " + book2);

            System.out.println("Забираем последний экземпляр book1:");
            book1.borrowCopy();
            System.out.println("Доступна ли book1? " + (book1.isAvailable() ? "Да" : "Нет"));
            book1.borrowCopy();
            System.out.println("Состояние book1: " + book1);
        }
        catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}