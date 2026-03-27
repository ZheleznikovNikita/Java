public class Product {
    // Поля
    private String name;
    private double price;
    private int quantityInStock;
    private String category;

    // Конструктор
    public Product(String name, double price, int quantityInStock, String category) throws Exception {
        setName(name);
        setPrice(price);
        setQuantityInStock(quantityInStock);
        setCategory(category);
    }

    // Геттеры
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantityInStock() { return quantityInStock; }
    public String getCategory() { return category; }

    // Сеттеры
    public void setName(String name) throws Exception {
        if (name == null)
            throw new Exception("Имя не может быть пустым");
        this.name = name;
    }
    public void setPrice(double price) throws Exception {
        if (price <= 0)
            throw new Exception("Цена должна быть положительным");
        this.price = price;
    }
    public void setQuantityInStock(int quantityInStock) throws Exception {
        if (quantityInStock < 0)
            throw new Exception("Количество не может быть отрицательным");
        this.quantityInStock = quantityInStock;
    }
    public void setCategory(String category) throws Exception {
        if (category == null)
            throw new Exception("Категория не может быть пустой");
        this.category = category;
    }

    // Проверка наличия на складе
    public boolean isAvailable() { return quantityInStock > 0; }
    // Уменьшения количества на складе
    public void reduceStock(int quantity) throws Exception {
        if (quantity < 0)
            throw new Exception("Нельзя взять отрицательное количество");
        if (quantity == 0)
            System.out.println("Зачем?");
        else if (quantity <= getQuantityInStock())
            quantityInStock -= quantity;
        else
            setQuantityInStock(0);
    }
    // Уменьшения количества на складе
    public void increaseStock(int quantity) throws Exception {
        if (quantity < 0)
            throw new Exception("Нельзя вернуть отрицательное количество");
        if (quantity == 0)
            System.out.println("Зачем?");
        quantityInStock += quantity;
    }
    // Вывод информации
    public void printInfo() {
        System.out.println("Название: " + getName());
        System.out.println("Цена: " + getPrice());
        System.out.println("Количество на складе: " + getQuantityInStock());
        System.out.println("Категория: " + getCategory());
    }
}
