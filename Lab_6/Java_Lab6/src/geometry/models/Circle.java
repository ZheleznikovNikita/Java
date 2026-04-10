package geometry.models;

public class Circle implements Shape {
    private double radius;

    private static final double PI = 3.141592653589793;

    public Circle(double radius) throws Exception {
        setRadius(radius);
    }

    // Геттер
    public double getRadius() { return radius; }
    // Сеттер
    public void setRadius(double radius) throws Exception {
        if (radius <= 0)
            throw new Exception("Радиус должен быть положительным");
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return PI * Math.pow(radius, 2);
    }

    @Override
    public double getPerimeter() {
        return 0;
    }

    @Override
    public String toString() {
        return "Круг радиуса " + radius;
    }
}
