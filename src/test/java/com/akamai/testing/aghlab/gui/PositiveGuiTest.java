package com.akamai.testing.aghlab.gui;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class PositiveGuiTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
    }

    @Test
    public void forValidCardInfoTransactionShouldBeAccepted() {

        // given
        driver.get("http://localhost:8080/shop/summary");

        Select products = new Select(driver.findElement(By.name("product_id")));
        products.selectByVisibleText("Canon EOS 5000D");

        driver.findElement(By.name("cc_number"))
                .sendKeys("4012888888881881");

        driver.findElement(By.name("cc_csc"))
                .sendKeys("757");

        WebElement cc_owner = driver.findElement(By.name("cc_owner"));

        cc_owner.sendKeys("MACIEK");

        // when
        cc_owner.submit();

        // then
        driver.getPageSource().contains("Transaction accepted");
    }

    @After
    public void tearDown() {
        driver.close();
    }

}
