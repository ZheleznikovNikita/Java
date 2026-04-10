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
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("Пустое название");
        this.title = title;
    }
    public void setAuthor(String author) throws IllegalArgumentException {
        if (author == null || author.trim().isEmpty())
            throw new IllegalArgumentException("Пустое имя автора");
        this.author = author;
    }
    private void setIsbn() { isbn = generateIsbn(); }
    public void setTotalCopies(int totalCopies) throws IllegalArgumentException {
        if (totalCopies < 0)
            throw new IllegalArgumentException("Количество не может быть отрицательным");
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
        if (borrowedCopies > 0){
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

}
