package ru.ubrr.knutarev.flyToMoon;

public class SpacePort {

    private Rocket rocket;

    public void mount(Rocket rocket) {
        this.rocket = rocket;
    }

    public void launch() {
        System.out.println("-----------ЗАПУСК РАКЕТЫ-----------");
        System.out.println("Кол-во ступеней: " + this.rocket.stagesList.size() + "Ускорение: " + rocket.getAcceler() + "Дистанция полета: " + rocket.getFlightDistance() + "Скорость ракеты: " + rocket.getSpeed() + "Вес ракеты: " + rocket.getWeightRocket() + "Время полета: " + rocket.getTimeRocket() / 60 + " мин");

    }

}
