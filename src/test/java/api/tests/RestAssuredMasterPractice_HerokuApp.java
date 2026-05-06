package api.tests;

import api.pojo.Herokuapp_POJO1_Booking;
import api.pojo.Herokuapp_POJO2_BookingDates;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RestAssuredMasterPractice_HerokuApp {
    static int bookingId;
    static String token;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI ="https://restful-booker.herokuapp.com";
    }

    @Test(priority = 1,enabled = false)
    public void getAllBookingIds() {

        Response response=given().log().all()
                .when().get("/booking")
                .then().log().all()
                .statusCode(200).extract().response();

        bookingId=response.jsonPath().getInt("[0].bookingid");
        System.out.println("Booking ID from GET API: " + bookingId);
        Assert.assertTrue(bookingId>0);

    }

    @Test(priority = 2,dependsOnMethods = "getAllBookingIds",enabled = false)
    public void getBookingDetailsUsingBookingId() {
        Response response=given().log().all()
                .when().get("/booking/"+bookingId)
                .then()
                .statusCode(200).extract().response();

        String firstname=response.jsonPath().getString("firstname");
        String checkingDate=response.jsonPath().getString("bookingdates.checkin");

        Assert.assertEquals(firstname,"Mark");
        Assert.assertEquals(checkingDate,"2026-01-19");

    }

    @Test(priority = 3,enabled = false)
    public void createBookingUsingStringPayload() {

        String payload= """
                {
                  "firstname": "Amrut",
                  "lastname": "Raibagi",
                  "totalprice": 3000,
                  "depositpaid": true,
                  "bookingdates": {
                    "checkin": "2026-05-15",
                    "checkout": "2026-05-20"
                  },
                  "additionalneeds": "Dinner"
                }
                """;

        Response response=given().log().all()
                .contentType("application/json")
                .body(payload)
                .when().post("/booking")
                .then().log().all()
                .body("booking.lastname",equalTo("Raibagi"))
                .body("booking.firstname",equalTo("Amrut"))
                .statusCode(200).extract().response();

        String lastname=response.jsonPath().getString("booking.lastname");

        Assert.assertEquals(lastname,"Raibagi");


    }
    @Test(priority = 4,enabled = false)
    public void createBookingUsingJsonFilePayload() {
        File payloadFile = new File("src/test/resources/testdata/bookingPayload.json");

        Response response=given().log().all()
                //auth().basic("username","password")
                //header("autherization","Bearer "+token)
                //auth().Oauth2(token)
                .contentType("application/json")
                .body(payloadFile)
                .when().post("/booking")
                .then().log().all()
                .statusCode(200)
                .body("booking.firstname",equalTo("Amrut"))
                .extract().response();

        bookingId = response.jsonPath().getInt("bookingid");
        System.out.println("Created Booking ID using JSON file: " + bookingId);

    }

    @Test(priority = 5,enabled = false)
    public void createBookingUsingPojoPayload() {

        Herokuapp_POJO2_BookingDates bookingDates=
                new Herokuapp_POJO2_BookingDates("2026-06-01", "2026-06-05");

        Herokuapp_POJO1_Booking booking=new Herokuapp_POJO1_Booking(
                "SDET",
                "QA",
                200,
                true,
                bookingDates,
                "Lunch"
        );

        Response response=given().log().all()
                .contentType("application/json")
                .body(booking)
                .when().post("/booking")
                .then().log().all()
                .statusCode(200)
                .extract().response();
        bookingId = response.jsonPath().getInt("bookingid");

        System.out.println("Created Booking ID using POJO: " + bookingId);
    }

    @Test(priority = 10)
    public void uploadImageOrMediaFile() {

        RestAssured.baseURI = "https://httpbin.org";

        File file = new File("src/test/resources/testdata/Test.png");

        given().log().all()
                .multiPart("file",file)
                .contentType("multipart/form-data")
                .when().post("/post")
                .then().log().all()
                .statusCode(200)
                .body("files",notNullValue());

        System.out.println("File uploaded successfully");

    }

    @Test(priority = 6)
    public void createAuthToken() {

        String authPayload = """
                {
                  "username": "admin",
                  "password": "password123"
                }
                """;

        Response response =
                given()
                        .log().all()
                        .header("Content-Type", "application/json")
                        .body(authPayload)
                        .when()
                        .post("/auth")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response();

        token = response.jsonPath().getString("token");

        System.out.println("Generated Token: " + token);

        Assert.assertNotNull(token);
    }


}
