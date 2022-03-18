package ru.ubrr.knutarev.Queue.SemaphoreDemo;

// Простой пример применения семафора в Java

import java.util.concurrent.*;

class SemDemo {

    public static void main(String args[]) {
        Semaphore sem = new Semaphore(1);

        new IncThread(sem, "A");
        new DecThread(sem, "B");

    }

}
