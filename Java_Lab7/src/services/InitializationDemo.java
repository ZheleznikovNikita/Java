package services;

public class InitializationDemo {
    private static final String staticField = initStaticField();
    private final String instanceField = initInstanceField();

    public static String initStaticField() {
        System.out.println("Static func returns \"static\"");
        return "static";
    }
    public String initInstanceField() {
        System.out.println("Not static func returns \"instance\"");
        return "instance";
    }

    static {
        System.out.println("Статический блок инициализации");
    }

    {
        System.out.println("Нестатический блок инициализации");
    }

    public InitializationDemo(String name) {
        System.out.println("Конструктор от name: " + name);
    }
    public InitializationDemo() {
        System.out.println("Конструктор по умолчанию");
        this("default");
    }

    static void main() {
        System.out.println("Создание объекта 1:");
        InitializationDemo obj1 = new InitializationDemo("FirstObject");

        System.out.println("\nСоздание объекта 2:");
        InitializationDemo obj2 = new InitializationDemo();
    }
    /*Тут сначала инициализируются статические поля класса, затем вызываются конструкторы и блоки инциализации*/
}

class Task3 {
    static void main() {
        System.out.println("Создание объекта 1:");
        InitializationDemo obj1 = new InitializationDemo("FirstObject");

        System.out.println("\nСоздание объекта 2:");
        InitializationDemo obj2 = new InitializationDemo();
    }
}

/*Тут при первом создании объекта инициализируются статические поля, потом аналогично*/