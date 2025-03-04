package flight.reservation.payment;
public class PaymentProcessorFactory {
public static PaymentProcessor getPaymentProcessor(Object paymentDetail) {
  if (paymentDetail instanceof CreditCard) {
    return new CreditCardProcessor((CreditCard) paymentDetail);
  } else if (paymentDetail instanceof String[]) {
    String[] paypalDetails = (String[]) paymentDetail;
    return new PayPalProcessor(paypalDetails[0], paypalDetails[1]);
  } else {
    throw new IllegalArgumentException("Invalid payment detail");
  }
}
}
