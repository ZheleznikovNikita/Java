package models;

public class Child extends Parent {
    private static final String childStaticField = initChildStaticField();
    private final String childInstanceField = initChildInstanceField();

    static {
        System.out.println("Child static block");
    }
    {
        System.out.println("Child instance block");
    }

    public static String initChildStaticField() {
        System.out.println("Child static field func");
        return "Child static field";
    }
    public String initChildInstanceField() {
        System.out.println("Child instance field func");
        return "Child instance field";
    }

    public Child() {
        System.out.println("Child constructor");
    }

    static void main() {
        Child child = new Child();
        System.out.println("Объект создан.\n");
    }
}

/* Сначала выполняются методы Parent, потом Child для static функций и блоков инициализации, потом аналогично для остального кода*/