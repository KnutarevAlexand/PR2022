package ru.ubrr.knutarev.Queue.SemaphoreDemo;

// Поток исполнения, уменьшающий значение счетчика на единицу

import java.util.concurrent.Semaphore;

class DecThread implements Runnable {

    String name;
    Semaphore sem;

    DecThread(Semaphore s, String n) {
        sem = s;
        name = n;
        new Thread(this).start();
    }

    public void run() {
        System.out.println("Запуск потока " + name);
        try {
            // сначала получить разрешение
            System.out.println("Поток " + name + " ожидает разрешения");
            sem.acquire();
            System.out.println("Поток " + name + " получает разрешение");

            // а теперь получить доступ к общему ресурсу

            for(int i = 0; i < 5; i++) {
                Shared.count--;
                System.out.println(name + ": " + Shared.count);

                // Разрешить, если возможно, переключение контекста
                Thread.sleep(10);
            }
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }

        // освободить разрешение
        System.out.println("Поток " + name + " освобождает разрешение");
        sem.release();
    }
}