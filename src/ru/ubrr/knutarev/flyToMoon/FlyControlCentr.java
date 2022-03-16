package ru.ubrr.knutarev.flyToMoon;

public class FlyControlCentr implements Runnable{

    public static void main(String[] args) {

        FlyControlCentr flyControlCentr = new FlyControlCentr();
        Thread flyControlCentrTread = new Thread(flyControlCentr);
        flyControlCentrTread.start();
    }

    @Override
    public void run() {

        SpacePort spacePort = new SpacePort();

        Rocket rocket = new Rocket.Builder()
                .weight(120.00)
                .stageRocket(new StageBooster.Builder()
                        .idStage(1)
                        .weight(30000.00)
                        .weightFuel(430000.00)
                        .gasflowRate(2800.00)
                        .fuelConsumption(3600.00)
                        .build())
                .stageRocket(new StageBooster.Builder()
                        .idStage(2)
                        .weight(11000.00)
                        .weightFuel(167000.00)
                        .gasflowRate(3000.00)
                        .fuelConsumption(800.00)
                        .build())
                .stageRocket(new StageBooster.Builder()
                        .idStage(3)
                        .weight(3000.00)
                        .weightFuel(65022.00)
                        .gasflowRate(3250.00)
                        .fuelConsumption(180.00)
                        .build())
                .stageRocket(new StageBrake.Builder()
                        .idStage(4)
                        .weight(300.00)
                        .weightFuel(300.00)
                        .gasflowRate(-5640.00)
                        .fuelConsumption(30.00)
                        .build())
                .stageRocket(new StageRowerMoon.Builder()
                        .idStage(5)
                        .weight(180.00)
                        .build())
                .build();

        Thread rocketTread = new Thread(rocket);
        spacePort.mount(rocket);
        rocket.checkElement();
        spacePort.launch();
        rocketTread.start();

    }
}



