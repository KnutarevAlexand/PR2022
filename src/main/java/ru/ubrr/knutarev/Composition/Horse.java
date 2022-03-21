package ru.ubrr.knutarev.Composition;

public class Horse {
    private Halter halter;

    public Horse() {
        this.halter = new Halter();
    }


    public static void main(String[] args) {
        Horse horse = new Horse();
    }

}