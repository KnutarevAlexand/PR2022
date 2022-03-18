package ru.ubrr.knutarev.CloneDemo;

//Класс Эллипс
public class Ellipse extends Shape {

    private double r1;//большая полуось
    private double r2;//малая полуось

    public Ellipse(double r1, double r2) {
        this.r1 = r1;
        this.r2 = r2;
    }

    public double getArea() {
        return PI*r1*r2;
    }
}

