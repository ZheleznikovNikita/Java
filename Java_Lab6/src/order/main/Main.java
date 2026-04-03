package order.main;

import order.models.*;
import order.services.*;

public class Main {
    static void main() {
        try {
            Customer fst = new Customer("Customer One");
            Customer snd = new Customer("Customer Two");

            Order order1 = new Order(fst);
            order1.addOrderItem(new OrderItem("Ноутбук", 95000.0, 1));
            order1.addOrderItem(new OrderItem("Мышь беспроводная", 2500.0, 2));
            fst.addOrder(order1);

            Order order2 = new Order(fst);
            order2.addOrderItem(new OrderItem("Монитор", 32000.0, 1));
            fst.addOrder(order2);

            Order order3 = new Order(snd);
            order3.addOrderItem(new OrderItem("Клавиатура", 7800.0, 1));
            order3.addOrderItem(new OrderItem("Коврик", 1500.0, 3));
            snd.addOrder(order3);

            System.out.println("Заказы до применения скидок");
            OrderService.printOrderDetails(order1);
            OrderService.printOrderDetails(order3);

            System.out.println("Применение скидок");
            double seasonal = DiscountService.getSeasonalDiscount();
            System.out.println("Сезонная скидка: " + (seasonal * 100) + "%");
            order1.applyDiscount(seasonal);

            double loyaltyFst = DiscountService.getLoyaltyDiscount(snd.getOrderCount());
            System.out.println("Скидка за лояльность для первого: " + (loyaltyFst * 100) + "%");

            System.out.println("\nИтоговая статистика");
            OrderService.printOrderDetails(order1);

            System.out.printf("Всего потрачено вторым: %.2f\n", fst.getTotalSpent());
            System.out.printf("Всего потрачено первым: %.2f\n", snd.getTotalSpent());
            System.out.println("Всего клиентов: " + Customer.getTotalCustomers());
            System.out.println("Всего заказов:  " + Order.getTotalOrders());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
