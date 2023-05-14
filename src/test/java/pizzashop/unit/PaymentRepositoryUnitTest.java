package pizzashop.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import pizzashop.PizzaException;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;

import java.io.FileWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PaymentRepositoryUnitTest {

    public static final String TEST_FILENAME = "data/empty.txt";
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        try (FileWriter writer = new FileWriter(TEST_FILENAME)) {
            writer.write("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        paymentRepository = new PaymentRepository("data/empty.txt");
    }

    @Test
    @DisplayName("add")
    public void add() {
        Payment payment = mock(Payment.class);
        paymentRepository.add(payment);

        assert(paymentRepository.paymentList.size() == 1);
    }

    @Test
    @DisplayName("get")
    public void get() {
        Payment payment = mock(Payment.class);
        paymentRepository.add(payment);

        assert(paymentRepository.getAll().size() == 1);
        assert(paymentRepository.getAll().contains(payment));
    }
}