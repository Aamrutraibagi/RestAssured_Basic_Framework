package api.tests;

import api.pojo.PostRequest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class PostsApiTest {

    private int userIdFromGetApi;
    private int postIdFromGetApi;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test(priority = 1)
    public void getPostAndExtractDataUsingJsonPath() {

        Response response =
                given()
                        .log().all()
                        .when()
                        .get("/posts/1")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .response();

        JsonPath jsonPath = response.jsonPath();

        postIdFromGetApi = jsonPath.getInt("id");
        userIdFromGetApi = jsonPath.getInt("userId");

        String title = jsonPath.getString("title");
        String body = jsonPath.getString("body");

        Assert.assertEquals(postIdFromGetApi, 1, "Post ID mismatch");
        Assert.assertTrue(userIdFromGetApi > 0, "User ID should be greater than 0");
        Assert.assertNotNull(title, "Title should not be null");
        Assert.assertNotNull(body, "Body should not be null");
    }

    @Test(priority = 2, dependsOnMethods = "getPostAndExtractDataUsingJsonPath")
    public void postUsingPojoSerializationAndDeserializeResponse() {

        PostRequest requestBody = new PostRequest(
                "Created using POJO",
                "This POST request uses userId extracted from GET response",
                userIdFromGetApi
        );

        Response response =
                given()
                        .log().all()
                        .header("Content-Type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/posts")
                        .then()
                        .log().all()
                        .statusCode(201)
                        .body(matchesJsonSchema(new File("src/test/resources/schemas/post-response-schema.json")))
                        .extract()
                        .response();

        PostRequest responsePojo = response.as(PostRequest.class);

        Assert.assertEquals(responsePojo.getTitle(), "Created using POJO");
        Assert.assertEquals(responsePojo.getBody(), "This POST request uses userId extracted from GET response");
        Assert.assertEquals(responsePojo.getUserId(), userIdFromGetApi);

        int createdId = response.jsonPath().getInt("id");
        Assert.assertTrue(createdId > 0, "Created ID should be generated");
    }

    @Test(priority = 3, dependsOnMethods = "getPostAndExtractDataUsingJsonPath")
    public void postUsingJsonFileBody() {

        File jsonFile = new File("src/test/resources/testdata/post-request.json");

        Response response =
                given()
                        .log().all()
                        .header("Content-Type", "application/json")
                        .body(jsonFile)
                        .when()
                        .post("/posts")
                        .then()
                        .log().all()
                        .statusCode(201)
                        .body(matchesJsonSchema(new File("src/test/resources/schemas/post-response-schema.json")))
                        .extract()
                        .response();

        Assert.assertEquals(response.jsonPath().getString("title"), "API testing from JSON file");
        Assert.assertEquals(response.jsonPath().getInt("userId"), 1);
    }

    @Test(priority = 4, dependsOnMethods = "getPostAndExtractDataUsingJsonPath")
    public void postUsingStringBody() {

        String requestBody = "{\n" +
                "  \"title\": \"Created using String body\",\n" +
                "  \"body\": \"This is raw JSON string payload\",\n" +
                "  \"userId\": " + userIdFromGetApi + "\n" +
                "}";

        Response response =
                given()
                        .log().all()
                        .header("Content-Type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/posts")
                        .then()
                        .log().all()
                        .statusCode(201)
                        .body(matchesJsonSchema(new File("src/test/resources/schemas/post-response-schema.json")))
                        .extract()
                        .response();

        Assert.assertEquals(response.jsonPath().getString("title"), "Created using String body");
        Assert.assertEquals(response.jsonPath().getInt("userId"), userIdFromGetApi);
    }

    @Test(priority = 5, dependsOnMethods = "getPostAndExtractDataUsingJsonPath")
    public void postUsingJsonObjectBody() {

        JSONObject requestBody = new JSONObject();
        requestBody.put("title", "Created using JSONObject");
        requestBody.put("body", "This request body is created using org.json.JSONObject");
        requestBody.put("userId", userIdFromGetApi);

        Response response =
                given()
                        .log().all()
                        .header("Content-Type", "application/json")
                        .body(requestBody.toString())
                        .when()
                        .post("/posts")
                        .then()
                        .log().all()
                        .statusCode(201)
                        .body(matchesJsonSchema(new File("src/test/resources/schemas/post-response-schema.json")))
                        .extract()
                        .response();

        Assert.assertEquals(response.jsonPath().getString("title"), "Created using JSONObject");
        Assert.assertEquals(response.jsonPath().getInt("userId"), userIdFromGetApi);
    }
}