import utils.ValueChecker;

public class ImmutableRectangle {
    private final double width;
    private final double height;

    public ImmutableRectangle(double width, double height) throws IllegalArgumentException {
        ValueChecker.check_low_equal_zero_double(width);
        ValueChecker.check_low_equal_zero_double(height);
        this.width = width;
        this.height = height;
    }

    // Геттеры
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    public double getArea() { return width * height; }

    public double getPerimeter() { return (width + height) * 2; }

    public ImmutableRectangle withWidth(double newWidth) throws IllegalArgumentException { return new ImmutableRectangle(newWidth, height); }

    public ImmutableRectangle withHeight(double newHeight) throws IllegalArgumentException { return new ImmutableRectangle(width, newHeight); }

    @Override
    public String toString() {
        return "Width: " + width +
                "\nHeight: " + height +
                "\nArea: " + getArea() +
                "\nPerimeter: " + getPerimeter();
    }

    static void main() {
        ImmutableRectangle rect1 = new ImmutableRectangle(5.0, 3.0);
        System.out.println("Исходный объект:");
        System.out.println(rect1);

        ImmutableRectangle rect2 = rect1.withWidth(10.0);
        System.out.println("\nНовый объект:");
        System.out.println(rect2);

        System.out.println("\nИсходный объект после вызова withWidth:");
        System.out.println(rect1);

        System.out.println("\nОбъект после изменения ширины и длины");
        ImmutableRectangle rect3 = rect1.withWidth(7.0).withHeight(4.0);
        System.out.println(rect3);

        try {
            new ImmutableRectangle(-2.0, 3.0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
