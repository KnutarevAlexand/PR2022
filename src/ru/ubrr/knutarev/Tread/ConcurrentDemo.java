package ru.ubrr.knutarev.Tread;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentDemo extends Thread {

    static CopyOnWriteArrayList<String> l  = new CopyOnWriteArrayList<String>();
    public void run()
    {
        l.add("D");
    }
    public static void main(String[] args)     throws InterruptedException
    {
        l.add("A");
        l.add("B");
        l.add("c");
        ConcurrentDemo t = new ConcurrentDemo();
        t.run();
        Thread.sleep(1000);
        Iterator itr = l.iterator();
        while (itr.hasNext()) {
            String s = (String)itr.next();
            System.out.println(s);
            Thread.sleep(1000);
        }
        System.out.println(l);
    }
}
