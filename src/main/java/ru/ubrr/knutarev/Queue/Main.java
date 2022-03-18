package ru.ubrr.knutarev.Queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {

        BlockingQueue queue = new LinkedBlockingQueue<>(10);

        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();

    }

}