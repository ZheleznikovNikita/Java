package order.models;

import java.security.spec.ECField;

public class OrderItem {
    private String productName;
    private double price;
    private int quantity;

    public OrderItem(String productName, double price, int quantity) throws Exception {
        setProductName(productName);
        setPrice(price);
        setQuantity(quantity);
    }

    // Геттеры
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    // Сеттеры
    public void setProductName(String productName) throws Exception {
        if (productName == null || productName.isEmpty())
            throw new Exception("Пустое имя");
        this.productName = productName;
    }
    public void setPrice(double price) throws Exception {
        if (price <= 0)
            throw new Exception("Цена должна быть положительной");
        this.price = price;
    }
    public void setQuantity(int quantity) throws Exception {
        if (quantity < 0)
            throw new Exception("Количество не может быть отрицательным");
        this.quantity = quantity;
    }

    public double getTotalPrice() { return price * quantity; }

    @Override
    public String toString() { return "Товар: " + productName + " Цена: " + price + " Количество: " + quantity ;};
}
