package order.models;

import order.utils.OrderIdGenerator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class Order {
    private long orderId;
    private Customer customer;
    private ArrayList<OrderItem> items = new ArrayList<>();
    private LocalDateTime orderDate;

    private static int totalOrders = 0;

    public Order(Customer customer) throws Exception {
        generateOrderId();
        setCustomer(customer);
        setDate();
        totalOrders++;
    }

    // Геттеры
    public long getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public ArrayList<OrderItem> getItems() { return items; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public static int getTotalOrders() { return totalOrders; }
    // Сеттеры
    public void setCustomer(Customer customer) throws Exception {
        if (customer == null)
            throw new Exception("Передано пустое значение");
        this.customer = customer;
    }
    private void setDate() { orderDate = LocalDateTime.now(); }

    private void generateOrderId() { orderId = Math.abs(OrderIdGenerator.generateOrderItemId() + new Random().nextLong()); }

    public void addOrderItem(OrderItem orderItem) throws Exception {
        if (orderItem == null)
            throw new Exception("Передано пустое значение");
        items.add(orderItem);
    }

    public double getTotalAmount() {
        double sum = 0.0;
        for (var item : items)
            sum += item.getTotalPrice();
        return sum;
    }

    // Если передан скидка в формате 1..100
    public void applyDiscount(int percent) throws Exception {
        if (percent < 0 || percent > 100)
            throw new Exception("Скидка не может быть отрицательной или больше 100");
        for (var item : items)
            item.setPrice(item.getPrice() * (1.0 - percent / 100.0));
    }
    // Если скидка передана в формате 0,0..1,0
    public void applyDiscount(double percent) throws Exception {
        if (percent < 0 || percent > 1.0)
            throw new Exception("Скидка не может быть отрицательной или больше 100");
        for (var item : items)
            item.setPrice(item.getPrice() * (1.0 - percent));
    }

    public String getFormattedDate() { return orderDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")); }

}
