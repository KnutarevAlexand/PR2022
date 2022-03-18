package ru.ubrr.knutarev.flyToMoon;

public class StageRowerMoon implements StageRocket{
    private int idStage;// номер ступени
    private double weight;// вес
    private double weightFuel;// вес топлива
    private double gasflowRate;// скорость истечения газов
    private double fuelConsumption;// расход топлива
    private Rocket rocket;// ссылка на ракету

    public void setIdStage(int idStage) {
        this.idStage = idStage;
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }

    public Rocket getRocket() {
        return rocket;
    }

    public int getIdStage() {
        return idStage;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public double getWeightFuel() {
        return weightFuel;
    }

    public double getGasflowRate() {
        return gasflowRate;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }


    public static class Builder {
        private StageRowerMoon newStageRowerMoon;

        public Builder() {
            newStageRowerMoon = new StageRowerMoon();
        }
        public Builder idStage(int idStage) {
            newStageRowerMoon.idStage = idStage;
            return this;
        }
        public Builder weight(double weight) {
            newStageRowerMoon.weight = weight;
            return this;
        }
        public Builder weightFuel(double weightFuel) {
            newStageRowerMoon.weightFuel = weightFuel;
            return this;
        }
        public Builder gasflowRate(double gasflowRate) {
            newStageRowerMoon.gasflowRate = gasflowRate;
            return this;
        }
        public Builder fuelConsumption(double fuelConsumption) {
            newStageRowerMoon.fuelConsumption = fuelConsumption;
            return this;
        }
        public StageRowerMoon build() {
            return newStageRowerMoon;
        }
    }

    public void checkStage() {
    }

    public void startStage() {

        //удаление предыдущей ступени
        rocket.stagesList.remove(rocket.nowStage-1);
        System.out.println("-----------Ступень удалена-------------");
        System.out.println("--------------Свободный полет----------------");

        //пересчет свободного полета до расстояния запуска тормозной ступени или до недолета ракеты (отрицательная скорость)
        while (rocket.getFlightDistance() < rocket.earthMoontDistance & rocket.getSpeed() > 0.0) {

            double resultForce = //gasflowRate * fuelConsumption
                    - Planet.EARTH.surfaceWeight(rocket.getWeightRocket(), rocket.getFlightDistance())
                            + Planet.MOON.surfaceWeight(rocket.getWeightRocket(), rocket.earthMoontDistance - rocket.getFlightDistance());

            rocket.setAcceler(resultForce / rocket.getWeightRocket());

            //this.setWeightFuel(weightFuel - fuelConsumption * rocket.getTimeStep());

            rocket.setFlightDistance(rocket.getFlightDistance() + rocket.getSpeed() * rocket.getTimeStep()  + rocket.getAcceler() * rocket.getTimeStep() * rocket.getTimeStep() / 2 );

            rocket.setSpeed(rocket.getSpeed() + rocket.getAcceler() * rocket.getTimeStep());
            //rocket.setSpeed( (resultForce / fuelConsumption) * Math.log( (rocket.getWeightRocket() / (rocket.getWeightRocket() - fuelConsumption * rocket.getTimeStep()) ) ) );

            rocket.setTimeRocket(rocket.getTimeRocket() + rocket.getTimeStep());

            //counter--;
        }

        if(rocket.getSpeed() <= 0.0) {
            System.out.println("------------НЕДОЛЕТ------------");
        }

        if(rocket.getFlightDistance() >= rocket.earthMoontDistance) {
            System.out.println("---------РАКЕТА ДОЛЕТЕЛА ДО ЛУНЫ------------------");
        }

        rocket.status = 0;//Выключаем ракету
        System.out.println("Ускорение: " + rocket.getAcceler());
        System.out.println("Дистанция полета: " + rocket.getFlightDistance());
        System.out.println("Скорость ракеты: " + rocket.getSpeed());
        System.out.println("Вес ракеты: " + rocket.getWeightRocket());
        System.out.println("Время полета: " + rocket.getTimeRocket() / 60 + " мин");

    }

    public void finishStage() {

        //запуск следующей супени
        rocket.stagesList.get(rocket.nowStage).startStage();

    }

}
