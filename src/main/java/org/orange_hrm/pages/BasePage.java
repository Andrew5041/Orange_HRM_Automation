package org.orange_hrm.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.orange_hrm.helpers.WaitHelper;

import static org.orange_hrm.driver.DriverSingleton.getDriver;

public class BasePage {

    private WaitHelper waitHelper;

    public BasePage() {
        PageFactory.initElements(getDriver(), this);
        waitHelper = new WaitHelper();
    }

    public void clickButton(WebElement webElement) {
        try {
            webElement.click();
        } catch (WebDriverException e) {
            WebElement button = waitHelper.waitForElementToBeClickable(webElement);
            new Actions(getDriver()).moveToElement(button).click().perform();
        }
    }

    public String getElementText(WebElement webElement) {
        return waitHelper.waitForVisibility(webElement).getText().trim();
    }

    public void waitForElementToDisappear(WebElement webElement) {
        waitHelper.waitForInvisibility(webElement);
    }

    public void waitForElementToAppear(WebElement webElement) {
        waitHelper.waitForVisibility(webElement);
    }
}
