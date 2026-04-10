import utils.ValueChecker;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Car {
    private String model;
    private String color;
    private int year;

    private static Set<String> availableColors;

    static {
        availableColors = new HashSet<String>();
        Collections.addAll(availableColors,  "Red", "Blue", "Black", "White",  "Silver");
    }

    public Car(String model, String color, int year) throws IllegalArgumentException {
        setModel(model);
        setColor(color);
        setYear(year);
    }

    // Геттеры
    public String getModel() { return model; }
    public String getColor() { return color; }
    public int getYear() { return year; }
    // Сеттеры
    public void setModel(String model) throws IllegalArgumentException {
        ValueChecker.check_string(model);
        this.model = model;
    }
    public void setColor(String color) throws IllegalArgumentException {
        ValueChecker.check_string(color);
        if (!availableColors.contains(color))
            throw new IllegalArgumentException("Цвета нет в доступных");
        this.color = color;
    }
    public void setYear(int year) throws IllegalArgumentException {
        if (year < 1885 || year > 2026)
            throw new IllegalArgumentException("Некорректный год");
        this.year = year;
    }

    public void changeColor(String color) throws IllegalArgumentException { setColor(color); }

    public static void addNewColor(String color) {
        if (availableColors.contains(color)) {
            System.out.println("Цвет уже есть");
            return;
        }
        availableColors.add(color);
    }

    @Override
    public String toString() {
        return "Model: " + model +
                "\nColor: " + color +
                "\nYear: " + year;
    }

    static void main() {
        Car car1 = new Car("Toyota Camry", "Red", 2023);
        System.out.println("Успешно создан: " + car1);

        System.out.println("Попытка создать с недопустимым цветом 'Green':");
        try {
            Car invalidCar = new Car("Ford Focus", "Green", 2022);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        System.out.println("Добавляем новый цвет 'Green'");
        Car.addNewColor("Green");
        System.out.println("Цвет добавлен");

        Car car2 = new Car("Ford Focus", "Green", 2022);
        System.out.println("Успешно создан с новым цветом: " + car2);

        System.out.println("Смена цвета:");
        car2.changeColor("Silver");
        System.out.println("Результат: " + car2);

        System.out.println("Попытка сменить на неизвестный 'Orange':");
        try {
            car2.changeColor("Orange");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
