package ru.ubrr.knutarev.CloneDemo;

//Круг
public class Circle extends Ellipse implements CloneableShape<Circle> {

    private double r;//радиус

    public Circle (double r) {
        super(r, r);
        this.r = r;
    }


    @Override
    public Circle clone(double scale) {
        return new Circle(this.r * scale);
    }
}