package ru.ubrr.knutarev.CloneDemo;

//Класс Равнобедренный треугольник
public class IssoscelesTriangle extends Triangle {

    static private double a;//сторона a
    static private double b;//сторона b
    static private double c;//сторона c
    private double u;//основание
    private double h;//высота

    public IssoscelesTriangle (double u, double h) {
        super(a, b, c);
        this.u = u;
        this.h = h;
        s = (double)1/2*u*h;
    }
}
