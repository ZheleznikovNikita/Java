package order.services;

import order.models.Order;

public class OrderService {
    public static void printOrderDetails(Order order) {
        System.out.println("Заказ: " + order.getOrderId());
        System.out.println("Дата: " + order.getFormattedDate());
        System.out.println("Клиент: " + order.getCustomer().getName());
        System.out.println("Позиции:");
        for (var item : order.getItems())
            System.out.println(item);
        System.out.printf("Итого: %.2f\n\n", order.getTotalAmount());
    }
}
