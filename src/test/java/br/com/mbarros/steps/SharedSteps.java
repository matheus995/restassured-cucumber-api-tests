package br.com.mbarros.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import br.com.mbarros.ApiRequestHandler;
import br.com.mbarros.SharedApiData;

import static br.com.mbarros.ApiConstants.BASE_URI;
import static br.com.mbarros.ApiHelpers.transformData;

public class SharedSteps {
    final SharedApiData sharedApiData;
    ApiRequestHandler apiHelper = new ApiRequestHandler();

    public SharedSteps(SharedApiData sharedApiData) {
        this.sharedApiData = sharedApiData;
    }

    @Quando("enviar requisicao {} para o path {word}")
    @When("send a {} request to the path {word}")
    public void sendRequest(Method method, String path) {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .build();

        Response response = apiHelper.doRequest(sharedApiData.prepareRequest(requestSpec), method, path);
        sharedApiData.setResponse(response);
    }

    @E("defino o path param {word} com o valor do campo {word} da response anterior")
    @And("I define the path param {word} with the value of the field {word} from the previous response")
    public void setPathParamToRequest(String param, String responseBodyField) {
        sharedApiData.addPathParam(param, sharedApiData.getResponse().jsonPath().get(responseBodyField));
    }

    @E("o contrato deve estar de acordo com o {word}")
    @E("the contract should match {word}")
    public void validateResponseSchema(String jsonSchemaFile) {
        sharedApiData.setJsonSchemaFile(jsonSchemaFile);
        apiHelper.validateJSONSchema(sharedApiData.getResponse(), sharedApiData.getJsonSchemaFile());
    }

    @E("preencho no payload o campo {word} com o valor {string}")
    @And("I fill in the payload the field {word} with the value {string}")
    public void fillPayloadWithAlternativeValues(String field, String value) {
        sharedApiData.getBody().put(field, transformData(value));
    }

    @Entao("deve retornar o status code {int}")
    @Then("should return the status code {int}")
    public void validateResponseStatusCode(int statusCode) {
        Assert.assertEquals(sharedApiData.getResponse().getStatusCode(), statusCode);
    }
}
