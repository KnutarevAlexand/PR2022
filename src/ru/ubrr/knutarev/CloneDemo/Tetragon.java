package ru.ubrr.knutarev.CloneDemo;

//Класс Четырехугольник
public class Tetragon extends Shape {

    private double d1;//диагональ 1
    private double d2;//диагональ 2
    private double angle;//угол между диагоналями

    public Tetragon(double d1, double d2, double angle) {
        this.d1 = d1;
        this.d2 = d2;
        this.angle = angle;
    }

    public double getArea() {
        return (double)1/2*d1*d2*Math.sin(Math.toRadians(angle));
    }
}

