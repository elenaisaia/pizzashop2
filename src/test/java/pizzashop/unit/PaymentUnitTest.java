package pizzashop.unit;

import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import static org.junit.jupiter.api.Assertions.*;

class PaymentUnitTest {

    @Test
    public void getTableNumber() {
        Payment payment = new Payment(1, PaymentType.Cash, 123.45);

        assert(payment.getTableNumber() == 1);
    }

    @Test
    public void getType() {
        Payment payment = new Payment(1, PaymentType.Cash, 123.45);

        assert(payment.getType().equals(PaymentType.Cash));
    }
}