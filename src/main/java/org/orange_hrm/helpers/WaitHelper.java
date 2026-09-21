package org.orange_hrm.helpers;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.orange_hrm.driver.DriverSingleton;

import java.time.Duration;

public class WaitHelper {

    private final FluentWait<WebDriver> fluentWait;

    public WaitHelper() {
        fluentWait = new FluentWait<>(DriverSingleton.getDriver())
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class, TimeoutException.class);
    }

    public WebElement waitForVisibility(WebElement webElement) {
        try {
            fluentWait.until(ExpectedConditions.visibilityOf(webElement));
        } catch (TimeoutException e) {
            throw new TimeoutException("Element " + webElement + " not found");
        }
        return webElement;
    }

    public WebElement waitForInvisibility(WebElement webElement) {
        try {
            fluentWait.until(ExpectedConditions.invisibilityOf(webElement));
        } catch (TimeoutException e) {
            throw new TimeoutException("Element " + webElement + " not found");
        }
        return webElement;
    }

    public WebElement waitForElementToBeClickable(WebElement webElement) {
        try {
            fluentWait.until(ExpectedConditions.elementToBeClickable(webElement));
        } catch (TimeoutException e) {
            throw new TimeoutException("Element " + webElement + " not found");
        }
        return webElement;
    }
}

