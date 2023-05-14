package pizzashop.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.io.FileWriter;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PizzaServiceAndPaymentRepositoryTestWithPayment {

    public static final String TEST_FILENAME = "data/test.txt";
    private MenuRepository menuRepository;
    private PaymentRepository paymentRepository;
    private PizzaService pizzaService;

    private static final PaymentType type = PaymentType.Card;

    @BeforeEach
    void setUp() {
        clearTestFile();
        menuRepository = new MenuRepository();
        paymentRepository = new PaymentRepository(TEST_FILENAME);
        pizzaService = new PizzaService(menuRepository, paymentRepository);
    }

    private void clearTestFile() {
        try (FileWriter writer = new FileWriter(TEST_FILENAME)) {
            writer.write("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("addPayment_valid")
    void addPaymentValid() {
        //given
        int tableNumber = 1;
        double amount = 420.69;

        //when
        try {
            pizzaService.addPayment(tableNumber, type, amount);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        //then
        assert(paymentRepository.getAll().contains(new Payment(tableNumber, type, amount)));
    }

    @Test
    @DisplayName("addPayment_invalid")
    void addPaymentInvalid() {
        //given
        int tableNumber = 3;
        double amount = -455;

        //when + then
        assertThrows(Exception.class, () -> {
            pizzaService.addPayment(tableNumber, type, amount);
        });
    }
}