package common;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.concurrent.atomic.AtomicLong;

public class Transaction {
    static private AtomicLong currentTransId = new AtomicLong(0);

    private final String fromId;
    private final String toId;
    private final BigDecimal amount;
    private final Currency currency;
    private final long transactionId;

    public Transaction(String fromId, String toId, BigDecimal amount, Currency currency) {
        this.fromId = fromId;
        this.toId = toId;
        this.amount = amount;
        this.currency = currency;
        this.transactionId = currentTransId.getAndIncrement();
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s", fromId, toId, amount, currency, transactionId);
    }
}
