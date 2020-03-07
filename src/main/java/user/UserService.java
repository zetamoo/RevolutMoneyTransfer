package user;

import common.Transaction;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import static java.math.BigDecimal.ZERO;

public class UserService {
    // TODO: Convert to a NoSQL database.
    private static HashMap<String, User> users = new HashMap<>();

    // TODO: User email could be used as the unique userId instead.
    private static AtomicLong userId = new AtomicLong();

    public static void transfer(Transaction transaction) {
        Currency currency = transaction.getCurrency();
        BigDecimal amount = transaction.getAmount();

        if (amount.compareTo(ZERO) <= 0) {
            // TODO: Create custom Exceptions.
            throw new IllegalArgumentException("Transferred amount must be positive.");
        }

        // TODO: Add locks.
        User fromUser = getUser(transaction.getFromId());
        User toUser = getUser(transaction.getToId());
        fromUser.changeBalanceBy(currency, amount.negate());
        try {
            toUser.changeBalanceBy(currency, amount);
        } catch (Exception e) {
            fromUser.changeBalanceBy(currency, amount);
            // TODO: Create custom Exceptions.
            throw new IllegalArgumentException("Unable to complete transaction.");
        }
    }

    private static User getUser(String id) {
        // TODO: Create custom Exceptions.
        return users.computeIfAbsent(id, key -> {
            throw new IllegalArgumentException("User " + key + " not found.");
        });
    }

    public static String getUserInfo(String id) {
        return getUser(id).toString();
    }

    public static String createUser() {
        String id = String.valueOf(userId.getAndIncrement());
        users.put(id, new User());
        return id;
    }

    public static void changeBalanceBy(String userId, Currency currency, BigDecimal amount) {
        getUser(userId).changeBalanceBy(currency, amount);
    }

    public static void clear() {
        users.clear();
        userId = new AtomicLong();
    }
}
