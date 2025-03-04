package flight.reservation;

public class Passenger implements User {

    private final String name;

    public Passenger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
