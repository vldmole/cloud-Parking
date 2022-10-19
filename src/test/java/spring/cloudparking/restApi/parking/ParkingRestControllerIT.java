package spring.cloudparking.restApi.parking;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import spring.cloudparking.system.database.DatabaseTestContainer;

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

    @Order(2)
    @Test
    public void testGetParking()
    {
       RestAssured.when()
       .get("/parking")
           .then()
               .statusCode(HttpStatus.OK.value())
               .and()
               .extract().response().print();
    }

    @Order(0)
    @Test
    public void parkingCreation()
    {
        RestAssured
                .given()
                .body("""
                        {
                         "license": "Jeep",
                         "state": "Pr",   \s
                         "model": "2021", \s
                         "color": "cinza" \s
                        }""")
                .contentType(ContentType.JSON)
                .when()
                .post("/parking")
                .then()
                .statusCode(HttpStatus.CREATED.value()).and()
                .extract().response().print();
    }

    @Order(3)
    @Test
    public void testGetAll()
    {
        testGetParking();
    }

    @Order(1)
    @Test
    public void testGetParkingWithId()
    {
        parkingCreation();
        RestAssured.when()
                .get("/parking/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract().response().body().toString().contains("jeep");
    }
}
