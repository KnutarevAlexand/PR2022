package ru.ubrr.knutarev.Tread;

public class DaemonThreadDemo extends Thread {
    String name;
    public void run() {

        name = Thread.currentThread().getName();
        if(Thread.currentThread().isDaemon())
            System.out.println(name + " is a daemon thread");
        else
            System.out.println(name + " is a user thread");
    }
    public static void main(String[] args) {
        DaemonThreadDemo d1 = new DaemonThreadDemo();
        DaemonThreadDemo d2 = new DaemonThreadDemo();
        DaemonThreadDemo d3 = new DaemonThreadDemo();

        d1.setDaemon(true);

        d1.start();
        d2.start();

        d3.setDaemon(false);
        d3.start();
    }
}
