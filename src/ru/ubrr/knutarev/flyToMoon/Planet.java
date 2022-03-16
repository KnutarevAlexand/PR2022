package ru.ubrr.knutarev.flyToMoon;

public enum Planet {
    EARTH   (5.976e24, 6.37814e6),
    MOON    (7.36e22, 1.7374e6);
    private final double massPlanet;   // в килограммах
    private final double radiusPlanet; // в метрах
    Planet(double massPlanet, double radiusPlanet) {
        this.massPlanet = massPlanet;
        this.radiusPlanet = radiusPlanet;
    }
    public double getMassPlanet() {
        return massPlanet;
    }
    public double getRadiusPlanet() {
        return radiusPlanet;
    }
    // гравитационная постоянная
    public static final double G = 6.67300E-11;
    //сила гравитации
    double surfaceWeight(double otherMass, double flightDistance) {
        return G * massPlanet * otherMass / (radiusPlanet + flightDistance) / (radiusPlanet + flightDistance);
    }
/*
        public static void main(String[] args) {
            Planet color = Planet.EARTH;
            System.out.println(color.getMassPlanet());
            System.out.println(color.getRadiusPlanet());
            System.out.println(Planet.MOON.surfaceWeight(75.0));
            double mass = 75.0;
            double flightDistance = 0.0;
            for (Planet p : Planet.values())
                System.out.printf("Your weight on %s is %f%n",p,p.surfaceWeight(mass, flightDistance));
        }
*/
}
