package b21.spartan.admin;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.*;

import static net.serenitybdd.rest.RestRequests.given;
import static org.hamcrest.Matchers.hasSize;

@SerenityTest
public class SpartanAdminTest {

@DisplayName("Test Admin GET /spartans endpoint")

@Test
@Disabled  //ignore this test
public void testAllSpartans(){
SerenityRest.
        given().
        auth().basic("admin","admin")
         .log().all()
     .when()
         .get("/spartans")
// .then()
//        .statusCode(200)
//        .contentType(ContentType.JSON)
//


         ;

 //belove is formula for Serenity Assertion formula
// Ensure.that("your discription",yourVariableName->yourVariableName.yourThenSectionAssertions);
    Ensure.that("get StatusCode(200)",p->p.statusCode(200));
    Ensure.that("get contentType is json",p->p.contentType(ContentType.JSON));

  //check the size of json Array

    Ensure.that("Response has the correct size",p->p.body("",hasSize(114)));



}


    @DisplayName("test public user GET /spartans endpoint")

    @Test
    public void testPublicUserGetSpartanData() {
SerenityRest.get("/spartans");
//ensure statusCode is 401
  Ensure.that("public user not got all spartans",p->p.statusCode(401));


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
