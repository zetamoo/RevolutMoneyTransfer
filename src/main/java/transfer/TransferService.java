package transfer;

import common.Transaction;
import user.UserService;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

public class TransferService {
    // This contains the history of transactions.
    // TODO: Convert to a SQL database.
    static protected List<String> book = new LinkedList<>();

    public static void transfer(Transaction transaction) {
        UserService.transfer(transaction);
        book.add(String.format("%s, %s", transaction.toString(), Instant.now()));
    }
}
