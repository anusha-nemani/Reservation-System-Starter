package flight.reservation.PaymentStrategy;

import flight.reservation.payment.CreditCard;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    private CreditCard creditCard;
    private double price;

    public CreditCardPaymentStrategy(CreditCard creditCard, double price) {
        this.creditCard = creditCard;
        this.price = price;
    }

    private boolean cardIsPresentAndValid(CreditCard card) {
        return card != null && card.isValid();
    }

    @Override
    public boolean processOrder() {
        if (!cardIsPresentAndValid(creditCard)) {
            throw new IllegalStateException("Payment information is not set or not valid");
        }

        boolean isPaid = payWithCreditCard(creditCard, getPrice());
        return isPaid;
    }

    public boolean payWithCreditCard(CreditCard card, double amount) throws IllegalStateException {
        if (cardIsPresentAndValid(card)) {
            System.out.println("Paying " + getPrice() + " using Credit Card.");
            double remainingAmount = card.getAmount() - getPrice();
            if (remainingAmount < 0) {
                System.out.printf("Card limit reached - Balance: %f%n", remainingAmount);
                throw new IllegalStateException("Card limit reached");
            }
            card.setAmount(remainingAmount);
            return true;
        } else {
            return false;
        }
    }

    public double getPrice() {
        return this.price;
    }
}