package ru.ubrr.knutarev.flyToMoon;

public interface StageRocket {

    public void setIdStage(int idStage);
    public int getIdStage();
    public void setWeight(double weight);
    public double getWeight();
    public double getWeightFuel();
    public double getGasflowRate();
    public double getFuelConsumption();
    public void setRocket(Rocket rocket);



    public void checkStage() throws CheckException;
    public void startStage();
    public void finishStage();
}
