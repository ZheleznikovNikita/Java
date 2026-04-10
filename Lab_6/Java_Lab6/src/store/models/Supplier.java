package store.models;

import java.util.ArrayList;

public class Supplier {
    private String supplierName;
    private String contactInfo;
    private ArrayList<Product> productsSupplied = new ArrayList<>();

    public Supplier(String supplierName, String contactInfo) throws Exception {
        setSupplierName(supplierName);
        setContactInfo(contactInfo);
    }

    // Геттеры
    public String getSupplierName() { return supplierName; }
    public String getContactInfo() { return contactInfo; }
    public ArrayList<Product> getProductsSupplied() { return productsSupplied; }
    // Сеттеры
    public void setSupplierName(String supplierName) throws Exception {
        check_string(supplierName);
        this.supplierName = supplierName;
    }
    public void setContactInfo(String contactInfo) throws Exception {
        check_string(contactInfo);
        this.contactInfo = contactInfo;
    }

    public void addProduct(Product product) throws Exception {
        if (product == null)
            throw new Exception("Передано пустое значение");
        if (productsSupplied.contains(product)) {
            System.out.println("Этот продукт уже есть");
            return;
        }
        productsSupplied.add(product);
        System.out.println("Продукт добавлен");
    }

    private void check_string(String s) throws Exception {
        if (s == null || s.isEmpty())
            throw new Exception("Передана пустая строка");
    }
}
