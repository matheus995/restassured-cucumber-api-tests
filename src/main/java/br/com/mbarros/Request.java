package br.com.mbarros;

import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Java class that contains all the fields to create a request
 * and the path to perform schema tests
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    RequestSpecification requestSpec;

    String jsonSchemaPath;
}
