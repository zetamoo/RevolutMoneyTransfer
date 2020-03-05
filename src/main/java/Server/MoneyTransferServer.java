package Server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import common.Transaction;
import transfer.TransferService;
import user.UserService;

import java.math.BigDecimal;
import java.util.Currency;

public class MoneyTransferServer extends AbstractVerticle {

    @Override
    public void start(Future<Void> fut) {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        server.requestHandler(router::accept).listen(8080);
        router.post("/transfer").handler(this::transfer);
        router.post("/user").handler(this::createUser);
        router.post("/deposit").handler(this::deposit);
        router.get("/user/info").handler(this::userInfo);
    }

    private void transfer(RoutingContext routingContext) {
        try {
            Transaction transaction = new Transaction(
                    routingContext.getBodyAsJson().getString("from"),
                    routingContext.getBodyAsJson().getString("to"),
                    getAmount(routingContext),
                    getCurrency(routingContext));
            TransferService.transfer(transaction);
            routingContext.response()
                    .setStatusCode(201)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end("Transaction Succeeded");
        } catch (Exception e) {
            routingContext.response().setStatusCode(400).end(e.getMessage());
        }
    }

    private void createUser(RoutingContext routingContext) {
        try {
            String userId = UserService.createUser();
            routingContext.response()
                    .setStatusCode(201)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(String.format("{\"user_id\": \"%s\"}", userId));
        } catch (Exception e) {
            routingContext.response().setStatusCode(400).end(e.getMessage());
        }
    }

    private void deposit(RoutingContext routingContext) {
        try {
            String userId = routingContext.getBodyAsJson().getString("user_id");
            BigDecimal amount = getAmount(routingContext);
            Currency currency = getCurrency(routingContext);
            UserService.changeBalanceBy(userId, currency, amount);
            routingContext.response()
                    .setStatusCode(201)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end();
        } catch (Exception e) {
            routingContext.response().setStatusCode(400).end(e.getMessage());
        }
    }

    private void userInfo(RoutingContext routingContext) {
        try {
            String userId = routingContext.request().getParam("user_id");
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(UserService.getUserInfo(userId));
        } catch (Exception e) {
            routingContext.response().setStatusCode(400).end(e.getMessage());
        }
    }

    private static Currency getCurrency(RoutingContext routingContext) {
        return Currency.getInstance(routingContext.getBodyAsJson().getString("currency"));
    }

    private static BigDecimal getAmount(RoutingContext routingContext) {
        return new BigDecimal(routingContext.getBodyAsJson().getString("amount"));
    }
}
