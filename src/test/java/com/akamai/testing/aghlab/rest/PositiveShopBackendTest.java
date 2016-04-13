package com.akamai.testing.aghlab.rest;


import com.akamai.testing.aghlab.Configuration;
import com.jayway.restassured.http.ContentType;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasXPath;


public class PositiveShopBackendTest {

    @Test
    public void forValidCardInfoTransactionShouldBeAccepted() {

        //@formatter:off
        given().
                log().all().
                baseUri(Configuration.SHOP_HOST).
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
                statusCode(200).
                body(hasXPath("/html/body/h2[text()='Transaction accepted']"));
        //@formatter:on

    }


}
