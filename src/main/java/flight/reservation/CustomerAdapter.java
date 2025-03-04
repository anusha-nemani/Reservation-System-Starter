package flight.reservation;

public class CustomerAdapter implements User {

    private Customer customer;

    public CustomerAdapter(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String getName() {
        return customer.getName();
    }
}