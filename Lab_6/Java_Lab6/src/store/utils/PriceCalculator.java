package store.utils;

import store.models.Product;
import java.util.List;

public class PriceCalculator {
    public static double calculateTotalPrice(List<Product> products) throws Exception {
        if (products == null || products.isEmpty())
            throw new Exception("Передан пустой список");
        double res = 0.0;
        for (var product : products)
            res += product.getPrice() * product.getQuantity();
        return res;
    }
}
