package ru.ubrr.knutarev.Tread;
import java.util.concurrent.*;

public class ConcurrentMapDemo {
    public static void main(String[] args) {
        ConcurrentMap<Integer,String> cm = new ConcurrentHashMap<Integer,String>();
        System.out.println("Insert elements...");
        cm.put(111, "Aarthi");
        cm.put(222, "Banu");
        cm.put(333, "Chetan");
        cm.putIfAbsent(444, "Devi");
        System.out.println(cm);
        System.out.println("Removing elements...");
        cm.remove(222);
        cm.remove(333, "Chetan");
        System.out.println(cm);
    }
}
