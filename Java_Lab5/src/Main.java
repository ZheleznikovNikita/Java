void main() {
    // Задание 1 "Класс Book"
    try {
        Book b1 = new Book("Война и мир", "Лев Толстой", 1869, 1200, "Азбука", true);
        Book b2 = new Book("1984", "Джордж Оруэлл", 1949, 328, false);
        Book b3 = new Book("Гарри Поттер", "Дж. К. Роулинг", 1997, 300, "Росмэн", false);
        Book b4 = new Book("Старинный манускрипт", "Неизвестный автор", 1451, 50, true);
        Book[] library = { b1, b2, b3, b4 };
        System.out.println("---Информация о книгах---");
        for (var elem : library) {
            elem.printInfo();
            System.out.println("Эта книга " + (elem.isOld() ? "старая\n" : "новая\n"));
        }
        System.out.println("Возврат и регистрация книги");
        library[0].borrow();
        library[1].borrow();
        library[2].returnBook();
        library[3].returnBook();
    }
    catch (Exception e) {
        System.err.println(e.getMessage());
    }
    // Задание 2 "Класс  Student"
    try{
        Student[] students = new Student[3];
        students[0] = new Student("Иванов Иван Иванович", "Группа ФИИТ-1", 1001, new int[]{5, 5, 5, 5, 5});
        students[1] = new Student("Петров Петр Петрович", "Группа ФИИТ-2", 1002, new int[]{4, 5, 4, 5, 4});
        students[2] = new Student("Человеков Человек Человекович", "Группа ПМИ-3", 1003,  new int[]{3, 3, 4, 3, 3});

        System.out.println("---Информация о всех студентах---");
        for (Student student : students) {
            student.printInfo();
            System.out.println();
        }

        System.out.println("Изменение статуса стипендии");

        System.out.println("Студент: " + students[1].getFullName());
        System.out.println("До изменения оценок:");
        System.out.println("Средний балл: " + String.format("%.2f", students[1].calculateAverage()));
        System.out.println("Стипендия: " + (students[1].getHasScholarship() ? "Получает" : "Не получает"));

        students[1].setGrade(5, 0);
        students[1].setGrade(5, 2);

        System.out.println("После изменения оценок:");
        System.out.println("Средний балл: " + String.format("%.2f", students[1].calculateAverage()));
        System.out.println("Стипендия: " + (students[1].getHasScholarship() ? "Получает" : "Не получает"));
        System.out.println();

        System.out.println("Студент: " + students[0].getFullName());
        System.out.println("Является ли отличником: " + students[0].isExcellent());

        students[0].setGrade(4, 0);
        System.out.println("Является ли отличником: " + students[0].isExcellent());
        System.out.println("Стипендия: " + (students[0].getHasScholarship() ? "Получает" : "Не получает"));
        System.out.println();

        for (Student student : students) {
            student.printInfo();
            System.out.println();
        }
    }
    catch (Exception e){
        System.err.println(e.getMessage());
    }
    // Задание 3 "Класс BankAccount"
    try{
        // Создаем несколько счетов
        BankAccount account1 = new BankAccount("1111-2222-3333", "Иванов Иван", 10000.0, 5.0);
        BankAccount account2 = new BankAccount("4444-5555-6666", "Петров Петр", 5000.0, 3.5);
        BankAccount account3 = new BankAccount("7777-8888-9999", "Человеков Человек", 20000.0, 4.0);

        // Вывод информации о всех счетах
        System.out.println("---Информация о всех счетах---\n");
        account1.printInfo();
        account2.printInfo();
        account3.printInfo();

        System.out.println("Операции пополнения и снятия");

        // Пополнение счета
        System.out.println("Пополнение счета Иванова:");
        account1.deposit(5000.0);
        account1.printInfo();

        // Снятие со счета
        account1.withdraw(2000.0);
        account1.printInfo();

        System.out.println("Операция перевода между счетами");

        account1.transfer(account3, 3000.0);
        account1.printInfo();
        account3.printInfo();

        System.out.println("Начисление процентов");

        account1.applyInterest();
        account1.printInfo();
        account2.applyInterest();
        account2.printInfo();
        account3.applyInterest();
        account3.printInfo();

        System.out.println("Закрытие счета");

        account2.closeAccount();
        account2.printInfo();

        System.out.println("---Итоговая информация о всех счетах---");
        account1.printInfo();
        account2.printInfo();
        account3.printInfo();
    }
    catch (Exception e){
        System.err.println(e.getMessage());
    }
    // Задание 4 "Класс Employee"
    try{
        Employee employee1 = new Employee(
                "Иванов Иван Иванович",
                "Программист",
                "IT-отдел",
                80000.0,
                LocalDate.of(2018, 5, 15)
        );
        Employee employee2 = new Employee(
                "Петров Петр Петрович",
                "Менеджер",
                "Отдел продаж",
                120000.0,
                LocalDate.of(2015, 3, 10),
                10000.0
        );
        Employee employee3 = new Employee(
                "Человеков Человек Человекович",
                "Бухгалтер",
                "Финансовый отдел",
                60000.0,
                LocalDate.of(2022, 11, 1)
        );

        System.out.println("---Информация о всех сотрудниках---");
        employee1.printInfo();
        System.out.println();
        employee2.printInfo();
        System.out.println();
        employee3.printInfo();
        System.out.println();

        System.out.println("Изменение надбавки");

        System.out.println("Увеличение надбавки для " + employee3.getFullName());
        employee3.setBonus(15000.0);
        System.out.println("Новая надбавка: " + employee3.getBonus());
        System.out.println("Общий доход: " + String.format("%.2f", employee3.getTotalSalary()));
        System.out.println();


        System.out.println("Повышение зарплаты для " + employee1.getFullName());
        employee1.setSalary(110000.0);
        System.out.println("Новая зарплата: " + employee1.getSalary());
        System.out.println("Теперь подходит для повышения: " + employee1.isEligibleForPromotion());
        System.out.println();

        System.out.println("Итоговая информация о сотрудниках 1 и 3");
        employee1.printInfo();
        System.out.println();
        employee3.printInfo();
        System.out.println();
    }
    catch (Exception e){
        System.err.println(e.getMessage());
    }
    // Задание 5 "Класс Product и ShoppingCart"
    try{
        Product product1 = new Product("Ноутбук", 75000.0, 5, "Электроника");
        Product product2 = new Product("Мышь беспроводная", 2500.0, 20, "Электроника");
        Product product3 = new Product("Клавиатура", 4500.0, 15, "Электроника");
        Product product4 = new Product("Монитор", 15000.0, 8, "Электроника");
        Product product5 = new Product("USB-кабель", 500.0, 50, "Аксессуары");

        // Вывод информации о всех товарах
        System.out.println("Информация о товарах");
        product1.printInfo();
        System.out.println();
        product2.printInfo();
        System.out.println();
        product3.printInfo();
        System.out.println();
        product4.printInfo();
        System.out.println();
        product5.printInfo();
        System.out.println();

        ShoppingCart cart = new ShoppingCart();

        System.out.println("Добавление товаров в корзину");

        cart.addProduct(product1, 1);
        cart.addProduct(product2, 2);
        cart.addProduct(product3, 1);
        cart.addProduct(product5, 3);

        System.out.println("Содержимое корзины");
        cart.printCart();

        System.out.println("Товары после добавления в корзину");
        product1.printInfo();
        System.out.println();
        product2.printInfo();
        System.out.println();
        product5.printInfo();
        System.out.println();

        System.out.println("Удаление товара из корзины");
        cart.removeProduct("Мышь беспроводная");
        cart.printCart();

        System.out.println("Товары на складе после удаления из корзины");
        product2.printInfo();
        System.out.println();

        cart.addProduct(product4, 2);

        System.out.println("Итоговая корзина");
        cart.printCart();

        System.out.println("Оформление заказа");
        cart.checkout();

        System.out.println("Корзина после оформления заказ");
        cart.printCart();

        System.out.println("Итоговые остатки на складе");
        product1.printInfo();
        System.out.println();
        product2.printInfo();
        System.out.println();
        product3.printInfo();
        System.out.println();
        product4.printInfo();
        System.out.println();
        product5.printInfo();
        System.out.println();

        System.out.println("Добавление в оформленную корзину");
        cart.addProduct(product1, 1); // Теперь можно добавить, корзина пуста
        cart.printCart();
    }
    catch (Exception e){
        System.err.println(e.getMessage());
    }
    // Задание 6 "Класс Time"
    try{
        Time time1 = new Time(14, 30, 45);
        Time time2 = new Time(9, 15, 20);
        Time time3 = new Time(23, 59, 50);
        Time currentTime = new Time();

        System.out.println("Время 1: ");
        time1.print24h();
        System.out.print(" | ");
        time1.print12h();
        System.out.println();

        System.out.println("Время 2: ");
        time2.print24h();
        System.out.print(" | ");
        time2.print12h();
        System.out.println();

        System.out.println("Время 3: ");
        time3.print24h();
        System.out.print(" | ");
        time3.print12h();
        System.out.println();

        System.out.println("Текущее время: ");
        currentTime.print24h();
        System.out.print(" | ");
        currentTime.print12h();
        System.out.println();

        System.out.println("addSeconds()");

        System.out.print("Время 1 до добавления секунд: ");
        time1.print24h();
        System.out.println();

        time1.addSeconds(30);
        System.out.print("После добавления 30 секунд: ");
        time1.print24h();
        System.out.println();

        time1.addSeconds(45);
        System.out.print("После добавления 45 секунд: ");
        time1.print24h();
        System.out.println();
        System.out.println();

        System.out.println("addMinutes()");

        System.out.print("Время 2 до добавления минут: ");
        time2.print24h();
        System.out.println();

        time2.addMinutes(30);
        System.out.print("После добавления 30 минут: ");
        time2.print24h();
        System.out.println();

        time2.addMinutes(45);
        System.out.print("После добавления 45 минут: ");
        time2.print24h();
        System.out.println();
        System.out.println();

        System.out.println("addHours()");

        System.out.print("Время 3 до добавления часов: ");
        time3.print24h();
        System.out.println();

        time3.addHours(1);
        System.out.print("После добавления 1 часа: ");
        time3.print24h();
        System.out.println();

        time3.addHours(25);
        System.out.print("После добавления 25 часов: ");
        time3.print24h();
        System.out.println();
        System.out.println();

        System.out.println("differenceInSeconds()");

        Time time4 = new Time(10, 0, 0);
        Time time5 = new Time(12, 30, 45);

        System.out.print("Время 4: ");
        time4.print24h();
        System.out.println();

        System.out.print("Время 5: ");
        time5.print24h();
        System.out.println();

        int diff = time4.differenceInSeconds(time5);
        System.out.println("Разница между временем 4 и 5: " + diff + " секунд");

        Time midnight = new Time(0, 0, 0);
        Time noon = new Time(12, 0, 0);
        Time evening = new Time(18, 30, 15);

        System.out.println("AM и PM");
        System.out.print("Полночь: ");
        midnight.print12h();
        System.out.println();

        System.out.print("Полдень: ");
        noon.print12h();
        System.out.println();

        System.out.print("Вечер: ");
        evening.print12h();
        System.out.println();
        System.out.println();
    }
    catch (Exception e){
        System.err.println(e.getMessage());
    }
}