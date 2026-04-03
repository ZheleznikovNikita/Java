package geometry.services;

import geometry.models.*;

public class ShapeService {
    // Проверки уже есть в конструкторах
    public static Circle createCircle(double radius) throws Exception {
        return new Circle(radius);
    }

    public static Rectangle createRectangle(double width, double height) throws Exception {
        return new Rectangle(width, height);
    }

    public static Triangle createTriangle(double sideA, double sideB, double sideC) throws Exception {
        return new Triangle(sideA, sideB, sideC);
    }
}
