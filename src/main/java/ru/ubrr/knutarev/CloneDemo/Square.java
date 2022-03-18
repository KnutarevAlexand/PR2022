package ru.ubrr.knutarev.CloneDemo;

//Класс Квадрат
public class Square extends Rectangle implements CloneableShape<Square> {



    public Square(double a) {
        super(a, a);
    }


    @Override
    public Square clone(double scale) {
        return new Square(this.a * scale);
    }
}
