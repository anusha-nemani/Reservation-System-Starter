package flight.reservation.flight;

import flight.reservation.Airport;
import flight.reservation.plane.Helicopter;
import flight.reservation.plane.PassengerDrone;
import flight.reservation.plane.PassengerPlane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Flight implements FlightSubject{

    private int number;
    private Airport departure;
    private Airport arrival;
    protected Object aircraft;

    private List<FlightObserver> observers = new ArrayList<>();

    public Flight(int number, Airport departure, Airport arrival, Object aircraft) throws IllegalArgumentException {
        this.number = number;
        this.departure = departure;
        this.arrival = arrival;
        this.aircraft = aircraft;
        checkValidity();
    }

    private void checkValidity() throws IllegalArgumentException {
        if (!isAircraftValid(departure) || !isAircraftValid(arrival)) {
            throw new IllegalArgumentException("Selected aircraft is not valid for the selected route.");
        }
    }

    private boolean isAircraftValid(Airport airport) {
        return Arrays.stream(airport.getAllowedAircrafts()).anyMatch(x -> {
            String model;
            if (this.aircraft instanceof PassengerPlane) {
                model = ((PassengerPlane) this.aircraft).model;
            } else if (this.aircraft instanceof Helicopter) {
                model = ((Helicopter) this.aircraft).getModel();
            } else if (this.aircraft instanceof PassengerDrone) {
                model = "HypaHype";
            } else {
                throw new IllegalArgumentException(String.format("Aircraft is not recognized"));
            }
            return x.equals(model);
        });
    }

    public Object getAircraft() {
        return aircraft;
    }

    public int getNumber() {
        return number;
    }

    public Airport getDeparture() {
        return departure;
    }

    public Airport getArrival() {
        return arrival;
    }

    @Override
    public String toString() {
        return aircraft.toString() + "-" + number + "-" + departure.getCode() + "/" + arrival.getCode();
    }

    @Override
    public void registerFlightObserver(FlightObserver observer) {
        observers.add(observer);

    }

    @Override
    public void removeFlightObserver(FlightObserver observer) {
        observers.remove(observer);

    }

    @Override
    public void notifyFlightObserver(String message) {
        for (FlightObserver observer : observers) {
            observer.notify(message);
        }

    }
}
