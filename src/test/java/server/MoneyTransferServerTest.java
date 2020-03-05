package server;

import com.jayway.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

// TODO: Add more test scenarios.
public class MoneyTransferServerTest {
    @BeforeClass
    public static void configureRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @AfterClass
    public static void unconfigureRestAssured() {
        RestAssured.reset();
    }

    @Test
    public void testScenario() {
        given().when()
                .post("user")
                .then()
                .assertThat()
                .statusCode(201).assertThat().body("user_id", equalTo("0"));
        given().when()
                .post("user")
                .then()
                .assertThat()
                .statusCode(201).assertThat().body("user_id", equalTo("1"));
        given().body("{\n" +
                "    \"user_id\": \"0\",\n" +
                "    \"amount\": \"100\",\n" +
                "    \"currency\": \"EUR\"\n" +
                "}")
                .when()
                .post("deposit")
                .then()
                .assertThat()
                .statusCode(201);
        assertThat(get("user/info?user_id=0")
                .then().assertThat().statusCode(200).extract().as(HashMap.class).toString()).isEqualTo("{EUR={balance=100}}");
        get("user/info?user_id=1")
                .then().assertThat().statusCode(200).body("EUR", Matchers.nullValue());
        given().body("{\n" +
                "\"from\": \"0\",\n" +
                "\"to\": \"1\",\n" +
                "\"amount\": \"40\",\n" +
                "\"currency\": \"EUR\"\n" +
                "}").when()
                .post("transfer")
                .then()
                .assertThat()
                .statusCode(201);
        assertThat(get("user/info?user_id=0")
                .then().assertThat().statusCode(200).extract().as(HashMap.class).toString()).isEqualTo("{EUR={balance=60}}");
        assertThat(get("user/info?user_id=1")
                .then().assertThat().statusCode(200).extract().as(HashMap.class).toString()).isEqualTo("{EUR={balance=40}}");

    }
}