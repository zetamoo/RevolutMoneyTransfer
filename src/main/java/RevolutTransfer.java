import Server.MoneyTransferServer;
import io.vertx.core.Vertx;
import org.apache.log4j.BasicConfigurator;

public class RevolutTransfer {
    public static void main(String[] args) {
        BasicConfigurator.configure();

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(MoneyTransferServer.class.getName());
    }
}