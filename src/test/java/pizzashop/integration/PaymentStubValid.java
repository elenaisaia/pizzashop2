package pizzashop.integration;

import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

public class PaymentStubValid extends Payment {
    public PaymentStubValid() {
        super(1, PaymentType.Cash, 123.45);
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
