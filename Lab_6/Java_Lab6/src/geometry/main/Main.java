package geometry.main;

import geometry.models.Shape;
import geometry.services.AreaCalculator;
import geometry.services.ShapeService;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main() {
        try {
            List<Shape> shapes = new ArrayList<>();
            Shape circle = ShapeService.createCircle(5.0);
            shapes.add(circle);
            System.out.println("Создан: " + circle);

            Shape rectangle = ShapeService.createRectangle(4.0, 6.0);
            shapes.add(rectangle);
            System.out.println("Создан: " + rectangle);

            Shape triangle = ShapeService.createTriangle(3.0, 4.0, 5.0);
            shapes.add(triangle);
            System.out.println("Создан: " + triangle);

            System.out.println("\nВалидация");
            try {
                ShapeService.createCircle(-3.0);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                ShapeService.createTriangle(1.0, 2.0, 10.0);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("\nИтоговая статистика");
            double totalArea = AreaCalculator.sumAreas(shapes);
            System.out.printf("Всего фигур: %d\n", shapes.size());
            System.out.printf("Сумма площадей: %.4f\n", totalArea);

            System.out.println("\nДетализация по фигурам");
            for (int i = 0; i < shapes.size(); i++)
                System.out.printf("%d. %s\n", i + 1, shapes.get(i).toString());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
