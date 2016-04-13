package com.akamai.testing.aghlab.rest;



import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

import com.akamai.testing.aghlab.Configuration;
import org.junit.Test;


public class PositiveBankBackendTest {

    public static final String CARD_NUMBER = "4012888888881881";
    public static final String SHOP_ACCOUNT_NUMBER = "1234";

    @Test
    public void forValidCardInfoTransactionShouldBeAccepted() {


        int initialCardBalance = getCardBalance(CARD_NUMBER);
        int initialshopAccountBalance = getAccountBalance(SHOP_ACCOUNT_NUMBER);

        //@formatter:off
        given().
                log().all().
                baseUri(Configuration.BANK_HOST).
                formParam("target_account_iban", "1234").
                formParam("amount", "600").
                formParam("cc_number", CARD_NUMBER).
                formParam("cc_owner", "MACIEK").
                formParam("cc_csc", 757).
        when().
                post("/bank/pay").
        then().
                log().all().
                statusCode(200);
        //@formatter:on

        int cardBalance = getCardBalance(CARD_NUMBER);
        int shopAccountBalance = getAccountBalance(SHOP_ACCOUNT_NUMBER);

        assertThat(cardBalance, lessThan(initialCardBalance));
        assertThat(shopAccountBalance, greaterThan(initialshopAccountBalance));
    }

    private int getCardBalance(String cardNumber) {
        return
        //@formatter:off
        given().
                log().all().
                baseUri(Configuration.BANK_HOST).
                pathParam("cardNumber", cardNumber).
        when().
                get("/bank/cards/{cardNumber}/balance").
        then().
                log().all().
                statusCode(200).extract().jsonPath().getInt("balance");
        //@formatter:on
    }

    private int getAccountBalance(String accountNumber) {
        return
                //@formatter:off
        given().
                log().all().
                baseUri(Configuration.BANK_HOST).
                pathParam("accountNumber", accountNumber).
        when().
                get("/bank/accounts/{accountNumber}/balance").
        then().
                log().all().
                statusCode(200).extract().jsonPath().getInt("balance");
        //@formatter:on
    }


}
