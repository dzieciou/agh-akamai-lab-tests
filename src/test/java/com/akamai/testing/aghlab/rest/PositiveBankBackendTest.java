package com.akamai.testing.aghlab.rest;



import static com.jayway.restassured.RestAssured.given;
import org.junit.Test;


public class PositiveBankBackendTest {

    @Test
    public void forValidCardInfoTransactionShouldBeAccepted() {

        //@formatter:off
        given().
                log().all().
                baseUri("http://localhost:8090").
                formParam("target_account_iban", "9484984948948498").
                formParam("amount", "600").
                formParam("cc_number", "4012888888881881").
                formParam("cc_owner", "MACIEK").
                formParam("cc_csc", 757).
        when().
                post("/bank/pay").
        then().
                log().all().
                statusCode(200);
        //@formatter:on

    }


}
