package ru.ubrr.knutarev.CloneDemo;

//Генератор случайного числа в заданном диапазоне
public class Random {

    public static int getRandom(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
