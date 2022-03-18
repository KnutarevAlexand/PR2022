package ru.ubrr.knutarev.CloneDemo;

//Фигура
public abstract class Shape {

    public final double PI = 3.14;//число pi  ЗАГЛАНАЯ
    protected int id = Random.getRandom(1,1000);// ИД фигуры
    protected double s;//площадь
    protected String color = Colors.getColor().toString();//цвет

    protected abstract double getArea();
}
