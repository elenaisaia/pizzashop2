package pizzashop.service;

import org.junit.jupiter.api.*;
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

import java.io.FileWriter;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PizzaServiceTest {
    public static final String TEST_FILENAME = "data/test.txt";
    private MenuRepository menuRepository;
    private PaymentRepository paymentRepository;
    private PizzaService pizzaService;

    private static final PaymentType type = PaymentType.Card;

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
    @DisplayName("TC1_ECP")
    @Timeout(1)
    public void givenTableNumber1AndVal420Point69_whenAddPayment_thenPaymentIsAdded() {
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
    @DisplayName("TC2_ECP")
    public void givenTableNumber3AndValMinus455_whenAddPayment_thenExceptionIsThrown() {
        //given
        int tableNumber = 3;
        double amount = -455;

        //when + then
        assertThrows(Exception.class, () -> {
            pizzaService.addPayment(tableNumber, type, amount);
        });
    }

    @Test
    @DisplayName("TC3_ECP")
    public void givenTableNumberMinus5AndVal800Point85_whenAddPayment_thenExceptionIsThrown() {
        //given
        int tableNumber = -5;
        double amount = 800.55;

        //when + then
        assertThrows(Exception.class, () -> {
            pizzaService.addPayment(tableNumber, type, amount);
        });
    }

    @Test
    @DisplayName("TC1_BVA")
    public void givenTableNumber0AndVal0Point5_whenAddPayment_thenExceptionIsThrown() {
        //given
        int tableNumber = 0;
        double amount = 0.5;

        //when + then
        assertThrows(Exception.class, () -> {
            pizzaService.addPayment(tableNumber, type, amount);
        });
    }

    @Test
    @DisplayName("TC2_BVA")
    public void givenTableNumber8AndVal0Point5_whenAddPayment_thenPaymentIsAdded() {
        //given
        int tableNumber = 8;
        double amount = 0.5;

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
    @DisplayName("TC3_BVA")
    public void givenTableNumber1AndVal0_whenAddPayment_thenExceptionIsThrown() {
        //given
        int tableNumber = 1;
        double amount = 0;

        //when + then
        assertThrows(Exception.class, () -> {
            pizzaService.addPayment(tableNumber, type, amount);
        });
    }

    @Test
    @DisplayName("TC4_BVA")
    public void givenTableNumber1AndVal0Point5_whenAddPayment_thenPaymentIsAdded() {
        //given
        int tableNumber = 1;
        double amount = 0.5;

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

    @ParameterizedTest
    @DisplayName("Parametrized with static method")
    @MethodSource("getValidPayments")
    public void givenValidProduct_whenAddProduct_thenProductIsSaved(int tableNumber, PaymentType type, double amount) {
        // when
        try {
            pizzaService.addPayment(tableNumber, type, amount);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // then
        assert(paymentRepository.getAll().contains(new Payment(tableNumber, type, amount)));
    }

    @ParameterizedTest
    @DisplayName("Parametrized with static class")
    @ArgumentsSource(PaymentArgumentsProvider.class)
    public void givenValidProductByProvider_whenAddProduct_thenProductIsSaved(int tableNumber, PaymentType type, double amount) {
        // when
        try {
            pizzaService.addPayment(tableNumber, type, amount);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // then
        assert(paymentRepository.getAll().contains(new Payment(tableNumber, type, amount)));

    }

    public static class PaymentArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            // given
            return Stream.of(
                    Arguments.arguments(1, type, 420.69),
                    Arguments.arguments(8, type, 0.5),
                    Arguments.arguments(1, type, 0.5)
            );
        }
    }

    public static Stream<Arguments> getValidPayments() {
        // given
        return Stream.of(
                Arguments.arguments(1, type, 420.69),
                Arguments.arguments(8, type, 0.5),
                Arguments.arguments(1, type, 0.5)
        );
    }
}