package store.models;

import java.util.Random;

public class Product {
    private long productId;
    private String name;
    private double price;
    private int quantity;
    private Category category;
    private Supplier supplier;

    private static int totalProducts = 0;

    public Product(String name, double price, int quantity, Category category, Supplier supplier) throws Exception {
        setProductId();
        setName(name);
        setPrice(price);
        setQuantity(quantity);
        setCategory(category);
        setSupplier(supplier);
        supplier.addProduct(this);
        totalProducts++;
    }

    // Геттеры
    public long getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public Category getCategory() { return category; }
    public Supplier getSupplier() { return supplier; }
    public static int getTotalProducts() { return totalProducts; }
    // Сеттеры
    private void setProductId() { productId = System.currentTimeMillis() + new Random().nextLong(); }
    public void setName(String name) throws Exception {
        if (name == null || name.isEmpty())
            throw new Exception("Передана пустая строка");
        this.name = name;
    }
    public void setPrice(double price) throws Exception {
        if (price <= 0)
            throw new Exception("Цена должна быть положительная");
        this.price = price;
    }
    public void setQuantity(int quantity) throws Exception {
        if (quantity < 0)
            throw new Exception("Количество не может быть отрицательным");
        this.quantity = quantity;
    }
    public void setCategory(Category category) throws Exception {
        if (category == null)
            throw new Exception("Передано пустое значение");
        this.category = category;
    }
    public void setSupplier(Supplier supplier) throws Exception {
        if (supplier == null)
            throw new Exception("Передано пустое значение");
        this.supplier = supplier;
    }

    public boolean isInStock() { return quantity > 0; }
}
