package pizzashop.integration;

import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

public class PaymentStubInvalid extends Payment {
    public PaymentStubInvalid() {
        super(3, PaymentType.Cash, -2.5);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Payment payment = (Payment) o;
        return tableNumber == payment.tableNumber && Double.compare(payment.amount, amount) == 0 && type == payment.type;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
