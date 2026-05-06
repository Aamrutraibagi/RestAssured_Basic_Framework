# RestAssuredApiPractice

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Build-Maven-red.svg)](https://maven.apache.org/)
[![Rest Assured](https://img.shields.io/badge/API%20Testing-Rest%20Assured-00a86b.svg)](https://rest-assured.io/)
[![TestNG](https://img.shields.io/badge/Test%20Runner-TestNG-f2b632.svg)](https://testng.org/)

RestAssuredApiPractice is a Java Maven project created for hands-on API automation practice using Rest Assured and TestNG. It demonstrates common real-world API testing techniques such as GET and POST requests, request body creation in multiple styles, response extraction, POJO serialization and deserialization, JSON schema validation, API chaining, authentication token generation, and multipart file upload.

## What This Project Covers

- REST API automation using Rest Assured
- Test execution using TestNG
- Maven-based dependency management
- GET request validation and response extraction
- POST request creation using:
  - Java POJO
  - JSON file
  - Raw JSON string
  - `org.json.JSONObject`
- JSON schema validation using `json-schema-validator`
- POJO serialization and deserialization
- API chaining by extracting values from one API response and reusing them in another request
- Authentication token generation using Restful Booker
- Multipart file upload using HTTPBin
- External test data management through `src/test/resources`

## Tech Stack

| Tool / Library | Purpose |
| --- | --- |
| Java 17 | Programming language |
| Maven | Build and dependency management |
| Rest Assured 5.5.0 | REST API automation |
| TestNG 7.10.2 | Test runner and assertions |
| JSON Schema Validator | API response schema validation |
| org.json | Creating JSON request bodies programmatically |

## APIs Used

| API | Base URL | Usage |
| --- | --- | --- |
| JSONPlaceholder | `https://jsonplaceholder.typicode.com` | Posts API practice, GET/POST, schema validation |
| Restful Booker | `https://restful-booker.herokuapp.com` | Booking API practice and token generation |
| HTTPBin | `https://httpbin.org` | Multipart file upload validation |

## Project Structure Skeleton

```text
RestAssuredApiPractice/
|-- pom.xml
|-- testng.xml
|-- Readme.md
|-- src/
|   |-- main/
|   |   `-- java/
|   |       `-- org/
|   |           `-- example/
|   |               `-- Main.java
|   `-- test/
|       |-- java/
|       |   `-- api/
|       |       |-- pojo/
|       |       |   |-- Herokuapp_POJO1_Booking.java
|       |       |   |-- Herokuapp_POJO2_BookingDates.java
|       |       |   `-- PostRequest.java
|       |       `-- tests/
|       |           |-- PostsApiTest.java
|       |           `-- RestAssuredMasterPractice_HerokuApp.java
|       `-- resources/
|           |-- schemas/
|           |   `-- post-response-schema.json
|           `-- testdata/
|               |-- bookingPayload.json
|               |-- post-request.json
|               `-- Test.png
```

## Important Files

| File | Description |
| --- | --- |
| `pom.xml` | Maven configuration with Rest Assured, TestNG, JSON schema validator, and JSON dependencies |
| `testng.xml` | TestNG suite file currently configured to run `PostsApiTest` |
| `PostsApiTest.java` | Main JSONPlaceholder API test class with GET, POST, POJO, JSON file, string body, JSONObject, and schema validation examples |
| `RestAssuredMasterPractice_HerokuApp.java` | Restful Booker and HTTPBin practice class with booking, auth token, and file upload examples |
| `PostRequest.java` | POJO for JSONPlaceholder post request and response mapping |
| `Herokuapp_POJO1_Booking.java` | Main booking POJO for Restful Booker payloads |
| `Herokuapp_POJO2_BookingDates.java` | Nested booking dates POJO |
| `post-response-schema.json` | JSON schema used to validate POST response structure |
| `post-request.json` | Sample JSON request payload for JSONPlaceholder POST API |
| `bookingPayload.json` | Sample JSON request payload for Restful Booker booking creation |
| `Test.png` | Sample media file used for multipart upload testing |

## Test Classes Overview

### `PostsApiTest`

This class uses `https://jsonplaceholder.typicode.com` as the base URI and is included in `testng.xml`.

Test flow:

1. `getPostAndExtractDataUsingJsonPath`
   - Sends `GET /posts/1`
   - Validates status code `200`
   - Extracts `id`, `userId`, `title`, and `body`
   - Stores `userId` and `postId` for later tests

2. `postUsingPojoSerializationAndDeserializeResponse`
   - Creates request body using `PostRequest` POJO
   - Sends `POST /posts`
   - Validates status code `201`
   - Validates response against `post-response-schema.json`
   - Deserializes response back into `PostRequest`

3. `postUsingJsonFileBody`
   - Reads payload from `post-request.json`
   - Sends `POST /posts`
   - Validates status code and schema

4. `postUsingStringBody`
   - Creates raw JSON as a Java string
   - Reuses `userId` extracted from the GET API
   - Validates response fields

5. `postUsingJsonObjectBody`
   - Creates request payload using `org.json.JSONObject`
   - Sends POST request and validates response

### `RestAssuredMasterPractice_HerokuApp`

This class contains practice examples for `https://restful-booker.herokuapp.com` and `https://httpbin.org`.

Covered scenarios:

- Get all booking IDs
- Get booking details by booking ID
- Create booking using raw string payload
- Create booking using JSON file payload
- Create booking using POJO payload
- Generate authentication token
- Upload image or media file using multipart request

Note: Some booking tests in this class are marked with `enabled = false`, so they will not execute until enabled manually.

## Prerequisites

Make sure the following are installed:

- Java 17 or higher
- Maven 3.8 or higher
- IntelliJ IDEA, Eclipse, VS Code, or any Java IDE
- Internet connection, because the tests call public APIs

Verify installation:

```bash
java -version
mvn -version
```

## How To Run Tests

Run the TestNG suite configured in `testng.xml`:

```bash
mvn test -DsuiteXmlFile=testng.xml
```

You can also run all Maven tests:

```bash
mvn test
```

To run a specific test class:

```bash
mvn test -Dtest=PostsApiTest
```

To run the Restful Booker practice class directly:

```bash
mvn test -Dtest=RestAssuredMasterPractice_HerokuApp
```

## Current TestNG Suite

The current `testng.xml` suite executes:

```xml
<class name="api.tests.PostsApiTest"/>
```

If you want to include the Restful Booker practice class in suite execution, add it inside the `<classes>` block:

```xml
<class name="api.tests.RestAssuredMasterPractice_HerokuApp"/>
```

## Sample API Scenarios

### GET Post

```bash
curl -X GET "https://jsonplaceholder.typicode.com/posts/1"
```

### Create Post

```bash
curl -X POST "https://jsonplaceholder.typicode.com/posts" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Created using String body",
    "body": "This is raw JSON string payload",
    "userId": 1
  }'
```

### Create Restful Booker Auth Token

```bash
curl -X POST "https://restful-booker.herokuapp.com/auth" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password123"
  }'
```

### Upload File

```bash
curl -X POST "https://httpbin.org/post" \
  -F "file=@src/test/resources/testdata/Test.png"
```

## Key Learning Points

- Use `given()`, `when()`, and `then()` to build readable Rest Assured tests.
- Extract response values with `JsonPath` when you need API chaining.
- Keep reusable request payloads under `src/test/resources/testdata`.
- Keep reusable schemas under `src/test/resources/schemas`.
- Use POJOs when request or response models are repeated across tests.
- Validate response contracts with JSON schema, not only individual field assertions.
- Use `dependsOnMethods` when one test requires data from another test.

## Good Practices Followed

- Test data is separated from test logic.
- Schema files are stored separately from Java code.
- POJOs are kept in a dedicated `api.pojo` package.
- Test classes are organized under `api.tests`.
- Base URI setup is handled using `@BeforeClass`.
- Assertions are written using TestNG and Hamcrest matchers.

## Notes

- JSONPlaceholder is a fake online REST API. POST requests return a successful created response, but data is not permanently saved on the server.
- Restful Booker test data can change because it is a public practice API.
- Tests require internet access and may fail if the external services are unavailable.
- File upload uses the sample `Test.png` file from the test resources folder.

## Author

Created for Rest Assured API automation practice and interview preparation.
