package ru.ubrr.knutarev.CloneDemo;

//Класс Параллелограмм
public class Parallelogramm extends Shape {


    protected double a;//сторона
    protected double h;//высота

    public Parallelogramm(double a, double h) {
        this.a = a;
        this.h = h;
    }
    public double getArea() {
        return a*h;
    }
}
