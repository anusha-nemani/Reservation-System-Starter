package flight.reservation.order;

import flight.reservation.Customer;
import flight.reservation.CreditCardProcessor;
import flight.reservation.PaypalProcessor;
import flight.reservation.PaymentProcessor;
import flight.reservation.PaymentProcessorFactory;
import flight.reservation.flight.ScheduledFlight;
import flight.reservation.payment.CreditCard;
import flight.reservation.payment.Paypal;
import flight.reservation.paymentstrategy.*

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FlightOrder extends Order {
    private final List<ScheduledFlight> flights;
    static List<String> noFlyList = Arrays.asList("Peter", "Johannes");

    public FlightOrder(List<ScheduledFlight> flights) {
        this.flights = flights;
    }

    public static List<String> getNoFlyList() {
        return noFlyList;
    }

    public List<ScheduledFlight> getScheduledFlights() {
        return flights;
    }

    private boolean isOrderValid(Customer customer, List<String> passengerNames, List<ScheduledFlight> flights) {
        boolean valid = true;
        valid = valid && !noFlyList.contains(customer.getName());
        valid = valid && passengerNames.stream().noneMatch(passenger -> noFlyList.contains(passenger));
        valid = valid && flights.stream().allMatch(scheduledFlight -> {
            try {
                return scheduledFlight.getAvailableCapacity() >= passengerNames.size();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                return false;
            }
        });
        return valid;
    }

    public boolean processOrderWithCreditCardDetail(String number, Date expirationDate, String cvv) throws IllegalStateException {
        CreditCard creditCard = new CreditCard(number, expirationDate, cvv);
        return processOrderWithCreditCard(creditCard);
    }

    public boolean processOrderWithCreditCard(CreditCard creditCard) throws IllegalStateException {
        if (isClosed()) {
            // Payment is already proceeded
            return true;
        }

        PaymentStrategy paymentStrategy = new CreditCardPaymentStrategy(creditCard, this.getPrice());
        PaymentContext paymentContext = new PaymentContext(paymentStrategy);

        boolean isPaid = paymentContext.processOrder();
        if (isPaid) {
            this.setClosed();
        }
        return isPaid;
    }

    public boolean processOrderWithPayPal(String email, String password) throws IllegalStateException {
        if (isClosed()) {
            // Payment is already proceeded
            return true;
        }

        PaymentStrategy paymentStrategy = new PaypalPaymentStrategy(email, password, this.getPrice());
        PaymentContext paymentContext = new PaymentContext(paymentStrategy);

        boolean isPaid = paymentContext.processOrder();

        if (isPaid) {
            this.setClosed();
        }
        return isPaid;
    }

    public boolean processOrder(Object paymentDetails) throws IllegalStateException {
        if (isClosed()) {
            return true;
        }
        PaymentProcessor processor = PaymentProcessorFactory.getPaymentProcessor(paymentDetails);
        boolean isPaid = processor.processPayment(this.getPrice());
        if (isPaid) {
            this.setClosed();
        }
        return isPaid;
    }
}
