package geometry.services;

import geometry.models.Shape;
import java.util.List;

public class AreaCalculator {
    public static double sumAreas(List<Shape> shapes) throws Exception {
        if (shapes == null || shapes.isEmpty())
            throw new Exception("Передан пустой список");
        double sum = 0.0;
        for (var shape : shapes)
            sum += shape.getArea();
        return sum;
    }
}
