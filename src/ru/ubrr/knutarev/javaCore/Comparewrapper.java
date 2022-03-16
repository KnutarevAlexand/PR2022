package ru.ubrr.knutarev.javaCore;

public class Comparewrapper {
    public Comparewrapper() {
        super();
    }

    public static void main(String[] args) {
        Integer iOb1 = 127;
        Integer iOb2 = 127;
        System.out.println(iOb1 == iOb2);
        System.out.println(iOb1 != iOb2);

        Integer iOb5 = 128;
        Integer iOb6 = 128;
        System.out.println(iOb5 == iOb6);//класс обертки примитивных типов сравнились по ссылке, превышен размер кеша хранения поэтому ячейка переприсвоилась
        System.out.println(iOb5 != iOb6);

        int iOb7 = 128;
        int iOb8 = 128;
        System.out.println(iOb7 == iOb8);//примитивные типы сравнились по значению, ограничения нет на кеш хранения
        System.out.println(iOb7 != iOb8);

    }

}
