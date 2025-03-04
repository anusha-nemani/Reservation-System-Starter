package flight.reservation.flight;



public interface FlightSubject {
    void registerFlightObserver(FlightObserver observer);
    void removeFlightObserver(FlightObserver observer);
    void notifyFlightObserver(String message);
}
