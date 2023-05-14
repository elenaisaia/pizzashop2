package pizzashop.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import pizzashop.PizzaException;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.io.FileWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PizzaServiceIntegrationTestWithPaymentRepository {

    public static final String TEST_FILENAME = "data/test.txt";
    private MenuRepository menuRepository;
    private PaymentRepository paymentRepository;
    private PizzaService pizzaService;

    @BeforeEach
    public void setUp() {
        clearTestFile();
        menuRepository = new MenuRepository();
        paymentRepository = new PaymentRepository(TEST_FILENAME);
        pizzaService = new PizzaService(menuRepository, paymentRepository);
    }

    public void clearTestFile() {
        try (FileWriter writer = new FileWriter(TEST_FILENAME)) {
            writer.write("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("addPayment_valid")
    public void addPaymentValid() throws Exception {
        Payment validPayment = new PaymentStubValid();

        pizzaService.addPayment(validPayment.getTableNumber(), validPayment.getType(), validPayment.getAmount());

        assert(paymentRepository.getAll().contains(validPayment));
    }

    @Test
    @DisplayName("addPayment_invalid")
    public void addPaymentInvalid() throws PizzaException {
        Payment invalidPayment = new PaymentStubInvalid();

        PizzaException exception = Assertions.assertThrows(PizzaException.class, () -> {
            pizzaService.addPayment(invalidPayment.getTableNumber(), invalidPayment.getType(), invalidPayment.getAmount());
        });

        Assertions.assertEquals("Invalid data!", exception.getMessage());
    }
}