package models;

public class Parent {
    private static final String parentStaticField = initParentStaticField();
    private final String parentInstanceField = initParentInstanceField();

    static {
        System.out.println("Parent static block");
    }
    {
        System.out.println("Parent instance block");
    }

    public static String initParentStaticField() {
        System.out.println("Parent static field func");
        return "Parent static field";
    }
    public String initParentInstanceField() {
        System.out.println("Parent instance field func");
        return "Parent instance field";
    }

    public Parent() {
        System.out.println("Parent constructor");
    }
}
