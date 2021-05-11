package b21.github;

import io.restassured.RestAssured;
//import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import  net.serenitybdd.junit5.SerenityTest;

//import static io.restassured.RestAssured.*;
@SerenityTest   // important:for see the full report (get full access) we have to press mvn->clean->verify->
public class GitHubUserTest {
//send GET https://api.github.com/users/CybertekSchool
@DisplayName("get https://api.github.com/users/CybertekSchool")
  @Test
public void testGithubUser1(){
//SerenityRest.
given()
   .pathParam("AA","CybertekSchool")
  .log().all()
  .when()
  .get("/users/{AA}")
  .then()
  .log().all()
  .statusCode(200)
  ;

}

    @DisplayName("get https://api.github.com/users/CybertekSchool to do more tests")
    @Test
    public void testGithubUser2(){
SerenityRest.
        given()
                .pathParam("user_id","CybertekSchool")
              //  .log().all()
           .when()
                .get("/users/{user_id}")
        ;
// if you send request using SerenityRest, the response object
        //can be obtained from the method called lastResponse() without being saved separately
//lastResponse() only picking up the response
        System.out.println("SerenityRest.lastResponse().statusCode() = " + lastResponse().statusCode());
        System.out.println("lastResponse().header(\"Date\") = " + lastResponse().header("Date"));

//print the response field login and id
String loginFieldValue=lastResponse().path("login") ;
        System.out.println("loginFieldValue = " + loginFieldValue);
int fieldId=lastResponse().jsonPath().getInt("id");
        System.out.println("fieldId = " + fieldId);
    }


    @DisplayName("get https://api.github.com/users/CybertekSchool to do more tests with Ensure.that()")
    @Test
    public void testGithubUser3() {
        SerenityRest.
                given()
                .pathParam("user_id", "CybertekSchool")
                //  .log().all()
                .when()
                .get("/users/{user_id}")
        ;
//our objective is to let each assertion show up in the report as step
 //check status code and let it show in the report
        // this is import:import net.serenitybdd.rest.Ensure

        Ensure.that("response run successully",validationOfResponse->validationOfResponse.statusCode(200));
// let's do more assertions
        Ensure.that("login field value is CybertekSchool",p->p.body("login",is("CybertekSchool")));
Ensure.that("id is 33201481",p->p.body("id",is(33201481)));

    }





 @BeforeAll
 public static void init(){

     RestAssured.baseURI = "https://api.github.com";

 }
  @AfterAll
  public static void closeUp(){

     RestAssured.reset();
  }


}
