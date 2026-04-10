
public class Main {
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
