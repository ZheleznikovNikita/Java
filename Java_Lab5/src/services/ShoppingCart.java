package services;

import models.Product;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import utils.ValidationUtils;

public class ShoppingCart {
    // Поля
    private Map<Product, Integer> items;

    // Конструктор
    public ShoppingCart() { items = new HashMap<>(); }

    // Добавление продукта в корзину
    public void addProduct(Product product, int quantity) throws Exception {
        ValidationUtils.check_number_less_or_equal(quantity, "Количество должно быть положительным");
        if (!product.isAvailable()) {
            throw new Exception("Нельзя взять товар, которого нет на складе");
        }
        if (product.getQuantityInStock() < quantity)
            throw new Exception("Товара не хватает на складе");
        if (items.containsKey(product)) {
            items.compute(product, (key, value) -> value += quantity);
            product.reduceStock(quantity);
        }
        else {
            items.put(product, quantity);
            product.reduceStock(quantity);
        }
    }
    // Удаление продукта из корзины
    public void removeProduct(String productName) throws Exception {
        ValidationUtils.check_name(productName, "Имя товара не может быть пустым");
        Map.Entry<Product, Integer> p = null;
        for (var elem : items.entrySet())
            if (Objects.equals(elem.getKey().getName(), productName))
                p = elem;
        if (p != null) {
            items.remove(p.getKey());
            p.getKey().increaseStock(p.getValue());
        }
    }
    // Общая стоимость товаров
    public double getTotalPrice() {
        double sum = 0.0;
        for (var elem : items.entrySet())
            sum += elem.getKey().getPrice() * elem.getValue();
        return sum;
    }
    // Оформление заказа
    public void checkout() {
        if (items.isEmpty())
            System.out.println("Вы ничего не купили :(");
        else {
            items.clear();
            System.out.println("Заказ оформлен");
        }
    }
    // Вывод информации о корзине
    public void printCart() {
        for (var elem : items.entrySet()){
            elem.getKey().printInfo();
            System.out.println("Количество в корзине: " + elem.getValue() + "\n");
        }
        System.out.println("Общая цена: " + getTotalPrice());
    }
}
