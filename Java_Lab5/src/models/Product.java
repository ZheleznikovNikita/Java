package models;

import utils.ValidationUtils;

public class Product {
    // Поля
    private String name;
    private double price;
    private int quantityInStock;
    private String category;

    static private final int min_quantity = 0;

    // Конструктор
    public Product(String name, double price, int quantityInStock, String category) throws Exception {
        setName(name);
        setPrice(price);
        setQuantityInStock(quantityInStock);
        setCategory(category);
    }

    // Геттеры
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantityInStock() { return quantityInStock; }
    public String getCategory() { return category; }

    // Сеттеры
    public void setName(String name) throws Exception {
        ValidationUtils.check_name(name, "Имя не может быть пустым");
        this.name = name;
    }
    public void setPrice(double price) throws Exception {
        ValidationUtils.check_number_less_or_equal(price, "Цена должна быть положительным");
        this.price = price;
    }
    public void setQuantityInStock(int quantityInStock) throws Exception {
        ValidationUtils.check_number_less(quantityInStock, "Количество не может быть отрицательным");
        this.quantityInStock = quantityInStock;
    }
    public void setCategory(String category) throws Exception {
        ValidationUtils.check_name(category, "Категория не может быть пустой");
        this.category = category;
    }

    // Проверка наличия на складе
    public boolean isAvailable() { return quantityInStock > 0; }
    // Уменьшения количества на складе
    public void reduceStock(int quantity) throws Exception {
        ValidationUtils.check_number_less(quantity, "Нельзя взять отрицательное количество");
        if (quantity == min_quantity)
            System.out.println("Зачем?");
        else if (quantity <= getQuantityInStock())
            quantityInStock -= quantity;
        else
            setQuantityInStock(min_quantity);
    }
    // Уменьшения количества на складе
    public void increaseStock(int quantity) throws Exception {
        ValidationUtils.check_number_less(quantity, "Нельзя вернуть отрицательное количество");
        if (quantity == min_quantity)
            System.out.println("Зачем?");
        quantityInStock += quantity;
    }
    // Вывод информации
    public void printInfo() {
        System.out.println("Название: " + getName());
        System.out.println("Цена: " + getPrice());
        System.out.println("Количество на складе: " + getQuantityInStock());
        System.out.println("Категория: " + getCategory());
    }
}

// Добавлена константа для минимально возможного количества товара на складе