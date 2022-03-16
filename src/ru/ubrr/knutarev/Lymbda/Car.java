package ru.ubrr.knutarev.Lymbda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Car implements Comparable<Car> {

    public int manufactureYear;
    private String model;
    private int maxSpeed;

    public int getManufactureYear() {
        return manufactureYear;
    }
    public int getMaxSpeed() {
        return maxSpeed;
    }

    public Car(int manufactureYear, String model, int maxSpeed) {
        this.manufactureYear = manufactureYear;
        this.model = model;
        this.maxSpeed = maxSpeed;
    }



    @Override
    public int compareTo(Car o) {
        return this.getManufactureYear() - o.getManufactureYear();
    }


    public static void main(String[] args) {

        List<Car> cars = new ArrayList<>();

        Car ferrari = new Car(1990, "Ferrari 360 Spider", 310);
        Car lambo = new Car(2012, "Lamborghini Gallardo", 290);
        Car bugatti = new Car(2010, "Bugatti Veyron", 350);

        cars.add(ferrari);
        cars.add(bugatti);
        cars.add(lambo);

        System.out.println("Comparable");
        Collections.sort(cars);
        for(Car car : cars) {
            System.out.println(car.getManufactureYear());
        }

        System.out.println("Comparator");
        Comparator speedComparator = new MaxSpeedCarComparator();
        Collections.sort(cars, speedComparator);
        for(Car car : cars) {
            System.out.println(car.getMaxSpeed());
        }

        System.out.println("Lymbda");
        cars.sort((o3, o4) -> o3.getManufactureYear() - o4.getManufactureYear());
        for(Car car : cars) {
            System.out.println(car.getManufactureYear());

        }


        cars.stream().filter(x-> x.getManufactureYear() >= 2010).forEach(System.out::println);//Using a method reference
        cars.stream().filter(x-> x.getManufactureYear() >= 2010).forEach(x -> System.out.println(x.getManufactureYear()));//Using lymbda
    }



}
