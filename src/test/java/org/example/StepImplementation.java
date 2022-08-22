package org.example;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.github.javafaker.Faker;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import org.junit.Assert;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class StepImplementation extends BasePage {

    Faker faker = new Faker();
    String name = faker.name().fullName();
    String phoneNumber = faker.number().digits(12);
    // Phonenumber olarak seçildiği zaman noktalama işaretleri de gelebiliyor ve test düzgün yapılamıyordu.
    // Bu nedenle number seçilip 12 dijit sınırlaması koyuldu.
    String email = "test@test.com";
    // Faker kütüphanesinde random email olmadığı için bu şekilde tanımlandı.

    @Step("Navigate to <text>")
    public void navigate(String text) {
        driver.get(text);
    }

    @Step("Click on <key> element")
    public void click(String key) {
        clickElement((key));
        logger.info("Clicked on " + key + " element");
    }

    @Step("Type <text> in the <key> element")
    public void type(String text, String key) {
        sendKeys((key), text);
        logger.info("Typed " + text + " in " + key + " element");
    }

    @Step ("Send defined keys to <key1> <key2> <key3> elements")
    public void sendKeys (String key1, String key2, String key3){
        sendKeys(key1,email);
        sendKeys(key2,name);
        sendKeys(key3,phoneNumber);
    }

    @Step("Click from <key> list <i> element")
    public void selectFromList(String key, int i) {
        clickSpecificElementFromList(key,i);
        logger.info("Element selected from " + key + " list");
    }

    @Step("Wait <wait> seconds")
    public void waitSeconds(int wait) throws InterruptedException {
        Thread.sleep(1000 * wait);
        logger.info("Wait " + wait + " seconds");
    }

    @Step("Check the <text> is displayed in the <key> element")
    public void displayedAssert(String text, String key) {
        Assert.assertEquals(text + "is not displayed", text, getText(key));
        logger.info(text + " is displayed on " + key + " element");
    }

    @Step ("Switch to iframe")
    public void switchToIframe(){
        driver.switchTo().frame("psn-widget-content-frame");
    }
}



