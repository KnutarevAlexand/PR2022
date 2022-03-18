package ru.ubrr.knutarev.Queue;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private final BlockingQueue queue;

    @Override
    public void run() {

        try {
            while (true) {
                Integer take = (Integer) queue.take();
                process(take);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void process(Integer take) throws InterruptedException {
        System.out.println("[Consumer] Take : " + take);
        Thread.sleep(500);
    }

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }
}
