package ru.ubrr.knutarev.flyToMoon;

public class ConsolParametr implements Runnable {

    private final long timeStep = 2;
    private Rocket rocket;


    public ConsolParametr(Rocket rocket) {
        this.rocket = rocket;

    }


    @Override
    public void run() {

        int count = 0;
        while(rocket.status == 1) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            System.out.println("ТЕЛЕМЕТРИЯ: Кол-во ступеней: " + this.rocket.stagesList.size() + " Ускорение: " + rocket.getAcceler() + " Дистанция полета: " + rocket.getFlightDistance() + " Скорость ракеты: " + rocket.getSpeed() + " Вес ракеты: " + rocket.getWeightRocket() + " Время полета: " + rocket.getTimeRocket() / 60 + " мин");
        }
    }
}

