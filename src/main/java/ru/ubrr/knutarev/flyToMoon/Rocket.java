package ru.ubrr.knutarev.flyToMoon;

import java.util.ArrayList;
import java.util.List;


public class Rocket  implements Runnable {
    private double weight;// вес
    private double timeRocket = 0.0;// общее время полета ракеты
    private double speed = 0.0;// скорость ракеты
    private double flightDistance = 0.0;// дистанция полета ракеты
    private double acceler = 0.0;// ускорение ракеты
    public final double earthMoontDistance = 384467000.0 - Planet.EARTH.getRadiusPlanet() - Planet.MOON.getRadiusPlanet();// расстояние меду поверхностями Земли и Луны
    private final double timeStep = 0.1;//период пересчета метрик полета (сек)
    public int nowStage;
    public int status = 1;
    List<StageRocket> stagesList = new ArrayList<>();


    public void setTimeRocket(double timeRocket) {
        this.timeRocket = timeRocket;
    }

    public void setAcceler(double acceler) {
        this.acceler = acceler;
    }

    public double getAcceler() {
        return acceler;
    }

    public void setFlightDistance(double flightDistance) {
        this.flightDistance = flightDistance;
    }

    public double getFlightDistance() {
        return flightDistance;
    }

    public double getTimeStep() {
        return timeStep;
    }

    public double getTimeRocket() {
        return timeRocket;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public double getWeightRocket() {
        return weight
                + stagesList.stream().mapToDouble(d->d.getWeight()).sum()
                + stagesList.stream().mapToDouble(d->d.getWeightFuel()).sum();
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }


    public void checkElement() {
        System.out.println("------Проверка ракеты-----");
        for(StageRocket stage: stagesList) {
            try {
                stage.checkStage();
            } catch (CheckException my) {
                System.out.println(my.whatHappenes());
                System.exit(0);
            } finally {
            }
            stage.setRocket(this);
        }
    }

    public static class Builder {
        private Rocket newRocket;

        public Builder() {
            newRocket = new Rocket();
        }
        public Builder weight(double weight) {
            newRocket.weight = weight;
            return this;
        }
        public Builder speed(double speed) {
            newRocket.speed = speed;
            return this;
        }
        public Builder stageRocket(StageRocket stageRocket) {
            newRocket.stagesList.add(stageRocket);
            return this;
        }
        public Rocket build() {
            return newRocket;
        }
    }



    @Override
    public void run() {

        // запуск потока исполнения отправки метрик ракеты в консоль
        ConsolParametr consolParametr = new ConsolParametr(this);
        Thread consolParametrTread = new Thread(consolParametr);
        consolParametrTread.start();

        //поджиг минимальной ступени из всех ускорителей
        nowStage = 0;
        stagesList.get(nowStage).startStage();

    }

}
