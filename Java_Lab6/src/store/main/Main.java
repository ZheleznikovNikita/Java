package store.main;

import store.models.*;
import store.services.*;
import store.utils.*;
import java.util.List;

public class Main {
    static void main() {
        try {
            Category electronics = new Category("Электроника", "Гаджеты и устройства");
            Category books = new Category("Книги", "Художественная и учебная литература");

            Supplier techSupplier = new Supplier("Samsung", "+7-999-111-22-33");
            Supplier bookSupplier = new Supplier("BookWorld", "+7-999-444-55-66");

            Product laptop = new Product("Ноутбук", 120000.0, 5, electronics, techSupplier);
            Product phone = new Product("Смартфон", 80000.0, 12, electronics, techSupplier);
            Product javaBook = new Product("Java для начинающих", 1500.0, 20, books, bookSupplier);
            Product novel = new Product("Мастер и Маргарита", 850.0, 3, books, bookSupplier);

            InventoryService.addProduct(laptop);
            InventoryService.addProduct(phone);
            InventoryService.addProduct(javaBook);
            InventoryService.addProduct(novel);

            System.out.println("\nОперации на складе");
            InventoryService.removeProduct(laptop, 2);
            InventoryService.removeProduct(novel, 5);

            List<Product> currentStock = InventoryService.getWarehouse();
            double totalValue = PriceCalculator.calculateTotalPrice(currentStock);
            System.out.printf("\nОбщая стоимость товаров на складе: %.2f руб.\n", totalValue);

            int lowStockThreshold = 5;
            List<Product> lowStockItems = InventoryService.checkLowStock(lowStockThreshold);
            ReportService.printLowStockReport(lowStockItems, lowStockThreshold);

            System.out.println("\nИтоговая информация");
            System.out.println("Всего категорий: " + Category.getTotalCategories());
            System.out.println("Всего товаров: " + Product.getTotalProducts());
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
