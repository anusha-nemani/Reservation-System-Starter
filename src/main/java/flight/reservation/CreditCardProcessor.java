package flight.reservation.payment;
public class CreditCardProcessor implements PaymentProcessor {
  private final CreditCard creditCard;
  public CreditCardProcessor(CreditCard creditCard) {
    this.creditCard = creditCard;
  }
  @Override
  public boolean processPayment(double amount) {
    System.out.println("Paying " + amount + " using Credit Card.");
    double remainingAmount = creditCard.getAmount() - amount;
    if (remainingAmount < 0) {
      System.out.printf("Card limit reached - Balance: %f%n", remainingAmount);
      throw new IllegalStateException("Card limit reached");
    }
    creditCard.setAmount(remainingAmount);
    return true;
  }
}
