package org.orange_hrm.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.PageFactory;
import org.orange_hrm.helpers.WaitHelper;

import static org.orange_hrm.driver.DriverSingleton.getDriver;

public class BasePage {

    private Actions actions;

    private WaitHelper waitHelper;

    public BasePage() {
        PageFactory.initElements(getDriver(), this);
        waitHelper = new WaitHelper();
        actions = new Actions(getDriver());
    }

    public void refreshPage() {
        getDriver().navigate().refresh();
    }

    public void clickButton(WebElement webElement) {
        WebElement element = waitHelper.waitForElementToBeClickable(webElement);
        try {
            element.click();
            //Dodać logger - na zajęciach
        } catch (NoSuchElementException e) {
            //TODO:
            //Wydzielić poniższego try-catcha do oddzielnej metody
            try {
                actions.moveToElement(element).click().build().perform();
            } catch (MoveTargetOutOfBoundsException | ElementNotInteractableException e2) {
                //TODO:
                //Dodać obsługe za pomocą JS
                ////W momencie kiedy żadna inna metoda nie działa, próbujemy za pomocą JS kliknąć button
            }
        }
    }



    public void type(WebElement webelement, String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }
        WebElement element = waitHelper.waitForVisibility(webelement);

        //TODO:
        //Dodać metode, która bedzie czyściła na różne sposoby
        //Obsługiwała za pomocą actions i JS
        element.clear();
        try {
            element.sendKeys(text);
            //TODO:
            //Dodać loggera - na zajęciach
        } catch (NoSuchElementException e) {
            try {
                actions.moveToElement(element).sendKeys(text).build().perform();
            } catch (MoveTargetOutOfBoundsException | ElementNotInteractableException e2) {
                //TODO:
                //Dodać obsługe za pomocą JS
                //W momencie kiedy żadna inna metoda nie działa, próbujemy za pomocą JS wysłać tekst (sendkeys)
            }
        }
    }

    //ElementClickInterceptedException
    //ElementNotInteractableException
    //TimeoutException
    //NoSuchElementException
    //TODO:
    //Wrzucić poniższe exeptiony do powyższych metod


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
