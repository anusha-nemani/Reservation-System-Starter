package flight.reservation.payment;
public class PayPalProcessor implements PaymentProcessor {
  private final String email;
  private final String password;
  public PayPalProcessor(String email, String password) {
    this.email = email;
    this.password = password;
  }
  @Override
  public boolean processPayment(double amount) {
    System.out.println("Paying " + amount + " using PayPal.");
    if (email.equals(Paypal.DATA_BASE.get(password))) {
      return true;
    } else{
      throw new IllegalStateException("Invalid PayPal credentials");
    }
  }
}
