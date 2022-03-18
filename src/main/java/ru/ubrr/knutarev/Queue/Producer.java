package ru.ubrr.knutarev.Queue;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private final BlockingQueue queue;
    @Override
    public void run() {
        try {
            process();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void process() throws InterruptedException {
// положить 20 дюймов в очередь
        for (int i = 0; i < 20; i++) {
            System.out.println("[Producer] Put : " + i);
            queue.put(i);
            System.out.println("[Producer] Queue remainingCapacity : " + queue.remainingCapacity());
            Thread.sleep(100);
        }
    }
    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }
}
