package order.models;

import order.utils.OrderIdGenerator;

import java.util.ArrayList;

public class Customer {
    private long customerId;
    private String name;
    private ArrayList<Order> orders = new ArrayList<>();

    private static int totalCustomers = 0;

    public Customer(String name) throws Exception {
        setCustomerId();
        setName(name);
        totalCustomers++;
    }

    // Геттеры
    public long getCustomerId() { return customerId; }
    public String getName() { return name; }
    public ArrayList<Order> getOrders() { return orders; }
    public int getOrderCount() { return orders.size(); }
    public static int getTotalCustomers() { return totalCustomers; }
    // Сеттеры
    private void setCustomerId() { customerId = Math.abs(OrderIdGenerator.generateOrderItemId() + System.currentTimeMillis()); }
    public void setName(String name) throws Exception {
        if (name == null || name.isEmpty())
            throw new Exception("Передпно пустое имя");
        this.name = name;
    }

    public void addOrder(Order order) throws Exception {
        if (order == null)
            throw new Exception("Передано пустое значение");
        orders.add(order);
    }

    public double getTotalSpent() {
        double sum = 0.0;
        for (var order : orders)
            sum += order.getTotalAmount();
        return sum;
    }
}
