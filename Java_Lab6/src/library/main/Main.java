package library.main;

import library.models.Book;
import library.models.Reader;
import library.services.LibraryService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main() {
        try {
            List<Book> library = new ArrayList<>();
            library.add(new Book("Чистый код", "Роберт Мартин", 2008));
            library.add(new Book("Эффективный Java", "Джошуа Блох", 2018));
            library.add(new Book("Совершенный код", "Стив Макконнелл", 2004));
            library.add(new Book("Паттерны проектирования", "Эрик Фриман", 2004));
            library.add(new Book("Алгоритмы", "Кормен", 2010));

            Reader reader1 = new Reader("Иванов Иван");
            Reader reader2 = new Reader("Пётр  Петрович");

            System.out.println("Выдача книг");
            reader1.borrowBook(library.get(0));
            reader1.borrowBook(library.get(2));
            reader2.borrowBook(library.get(0));
            reader2.borrowBook(library.get(1));

            System.out.println("\nСтатистика после выдачи");
            System.out.println("Доступно книг: " + LibraryService.getAvailableBooks(library).size());

            reader2.returnBook(library.get(1));
            reader2.borrowBook(library.get(1));
            Book popular = LibraryService.getMostPopularBook(library);
            System.out.println("Самая популярная: " + popular.getTitle() + " (взяли " + popular.getBorrowCount() + " раз)");

            List<Book> blochBooks = LibraryService.searchByAuthor(library, "Джошуа Блох");
            System.out.println("Книги Джошуа Блоха: " + blochBooks.size());

            System.out.println("\nВозврат и новая выдача");
            reader1.returnBook(library.get(0)); // Возвращает "Чистый код"
            reader2.borrowBook(library.get(0)); // Теперь должно пройти успешно

            System.out.println("\nИтоговая информация");
            System.out.println("Всего книг: " + Book.getTotalBooks());
            System.out.println("Всего читателей: " + Reader.getTotalReaders());
            System.out.println("У " + reader1.getFullName() + " на руках книг: " + reader1.getBookCount());
            System.out.println("У " + reader2.getFullName() + " на руках книг: " + reader2.getBookCount());
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
