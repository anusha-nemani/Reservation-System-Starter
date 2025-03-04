package flight.reservation;

import flight.reservation.flight.FlightObserver;

public class Passenger implements User , FlightObserver {

    private final String name;

    public Passenger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void notify(String message) {

        System.out.println(name + " received notification: " + message);
    }
}
