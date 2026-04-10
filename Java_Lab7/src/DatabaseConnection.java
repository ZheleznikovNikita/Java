import utils.ValueChecker;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private String url, username, password;

    private static String cfgUrl, cfgUsername, cfgPassword;

    static {
        cfgUrl = "jdbc:h2:mem:test";
        cfgUsername = "sa";
        cfgPassword = "";
    }

    private DatabaseConnection() {
        url = cfgUrl;
        username = cfgUsername;
        password = cfgPassword;
    }

    // Геттеры
    public String getUrl() { return url; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public static DatabaseConnection getInstance() {
        if (instance == null)
            instance = new DatabaseConnection();
        return instance;
    }

    public void configure(String url, String username, String password) throws IllegalArgumentException {
        ValueChecker.check_string(url);
        ValueChecker.check_string(username);
        ValueChecker.check_string(password);
        cfgUrl = url;
        cfgUsername = username;
        cfgPassword = password;
    }

    public void connect() { System.out.println("Подключение к БД установлено"); }

    public void disconnect() { System.out.println("Отключено"); }

    public void executeQuery(String sql) throws IllegalArgumentException {
        ValueChecker.check_string(sql);
        System.out.println("Выполнение запроса: " + sql);
    }

    static void main() {
        System.out.println("Первый вызов getInstance():");
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        System.out.println("URL подключения: " + db1.getUrl());

        System.out.println("Второй вызов getInstance():");
        DatabaseConnection db2 = DatabaseConnection.getInstance();

        System.out.println("Проверка идентичности объектов:");
        System.out.println("Ссылки равны? " + (db1 == db2 ? "Да" : "Нет"));
        System.out.println("Хэш-код db1: " + System.identityHashCode(db1));
        System.out.println("Хэш-код db2: " + System.identityHashCode(db2));

        System.out.println("Работа с подключением:");
        db1.connect();
        db1.executeQuery("SELECT * FROM users WHERE role = 'admin'");
        db1.executeQuery("UPDATE logs SET status = 'OK'");
        db1.disconnect();
    }
}
