package b21.spartan.user;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import spartan_util.SpartanUtil;

import java.util.HashMap;
import java.util.Map;

import static net.serenitybdd.junit5.SerenityTest.*;
import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.Matchers.*;
@SerenityTest
public class SpartanUserRoleTest {

    //this is the way to change display name of parameterized test
    //accessing row number using {index}
    //accessing the parameter by order {argIndex}

@ParameterizedTest(name = "Test {index} : GET /spartans/{0}")

@ValueSource(ints = {1,3,5,7,8,99})
public void testUserGetSingleSpartan(int SpartanIDArg){
    System.out.println("SpartanIDAgs = " + SpartanIDArg);
//send GET /spartans/{id} check status 200
    given()
            .auth().basic("user","user")
            .pathParam("id",SpartanIDArg)
            .when()
            .get("/spartans/{id}");

Ensure.that("Status Code is 200",p->p.statusCode(200));

        }


    @BeforeAll
    public static void init(){

        RestAssured.baseURI = "http://54.227.116.12:7000";
        RestAssured.basePath="/api";
    }
    @AfterAll
    public static void closeUp(){

        RestAssured.reset();
    }
}
