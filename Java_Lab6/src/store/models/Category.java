package store.models;

public class Category {
    private String categoryName;
    private String description;

    private static int totalCategories = 0;

    public Category(String categoryName, String description) throws Exception {
        setCategoryName(categoryName);
        setDescription(description);
        totalCategories++;
    }

    // Геттеры
    public String getCategoryName() { return categoryName; }
    public String getDescription() { return description; }
    public static int getTotalCategories() { return totalCategories; }
    // Сеттеры
    public void setCategoryName(String categoryName) throws Exception {
        check_string(categoryName);
        this.categoryName = categoryName;
    }
    public void setDescription(String description) throws Exception {
        check_string(description);
        this.description = description;
    }

    private void check_string(String s) throws Exception {
        if (s == null || s.isEmpty())
            throw new Exception("Передана пустая строка");
    }
}
