package b21.spartan.editor;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.*;
import spartan_util.SpartanUtil;
import java.util.Map;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.Matchers.*;
@SerenityTest
public class SpartanEditorRolePostTest {



    @DisplayName("Editor Should able to post Valid Data")

    @Test
    public void testEditorPostData() {
 Map<String,Object> bodyMap= SpartanUtil.getRandomSpartanMap();
SerenityRest
        .given()
        .auth().basic("editor","editor")
        .log().body()
        .contentType(ContentType.JSON)
        .body(bodyMap)
        .when()
        .post("/spartans").prettyPeek()
        ;

// Do all assertions here
        Ensure.that("It run successfully",p->p.statusCode(equalTo(201)));
Ensure.that("response format is correct",p->p.contentType(ContentType.JSON));
Ensure.that("success mesage is correct",p->p.body("success",is("A Spartan is Born!")));
Ensure.that("ID is generated not null",p->p.body("data.id",notNullValue()));

//checking actual data
 Ensure.that("name is correct",p->p.body("data.name",is(bodyMap.get("name"))));
 Ensure.that("gender is correct",p->p.body("data.gender",is(bodyMap.get("gender"))));
  Ensure.that("phone is correct",p->p.body("data.phone",is(bodyMap.get("phone"))));

//check location header end with newly generated id
   String newID=lastResponse().path("data.id").toString();
        System.out.println("lastResponse().header(\"location\") = " + lastResponse().header("location"));
Ensure.that("location header end with "+newID,p->p.header("Location",endsWith(newID)));

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
