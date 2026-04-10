package geometry.models;

public class Rectangle implements Shape{
    private double width;
    private double height;

    public Rectangle(double width, double height) throws Exception {
        setWidth(width);
        setHeight(height);
    }

    // Геттеры
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    // Сеттеры
    public void setWidth(double width) throws Exception {
        if (width <= 0)
            throw new Exception("Ширина должна быть положительной");
        this.width = width;
    }
    public void setHeight(double height) throws Exception {
        if (height <= 0)
            throw new Exception("Высота должна быть положительной");
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return width * 2 + height * 2;
    }

    @Override
    public String toString() {
        return "Прямоугольник размера " + width + " x " + height;
    }
}
