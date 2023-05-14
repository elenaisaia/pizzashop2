package pizzashop.unit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzashop.PizzaException;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class PizzaServiceUnitTest {

    @Mock
    private MenuRepository menuRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @InjectMocks
    private PizzaService pizzaService;


    @Test
    @DisplayName("addPayment_valid")
    public void addPaymentValid() throws Exception {
        int tableNumber = 1;
        PaymentType type = PaymentType.Card;
        double amount = 420.69;
        Payment payment = new Payment(tableNumber, type, amount);

        Mockito.when(paymentRepository.getAll()).thenReturn(List.of(payment));
        Mockito.doNothing().when(paymentRepository).add(payment);

        pizzaService.addPayment(tableNumber, type, amount);

        assert(paymentRepository.getAll().contains(new Payment(tableNumber, type, amount)));
    }

    @Test
    @DisplayName("addPayment_invalid")
    public void addPaymentInvalid() throws PizzaException {
        //given
        int tableNumber = 3;
        PaymentType type = PaymentType.Card;
        double amount = -455;

        PizzaException exception = Assertions.assertThrows(PizzaException.class, () -> {
            pizzaService.addPayment(tableNumber, type, amount);
        });

        Assertions.assertEquals("Invalid data!", exception.getMessage());
    }
}

