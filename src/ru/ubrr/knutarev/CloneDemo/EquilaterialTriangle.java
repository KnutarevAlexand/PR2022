package ru.ubrr.knutarev.CloneDemo;

//Класс Равносторонний треугольник
public class EquilaterialTriangle extends IssoscelesTriangle {


    static private double u;//основание
    static private double h;//высота
    static private double a;//сторона

    public EquilaterialTriangle (double a, double h) {
        super(u, h);
        EquilaterialTriangle.a = a;
        EquilaterialTriangle.h = h;
        s = (double)1/2*a*h;
    }
}
