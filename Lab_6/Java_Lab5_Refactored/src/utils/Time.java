package utils;

import java.time.LocalTime;

public class Time {
    // Поля
    private int hours;
    private int minutes;
    private int seconds;

    static private final int min_time = 0;
    static private final int max_hours = 23;
    static private final int max_minutes_and_seconds = 59;
    static private final int step = 60;
    static private final int step_for_hour = 3600;
    static private final int hours_in_day = 24;
    static private final int middle = 12;

    // Конструкторы
    public Time(int hours, int minutes, int seconds) throws Exception {
        if (hours < min_time || hours > max_hours)
            throw new Exception("Некорректный час");
        if (minutes < min_time || minutes > max_minutes_and_seconds)
            throw new Exception("Некорректные минуты");
        if (seconds < min_time || seconds > max_minutes_and_seconds)
            throw new Exception("Некорректные секунды");
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }
    public Time(){
        hours = LocalTime.now().getHour();
        minutes = LocalTime.now().getMinute();
        seconds = LocalTime.now().getSecond();
    }

    // Геттеры
    public int getHours() { return hours; }
    public int getMinutes() { return minutes; }
    public int getSeconds() { return seconds; }

    // Добавление секунд
    public void addSeconds(int seconds) throws Exception {
        ValidationUtils.check_number_less(seconds, "Время не может быть отрицательным");
        var total = this.seconds + seconds;
        this.seconds = total % step;
        var extra = total / step;
        addMinutes(extra);
    }
    // Добавление минут
    public void addMinutes(int minutes) throws Exception {
        ValidationUtils.check_number_less(minutes, "Время не может быть отрицательным");
        var total = this.minutes + minutes;
        this.minutes = total % step;
        var extra = total / step;
        addHours(extra);
    }
    // Добавление часов
    public void addHours(int hours) throws Exception {
        ValidationUtils.check_number_less(hours, "Время не может быть отрицательным");
        this.hours = (this.hours + hours) % hours_in_day;
    }
    // Разница во времени в секундах
    public int differenceInSeconds(Time other) {
        int fst = getHours() * step_for_hour + getMinutes() * step + getSeconds();
        int snd = other.getHours() * step_for_hour + other.getMinutes() * step + other.getSeconds();
        return  Math.abs(snd - fst);
    }
    // Вывод в 24-часовом формате
    public void print24h() {
        System.out.printf("%02d:%02d:%02d", getHours(), getMinutes(), getSeconds());
    }
    // Вывод в 12-часовом формате
    public void print12h() {
        String AmPm = getHours() < middle ? "AM" : "PM";
        int hours_12 = getHours() % middle;
        if (hours_12 == min_time)
            hours_12 = middle;
        System.out.printf("%02d:%02d:%02d %s", hours_12, getMinutes(), getSeconds(), AmPm);
    }
}

// Добавлены константы для проверки времени и выполнении шагов