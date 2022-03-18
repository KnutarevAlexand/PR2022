package ru.ubrr.knutarev.Tread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class ReadWriteLockDemo {

    private final ReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock wl = rwl.writeLock();
    private final Lock rl = rwl.readLock();

    private final List<String> list = new ArrayList<String>();
    public static void main(String[] args) {
        ReadWriteLockDemo d = new ReadWriteLockDemo();
        d.addElements("Java");
        d.addElements("Python");
        d.addElements("Perl");

        System.out.println("Printing element in main thread: " + d.getElements(1));
    }

    public void addElements(String s) {
        wl.lock();

        try {
            list.add(s);
            System.out.println("Element " + s + " is added to the list");
        }
        finally {
            wl.unlock();
        }
    }

    public String getElements(int i) {
        rl.lock();

        try {
            System.out.println("Retrieve element at index " + i + ": " + list.get(i));
            return list.get(i);
        }
        finally {
            rl.unlock();
        }
    }
}
