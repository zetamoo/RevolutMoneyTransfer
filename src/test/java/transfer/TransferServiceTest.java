package transfer;

import common.Transaction;
import org.junit.Test;
import user.UserService;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

public class TransferServiceTest {
    @Test
    public void testBookIsPopulatedCorrectly() {
        UserService.createUser();
        UserService.createUser();
        UserService.changeBalanceBy("0", Currency.getInstance("USD"), BigDecimal.valueOf(100));
        Transaction transaction = new Transaction("0", "1", BigDecimal.valueOf(50), Currency.getInstance("USD"));
        TransferService.transfer(transaction);
        assertThat(TransferService.book.get(0)).startsWith("0, 1, 50, USD, 0, ");
    }
}