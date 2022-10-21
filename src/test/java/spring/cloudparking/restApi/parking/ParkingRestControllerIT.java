package spring.cloudparking.restApi.parking;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import spring.cloudparking.system.database.DatabaseTestContainer;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ParkingRestControllerIT
{
    @LocalServerPort
    private int port;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeAll
    public static void setUpDatabaseContainer()
    {
        DatabaseTestContainer.start();

    }
    @BeforeEach
    public
    void setUpTest()
    {
        RestAssured.port = port;
    }

    @Test
    public void testGet()
    {
        when()
            .get("/")
        .then()
            .statusCode(HttpStatus.OK.value())
            .and()
            .extract()
                .response()
                    .body().toString().contains("Hello World!");
    }

    @Test
    public void testGetBillingTypes()
    {
        given()
            .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .auth().preemptive()
                .basic("user", "password")
        .when()
            .get("/parking/BillingTypes")
        .then()
            .statusCode(HttpStatus.OK.value())
            .and()
                .extract().response().print();

    }

    @Test
    public void testGetParking()
    {
        given()
            .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
           .auth().preemptive()
               .basic("user", "password")

        .when()
           .get("/parking")
        .then()
           .statusCode(HttpStatus.OK.value())
           .and()
           .extract().response().print();
    }

    @Test
    public void parkingCreation()
    {
        given()
            .header("Content-Type", "application/json")
               .contentType(ContentType.JSON)
               .accept(ContentType.JSON)
            .auth().preemptive()
                .basic("user", "password")
            .body("""
                        {
                         "license": "Jeep",
                         "state": "Pr",   \s
                         "model": "2021", \s
                         "color": "cinza", \s
                         "billingTypeName" : "HOUR_BILLING" \s
                        }""")
                .contentType(ContentType.JSON)

        .when()
            .post("/parking/checkin")
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .and()
            .extract().response().print();
    }

    @Test
    public void testGetParkingWithId()
    {
        parkingCreation();

        given()
            .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .auth().preemptive()
                .basic("user", "password")
            .when()
                .get("/parking/1")
            .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                    .response().body()
                .toString().contains("jeep");
    }
}
