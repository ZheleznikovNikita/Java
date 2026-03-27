import java.time.LocalTime;

public class Time {
    // Поля
    private int hours;
    private int minutes;
    private int seconds;

    // Конструкторы
    public Time(int hours, int minutes, int seconds) throws Exception {
        if (hours < 0 || hours > 23)
            throw new Exception("Некорректный час");
        if (minutes < 0 || minutes > 59)
            throw new Exception("Некорректные минуты");
        if (seconds < 0 || seconds > 59)
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
        if (seconds < 0)
            throw new Exception("Время не может быть отрицательным");
        var total = this.seconds + seconds;
        this.seconds = total % 60;
        var extra = total / 60;
        addMinutes(extra);
    }
    // Добавление минут
    public void addMinutes(int minutes) throws Exception {
        if (minutes < 0)
            throw new Exception("Время не может быть отрицательным");
        var total = this.minutes + minutes;
        this.minutes = total % 60;
        var extra = total / 60;
        addHours(extra);
    }
    // Добавление часов
    public void addHours(int hours) throws Exception {
        if (hours < 0)
            throw new Exception("Время не может быть отрицательным");
        this.hours = (this.hours + hours) % 24;
    }
    // Разница во времени в секундах
    public int differenceInSeconds(Time other) {
        int fst = getHours() * 3600 + getMinutes() * 60 + getSeconds();
        int snd = other.getHours() * 3600 + other.getMinutes() * 60 + other.getSeconds();
        return  Math.abs(snd - fst);
    }
    // Вывод в 24-часовом формате
    public void print24h() {
        System.out.printf("%02d:%02d:%02d", getHours(), getMinutes(), getSeconds());
    }
    // Вывод в 12-часовом формате
    public void print12h() {
        String AmPm = getHours() < 12 ? "AM" : "PM";
        int hours_12 = getHours() % 12;
        if (hours_12 == 0) hours_12 = 12;
        System.out.printf("%02d:%02d:%02d %s", hours_12, getMinutes(), getSeconds(), AmPm);
    }
}
