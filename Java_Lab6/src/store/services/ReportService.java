package store.services;

import store.models.Product;

import java.util.List;

public class ReportService {
    public static void printLowStockReport(List<Product> lowStockProducts, int threshold) {
        System.out.println("Отчёт по товарам с низким остатком (менее " + threshold + " единиц):");
        if (lowStockProducts.isEmpty())
            System.out.println("Все товары в достаточном количестве.");
        else
            for (Product p : lowStockProducts)
                System.out.printf("%s | Остаток: %d | Категория: %s\n", p.getName(), p.getQuantity(), p.getCategory().getCategoryName());
    }
}
