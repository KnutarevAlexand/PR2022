package ru.ubrr.knutarev.CloneDemo;

//Класс Треугольник
public class Triangle extends Shape {

    private double a;//сторона a
    private double b;//сторона b
    private double c;//сторона c

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        s = (double) Math.sqrt(((a+b+c)/2)*(((a+b+c)/2)-a)*(((a+b+c)/2)-b)*(((a+b+c)/2)-c));
    }

    public double getArea() {
        return s;
    }
}

