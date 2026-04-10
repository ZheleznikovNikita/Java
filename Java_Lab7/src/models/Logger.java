package models;

import utils.ValueChecker;
import utils.ValueChecker.*;

public class Logger {
    private static int totalMessages = 0;
    private static int errorCount = 0;
    private static int warningCount = 0;

    public static void logInfo(String message) {
        ValueChecker.check_string(message);
        System.out.println("[INFO] " + message);
        totalMessages++;
    }

    public static void logWarning(String message) {
        ValueChecker.check_string(message);
        System.out.println("[WARNING] " + message);
        totalMessages++;
        warningCount++;
    }

    public static void logError(String message) {
        ValueChecker.check_string(message);
        System.out.println("[ERROR] " + message);
        totalMessages++;
        errorCount++;
    }

    public static String getStats() {
        return String.format("Всего сообщений: %d, из них ошибок: %d, предупреждений: %d", totalMessages, errorCount, warningCount);
    }

    static void main() {
        Logger.logInfo("Запуск приложения");
        Logger.logInfo("Загрузка конфигурационных файлов");
        Logger.logWarning("Файл 'custom_config.xml' не найден");

        Logger.logInfo("Начало обработки пакета данных");
        Logger.logError("Не удалось подключиться к внешней БД");
        Logger.logInfo("Активация режима офлайн-кэширования");
        Logger.logWarning("Размер кэша превышает 80% от лимита");
        Logger.logError("Ошибка сериализации объекта");

        Logger.logInfo("Сохранение промежуточных результатов");
        Logger.logInfo("Закрытие соединений и освобождение ресурсов");

        System.out.println("\nИтоговая статистика");
        System.out.println(Logger.getStats());
    }
}
