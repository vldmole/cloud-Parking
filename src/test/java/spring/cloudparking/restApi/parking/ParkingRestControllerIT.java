package spring.cloudparking.restApi.parking;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingRestControllerIT
{
    @LocalServerPort
    private int port;

    @BeforeEach
    public
    void setUpTest(){

        RestAssured.port = port;
    }

    @Test
    public void testGetParking()
    {
       RestAssured.when()
       .get("/parking")
           .then()
               .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testGetParkingWithId()
    {
        RestAssured.when()
                .get("/parking/1")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void parkingCreation()
    {
        RestAssured
            .given()
                .body("{\n"+
                        " \"license\": \"Jeep\",\n" +
                        " \"state\": \"Pr\",    \n"+
                        " \"model\": \"2021\",  \n"+
                        " \"color\": \"cinza\"  \n"+
                        "}")
                .contentType(ContentType.JSON)
            .when()
                .post("/parking")
            .then()
                .statusCode(HttpStatus.CREATED.value());
    }
}
