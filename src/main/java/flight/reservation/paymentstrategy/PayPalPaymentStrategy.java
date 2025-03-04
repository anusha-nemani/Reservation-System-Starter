package flight.reservation.PaymentStrategy;

import flight.reservation.payment.Paypal;

public class PayPalPaymentStrategy implements PaymentStrategy {
    private double price;
    private String email;
    private String password;

    public PayPalPaymentStrategy(double price, String email, String password) {
        this.price = price;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean processOrder() {
        if (email == null || password == null || !email.equals(Paypal.DATA_BASE.get(password))) {
            throw new IllegalStateException("Payment information is not set or not valid.");
        }

        boolean isPaid = payWithPayPal(email, password, this.getPrice());
        return isPaid;
    }

    public boolean payWithPayPal(String email, String password, double amount) throws IllegalStateException {
        if (email.equals(Paypal.DATA_BASE.get(password))) {
            System.out.println("Paying " + getPrice() + " using PayPal.");
            return true;
        } else {
            return false;
        }
    }

    public double getPrice() {
        return this.price;
    }
}