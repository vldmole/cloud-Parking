package spring.cloudparking.restApi.parking;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import spring.cloudparking.system.database.DatabaseTestContainer;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ParkingRestControllerIT
{
    @LocalServerPort
    private int port;

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
                .body(is("Hello World!"));
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
                .assertThat()
                .body("size()", greaterThan(0));

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
        .then().assertThat()
            .statusCode(HttpStatus.CREATED.value())
            .and()
            .body("license", Matchers.is("Jeep"));
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
                .body("id", is(1));
    }
}
