package pizzashop.service;

import org.junit.jupiter.api.*;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.*;

public class PizzaServiceTestWBT {
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
    @DisplayName("F02_TC01")
    public void givenEmptyListAndCash_whenGetTotalAmount_thenReturn0() {
        //given

        //when
        double total = pizzaService.getTotalAmount(PaymentType.Cash);

        //then
        assert(total == 0.0);
    }

    @Test
    @DisplayName("F02_TC02")
    public void givenNonEmptyListAndCard_whenGetTotalAmount_thenReturn0() {
        //given

        //when
        double total = pizzaService.getTotalAmount(PaymentType.Card);

        //then
        assert(total == 0.0);
    }

    @Test
    @DisplayName("F02_TC03")
    public void givenNonEmptyListAndCash_whenGetTotalAmount_thenReturnGraterThan0() {
        //given
        try {
            pizzaService.addPayment(4,PaymentType.Cash,13.97);
            pizzaService.addPayment(1,PaymentType.Cash,80.08);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        //when
        double total = pizzaService.getTotalAmount(PaymentType.Cash);

        //then
        assert(total == 94.05);
    }
}