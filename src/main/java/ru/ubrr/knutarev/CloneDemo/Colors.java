package ru.ubrr.knutarev.CloneDemo;

//Цвета
public enum Colors {
    YELLOW,
    RED,
    GREEN,
    BLUE,
    GRAY,
    CYAN,
    BLACK,
    MAGENTA;

    private static Colors[] list = Colors.values();

    public static Colors getColor() {
        int colorCode = Random.getRandom(0,7);//rnd(0,7);
        return list[colorCode];
    }
}