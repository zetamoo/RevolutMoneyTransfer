package user;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;

public class User {
    // This class should contain all the User info. E.g. email, phone number, address, etc.

    private final HashMap<Currency, Account> accounts = new HashMap<>();

    public Account getAccount(Currency currency) {
        return accounts.computeIfAbsent(currency, v -> new Account());
    }

    public void changeBalanceBy(Currency currency, BigDecimal amount) {
        getAccount(currency).changeBalanceBy(amount);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(accounts);
    }
}
