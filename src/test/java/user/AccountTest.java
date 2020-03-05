package user;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static org.junit.Assert.assertEquals;

public class AccountTest {

    Account account;

    @Before
    public void setup() {
        account = new Account();
    }

    @Test
    public void testSimpleChangingBalance() {
        assertEquals(account.balance, ZERO);
        account.changeBalanceBy(BigDecimal.valueOf(100));
        assertEquals(account.balance, BigDecimal.valueOf(100));
        account.changeBalanceBy(BigDecimal.valueOf(-10));
        assertEquals(account.balance, BigDecimal.valueOf(90));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidChangingBalance() {
        assertEquals(account.balance, ZERO);
        account.changeBalanceBy(BigDecimal.valueOf(100));
        assertEquals(account.balance, BigDecimal.valueOf(100));
        account.changeBalanceBy(BigDecimal.valueOf(-100));
        assertEquals(account.balance, ZERO);
        account.changeBalanceBy(BigDecimal.ONE.negate());
    }
}