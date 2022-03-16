package ru.ubrr.knutarev.flyToMoon;

public class StageBooster implements StageRocket{
    private int idStage;// номер ступени
    private double weight;// вес
    private double weightFuel;// вес топлива
    private double gasflowRate;// скорость истечения газов
    private double fuelConsumption;// расход топлива
    private Rocket rocket;// ссылка на ракету

    public void setIdStage(int idStage) {
        this.idStage = idStage;
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

    public void setWeightFuel(double weightFuel) {
        this.weightFuel = weightFuel;
    }

    public double getWeightFuel() {
        return weightFuel;
    }

    public void setGasflowRate(double gasflowRate) {
        this.gasflowRate = gasflowRate;
    }

    public double getGasflowRate() {
        return gasflowRate;
    }

    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }

    public Rocket getRocket() {
        return rocket;
    }


    public static class Builder {
        private StageBooster newStageBooster;

        public Builder() {
            newStageBooster = new StageBooster();
        }
        public Builder idStage(int idStage) {
            newStageBooster.idStage = idStage;
            return this;
        }
        public Builder weight(double weight) {
            newStageBooster.weight = weight;
            return this;
        }
        public Builder weightFuel(double weightFuel) {
            newStageBooster.weightFuel = weightFuel;
            return this;
        }
        public Builder gasflowRate(double gasflowRate) {
            newStageBooster.gasflowRate = gasflowRate;
            return this;
        }
        public Builder fuelConsumption(double fuelConsumption) {
            newStageBooster.fuelConsumption = fuelConsumption;
            return this;
        }
        public StageBooster build() {
            return newStageBooster;
        }
    }




    public void checkStage()  throws CheckException {
        if(this.getWeightFuel() <= 0) throw new CheckException("НЕТ ТОПЛИВА. ЗАПРАВЬТЕ СТУПЕНЬ");
    }

    public void startStage() {

        //удаление предыдущей ступени
        if(rocket.nowStage != 0) {
            rocket.stagesList.remove(rocket.nowStage-1);
            System.out.println("-----------Ступень удалена-------------");
        } else rocket.nowStage ++;

        System.out.println("Ускорение: " + rocket.getAcceler());
        System.out.println("Дистанция полета: " + rocket.getFlightDistance());
        System.out.println("Скорость ракеты: " + rocket.getSpeed());
        System.out.println("Вес ракеты: " + rocket.getWeightRocket());
        System.out.println("Время полета: " + rocket.getTimeRocket() / 60 + " мин");
        System.out.println("Запущена разгонная ступень: " + this.getIdStage());
        double runTimeStageBooster = weightFuel / fuelConsumption;
        System.out.println("Расчетное время горения разгонной ступени: " + runTimeStageBooster);


        //пересчет ступени
        int counter = (int) (runTimeStageBooster / rocket.getTimeStep());
        while (counter > 0) {

            //замеделение пересчета
            try {
                Thread.sleep( 1 );
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            double resultForce = gasflowRate * fuelConsumption
                    - Planet.EARTH.surfaceWeight(rocket.getWeightRocket(), rocket.getFlightDistance())
                    + Planet.MOON.surfaceWeight(rocket.getWeightRocket(), rocket.earthMoontDistance - rocket.getFlightDistance());

            rocket.setAcceler(resultForce / rocket.getWeightRocket());

            this.setWeightFuel(weightFuel - fuelConsumption * rocket.getTimeStep());

            rocket.setFlightDistance(rocket.getFlightDistance() + rocket.getSpeed() * rocket.getTimeStep()  + rocket.getAcceler() * rocket.getTimeStep() * rocket.getTimeStep() / 2 );

            rocket.setSpeed(rocket.getSpeed() + rocket.getAcceler() * rocket.getTimeStep());
            //rocket.setSpeed( (resultForce / fuelConsumption) * Math.log( (rocket.getWeightRocket() / (rocket.getWeightRocket() - fuelConsumption * rocket.getTimeStep()) ) ) );

            rocket.setTimeRocket(rocket.getTimeRocket() + rocket.getTimeStep());

            counter--;

        }

        this.finishStage();

    }

    public void finishStage() {

        //запуск следующей супени
        rocket.stagesList.get(rocket.nowStage).startStage();

    }

}
