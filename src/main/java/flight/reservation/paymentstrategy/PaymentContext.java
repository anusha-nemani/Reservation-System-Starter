package flight.reservation.PaymentStrategy;

public class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public PaymentContext(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean processOrder() {
        return paymentStrategy.processOrder();
    }
}