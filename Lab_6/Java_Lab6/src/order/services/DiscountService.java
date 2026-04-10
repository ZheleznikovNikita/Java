package order.services;

public class DiscountService {
    public static double getSeasonalDiscount() { return 0.1; }

    public static double getLoyaltyDiscount(int orderCount) throws Exception {
        if (orderCount < 0)
            throw new Exception("Количество не может быть отрицательным");
        if (orderCount > 5)
            return 0.05;
        return 0.0;
    }
}
