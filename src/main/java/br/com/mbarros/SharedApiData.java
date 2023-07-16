package br.com.mbarros;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * The SharedApiData class represents shared data and operations for API requests and responses.
 */
public class SharedApiData {

    private final String schemaFolder = "schemas/";

    @Getter
    @Setter
    private Response response;

    @Getter
    @Setter
    private Request request = new Request();

    private Map<String, Object> queryParams = new HashMap<>();
    private Map<String, Object> pathParams = new HashMap<>();
    private Map<String, Object> headers = new HashMap<>();
    private Map<String, Object> body = null;
    private String jsonSchemaFile = "";

    /**
     * Prepares an HTTP request based on the provided configurations.
     *
     * @param requestSpecificationConfig The request specification configurations.
     * @return A Request object representing the prepared request.
     */
    public Request prepareRequest(RequestSpecification requestSpecificationConfig) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addRequestSpecification(requestSpecificationConfig)
                .addPathParams(pathParams)
                .addQueryParams(queryParams)
                .build();

        if (body != null) {
            requestSpecification.contentType(ContentType.JSON);
            requestSpecification.body(body);
        }

        return Request.builder().requestSpec(requestSpecification).jsonSchemaPath(schemaFolder + jsonSchemaFile).build();
    }

    public void setQueryParams(Map<String, Object> queryParams) {
        this.queryParams = queryParams;
    }

    public void addQueryParam(String field, Object value) {
        queryParams.put(field, value);
    }

    public Map<String, Object> getPathParams() {
        return pathParams;
    }

    public void addPathParam(String field, Object value) {
        pathParams.put(field, value);
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headersParams) {
        this.headers = headersParams;
    }

    public void addHeader(String field, Object value) {
        headers.put(field, value);
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    public void addBodyParam(String field, Object value) {
        body.put(field, value);
    }

    public String getJsonSchemaFile() {
        return jsonSchemaFile;
    }

    public void setJsonSchemaFile(String schema) {
        jsonSchemaFile = schemaFolder + schema;
    }
}
