package user;

import common.Transaction;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {

    @BeforeClass
    public static void setup() {
        UserService.createUser();
        UserService.createUser();
        UserService.changeBalanceBy("0", Currency.getInstance("USD"), BigDecimal.valueOf(100));
    }

    @Test
    public void testSimpleTransfer() {
        Transaction transaction = new Transaction("0", "1", BigDecimal.valueOf(60), Currency.getInstance("USD"));
        UserService.transfer(transaction);
        assertThat(UserService.getUserInfo("0")).isEqualTo("{\"USD\":{\"balance\":40}}");
        assertThat(UserService.getUserInfo("1")).isEqualTo("{\"USD\":{\"balance\":60}}");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroAmountTransfer() {
        Transaction transaction = new Transaction("0", "1", BigDecimal.ZERO, Currency.getInstance("USD"));
        UserService.transfer(transaction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAmountTransfer() {
        Transaction transaction = new Transaction("0", "1", BigDecimal.ONE.negate(), Currency.getInstance("USD"));
        UserService.transfer(transaction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidUserTransfer() {
        Transaction transaction = new Transaction("0", "2", BigDecimal.ZERO, Currency.getInstance("USD"));
        UserService.transfer(transaction);
    }
}