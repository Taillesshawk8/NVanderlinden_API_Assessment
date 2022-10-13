package unittest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class APITest {

    private static RequestSpecification requestSpec;

    private static ResponseSpecification responseSpec;

    @BeforeClass
    public static void createRequestSpecificationAndResponseSpecification() {

        requestSpec = new RequestSpecBuilder().
            setBaseUri("https://jsonplaceholder.typicode.com/posts").
            build();

        responseSpec = new ResponseSpecBuilder().
            expectStatusCode(200).
            expectContentType(ContentType.JSON).
            build();

    }

    //Step 1: Execute a GET request that lists all posts resources
    @Test
    public void getRequestForAllPosts() {

        given().
            spec(requestSpec).
        when().
        get().
        then().
            spec(responseSpec).
        log().
        body();

    }

    //Step 2: Execute a GET request that returns a single posts resource with id = 11
    @Test
    public void getSpecificPost() {

        given().
            spec(requestSpec).
        when().
        get("11").
        then().
            spec(responseSpec).
        log().
        body();

    }

    //Step 3: Execute a POST request to create a new posts resource
    @Test
    public void postRequestUsingJsonRequestBody(){

        JSONObject request = new JSONObject();

        request.put("title","foo");
        request.put("body","bar");
        request.put("userId",1);

        given().
            contentType(ContentType.JSON).
            body(request).
        when().
        post("https://jsonplaceholder.typicode.com/posts").
        then().
            assertThat().
            statusCode(201).
        log().
        body();
        System.out.println("Successfully posted Json body with status code 201");

    }

    //Step 4: Execute a DELETE request that removes the posts resource with id = 1
    @Test
    public void deleteRequest(){

        given().
        when().
        delete("https://jsonplaceholder.typicode.com/posts/1").
        then().
            assertThat().
            statusCode(200).
        log().
        body();
        System.out.println("Successfully deleted request with status code 200");

    }

}