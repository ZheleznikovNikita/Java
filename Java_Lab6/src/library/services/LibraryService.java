package library.services;

import library.models.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LibraryService {
    public static List<Book> getAvailableBooks(List<Book> books) throws Exception {
        if (books == null || books.isEmpty())
            throw new Exception("Подан пустой список");
        List<Book> res = new ArrayList<>();
        for (var book : books)
            if (book.getIsAvailable())
                res.add(book);
        return res;
    }

    public static Book getMostPopularBook(List<Book> books) throws Exception {
        if (books == null || books.isEmpty())
            throw new Exception("Подан пустой список");
        Book res = books.get(0);
        for (var book : books)
            if (book.getBorrowCount() > res.getBorrowCount())
                res = book;
        return res;
    }

    public static List<Book> searchByAuthor(List<Book> books, String author) throws Exception {
        if (books.isEmpty())
            throw new Exception("Подан пустой список");
        if (author == null || author.isEmpty())
            throw new Exception("Пустое имя автора");
        List<Book> res = new ArrayList<>();
        for (var book : books)
            if (Objects.equals(book.getAuthor(), author))
                res.add(book);
        return res;
    }
}
