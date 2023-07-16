package br.com.mbarros;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.Method;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * The ApiRequestHandler class is responsible for handling API requests and performing response validations.
 */
public class ApiRequestHandler {

    Boolean isToReport;

    /**
     * Default constructor that initializes the ApiRequestHandler object with reporting enabled.
     */
    public ApiRequestHandler() {
        isToReport = true;
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    /**
     * Constructor that allows specifying the reporting preference for the ApiRequestHandler object.
     *
     * @param isToReport A boolean indicating whether to enable reporting or not.
     */
    public ApiRequestHandler(Boolean isToReport) {
        this.isToReport = isToReport;
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    /**
     * Performs an HTTP request with the specified method and endpoint using the given request object.
     *
     * @param request  The request object containing the request specification.
     * @param method   The HTTP method to be used for the request.
     * @param endpoint The endpoint URL for the request.
     * @return A Response object representing the response obtained from the request.
     */
    public Response doRequest(Request request, Method method, String endpoint) {
        if (isToReport) {
            request.getRequestSpec().filter(new AllureRestAssured());
        } else {
            request.getRequestSpec().noFiltersOfType(AllureRestAssured.class);
        }

        return given()
                .spec(request.getRequestSpec())
                .log().all()
                .when()
                .request(method, endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    /**
     * Validates the JSON response against the specified JSON schema.
     *
     * @param response       The response object containing the JSON response.
     * @param jsonSchemaPath The path to the JSON schema used for validation.
     */
    public void validateJSONSchema(Response response, String jsonSchemaPath) {
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchemaPath));
    }
}