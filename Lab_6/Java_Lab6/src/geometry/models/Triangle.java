package geometry.models;

import geometry.utils.MathUtils;

public class Triangle implements Shape {
    private double sideA;
    private double sideB;
    private double sideC;

    public Triangle(double sideA, double sideB, double sideC) throws Exception {
        if (!MathUtils.isValidTriangle(sideA, sideB, sideC))
            throw new Exception("Такого треугольника не существуют");
        setSideA(sideA);
        setSideB(sideB);
        setSideC(sideC);
    }

    // Геттеры
    public double getSideA() { return sideA; }
    public double getSideB() { return sideB; }
    public double getSideC() { return sideC; }
    // Сеттеры
    public void setSideA(double sideA) throws Exception {
        check_side(sideA);
        this.sideA = sideA;
    }
    public void setSideB(double sideB) throws Exception {
        check_side(sideB);
        this.sideB = sideB;
    }
    public void setSideC(double sideC) throws Exception {
        check_side(sideC);
        this.sideC = sideC;
    }

    @Override
    public double getArea() {
        double p = getPerimeter() / 2.0;
        return Math.sqrt(p * (p - sideA) * (p - sideB) * (p - sideC));
    }
    @Override
    public double getPerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public String toString() {
        return "Треугольник размера " + sideA + " x " + sideB  + " x " + sideC;
    }

    private void check_side(double side) throws Exception {
        if (side <= 0)
            throw new Exception("Сторона должна быть положительной");
    }
}
