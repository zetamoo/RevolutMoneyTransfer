package user;

import java.math.BigDecimal;

public class Account {
    protected BigDecimal balance;

    public Account() {
        this.balance = BigDecimal.ZERO;
    }

    public void changeBalanceBy(BigDecimal amount) {
        BigDecimal newBalance = balance.add(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
            balance = newBalance;
        } else {
            // TODO: Create custom Exceptions.
            throw new IllegalArgumentException("Insufficient balance.");
        }
    }

    @Override
    public String toString() {
        return balance.toString();
    }
}
