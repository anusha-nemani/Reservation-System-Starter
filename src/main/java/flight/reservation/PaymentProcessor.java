package flight.reservation.payment;
public interface PaymentProcessor {
  boolean processPayment(double amount);
}
