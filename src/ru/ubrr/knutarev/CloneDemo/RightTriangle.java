package ru.ubrr.knutarev.CloneDemo;

//Класс Прямоугольный треугольник
public class RightTriangle extends Triangle {

    private double a;//сторона
    private double b;//сторона

    public RightTriangle (double a, double b, double c) {
        super(a, b, c);
        this.a = a;
        this.b = b;
        s = (double)1/2*a*b;
    }
}
