package com.akamai.testing.aghlab.rest;


import com.jayway.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasXPath;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class NegativeTestWithMockedBank {


    private ClientAndServer serverMock;

    @Before
    public void setupMock() {
        serverMock = startClientAndServer(8090);
        serverMock.
                when(request().
                        withMethod("POST").
                        withPath("/bank/pay")).
                respond(response().
                        withStatusCode(200));
    }

    @Test
    public void test() {
        //@formatter:off
        given().
                log().all().
                baseUri("http://localhost:8080").
                contentType(ContentType.URLENC).
                formParam("target_account_iban", "9484984948948498").
                formParam("product_id", "600").
                formParam("cc_number", "4012888888881881").
                formParam("cc_owner", "MACIEK").
                formParam("cc_csc", 757).
        when().
                post("/shop/order").
        then().
                log().all().
                statusCode(401).
                body(hasXPath("/html/body/h2[text()='Transaction rejected']"));;
        //@formatter:on
    }

    @After
    public void closeMock() {
        serverMock.stop();
    }

}
