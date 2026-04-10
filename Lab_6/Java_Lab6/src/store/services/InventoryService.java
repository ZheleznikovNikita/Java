package store.services;

import store.models.Product;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    private static List<Product> warehouse = new ArrayList<>();

    // Геттер
    public static List<Product> getWarehouse() { return warehouse; }

    public static void addProduct(Product product) throws Exception {
        if (product == null)
            throw new Exception("Передано пустое значение");
        if (warehouse.contains(product)) {
            System.out.println("Товар уже есть на складе");
            return;
        }
        warehouse.add(product);
        System.out.println("Товар добавлен на склад");
    }

    public static void removeProduct(Product product, int quantity) throws Exception {
        if (product == null)
            throw new Exception("Передано пустое значение");
        if (quantity <= 0)
            throw new Exception("Количество должно быть положительным");
        if (!warehouse.contains(product)){
            System.out.println("Товара нет на складе");
            return;
        }
        for (var prod : warehouse) {
            if (prod == product && quantity <= prod.getQuantity()){
                prod.setQuantity(prod.getQuantity() - quantity);
                warehouse.remove(product);
                System.out.println("Товар удалён со склада");
                return;
            }
            else if (quantity > prod.getQuantity()){
                System.out.println("ТОвара недостаточно на складе");
                return;
            }
        }
    }

    public static List<Product> checkLowStock(int threshold) throws Exception {
        if (threshold <= 0)
            throw new Exception("Количество должно быть положительным");
        List<Product> res = new ArrayList<>();
        for (var product : warehouse)
            if (product.getQuantity() < threshold)
                res.add(product);
        return res;
    }
}
