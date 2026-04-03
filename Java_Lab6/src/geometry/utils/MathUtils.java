package geometry.utils;

public class MathUtils {
    public static boolean isValidTriangle(double a, double b, double c) throws Exception {
        if (a <= 0 || b <= 0 || c <= 0)
            throw new Exception("Стороны должны быть положительные");
        return ((a + b) > c) && ((a + c) > b) && ((b + c) > a);
    }
}
