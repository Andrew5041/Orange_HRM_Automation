package org.orange_hrm.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.orange_hrm.helpers.WaitHelper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static org.orange_hrm.driver.DriverSingleton.getDriver;

public class BasePage {

    //TODO:
    //Zapisywać powtarzalne błędy ze strony

    //final Logger logger = LogManager.getLogManager().getLogger(getClass());
    protected final Logger logger = LogManager.getLogger(getClass());

    private Actions actions;

    private WaitHelper waitHelper;

    @FindBy(className = "oxd-loading-spinner")
    protected WebElement loadingSpinner;

    @FindBy(css = ".oxd-userdropdown-name")
    private WebElement loggedEmployeeName;

    @FindBy(css = ".oxd-brand-banner")
    private WebElement brandBanner;


    public BasePage() {
        PageFactory.initElements(getDriver(), this);
        waitHelper = new WaitHelper();
        actions = new Actions(getDriver());
    }

    public void refreshPage() {
        getDriver().navigate().refresh();
        logger.info("Page was refreshed");
        logger.info("Waiting for a banner to appear {}", brandBanner);
        waitHelper.waitForVisibility(brandBanner);
    }

    //WARN --> mogę informować że jest jakiś problem ale jest kontynuowany
    //ERROR
    //TODO:
    //Zastosować w metodach w Base Page Info, Warn i Error

    public String getLoggedEmployeeName() {
        return getElementText(loggedEmployeeName);
    }

    public void clickButton(WebElement webElement) {
        WebElement element = waitHelper.waitForElementToBeClickable(webElement);

        try {
            element.click();
            //TODO:
            //Dodać logger - na zajęciach
        } catch (ElementNotInteractableException e) {
            scrollToElementAndClick(element);
        }
    }

    private void scrollToElementAndClick(WebElement element) {
        try {
            actions.scrollToElement(element).click(element).build().perform();
        } catch (Exception e) {
            moveToElementAndClick(element);
        }
    }

    private void moveToElementAndClick(WebElement element) {
        try {
            actions.moveToElement(element).click().build().perform();
        } catch (Exception e) {
            clickViaJS(element);
        }
    }

    private void clickViaJS(WebElement element) {
        try {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
        } catch (WebDriverException e) {
            throw new RuntimeException("Not able to click the element " + element);
        }
    }

    public void type(WebElement webelement, String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }
        WebElement element = waitHelper.waitForVisibility(webelement);

        clearInput(element);
        try {
            element.sendKeys(text);
            //TODO:
            //Dodać loggera - na zajęciach
        } catch (ElementNotInteractableException e) {
            scrollToElementAndType(element, text);
        }
    }

    private void scrollToElementAndType(WebElement element, String text) {
        try {
            actions.scrollToElement(element).click(element).sendKeys(text).build().perform();
        } catch (Exception e) {
            moveToElementAndType(element, text);
        }
    }

    private void moveToElementAndType(WebElement element, String text) {
        try {
            actions.moveToElement(element).click(element).sendKeys(text).build().perform();
        } catch (Exception e) {
            typeViaJS(element, text);
        }
    }

    private void typeViaJS(WebElement element, String text) {
        try {
            ((JavascriptExecutor) getDriver()).executeScript(
                    "arguments[0].value = arguments[1];" +
                            "arguments[0].dispatchEvent(new Event('input', {bubbles: true}))"
                    , element, text);
        } catch (WebDriverException e) {
            throw new RuntimeException("Not able to send the text " + text + "to the element " + element);
        }
    }

    public void clearInput(WebElement webElement) {
        WebElement element = waitHelper.waitForVisibility(webElement);

        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);

        if (!(element.getDomProperty("value").equals(""))) {
            try {
                element.clear();
                //TODO:
                //Poprawić zbyt ogólne Exceptiony
            } catch (Exception e) {
                throw new RuntimeException("Not able to clear the element " + element);
            }
        }
    }

    public void chooseOptionFromList(List<WebElement> options, String text) {
        waitHelper.waitForVisibilityOfElements(options);

        WebElement option = options.stream()
                .filter(element -> element.getText().equals(text.trim()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Option " + text + " not found"));

        clickButton(option);
    }

    public void uploadFile(WebElement file, String relativePath) {
        String absolutePath = Path.of(relativePath).toAbsolutePath().toString();

        if (!Files.exists(Path.of(absolutePath))) {
            throw new IllegalArgumentException("There is no file in : " + absolutePath);
        }
        try {
            file.sendKeys(absolutePath);
        } catch (WebDriverException e) {
            throw new RuntimeException("File upload failed");
        }
    }

    public String getElementText(WebElement webElement) {
        return waitHelper.waitForVisibility(webElement).getText().trim();
    }

    public String getInputElementText(WebElement webElement) {
        return waitHelper.waitForVisibility(webElement).getAttribute("value");
    }

    public void waitForElementToDisappear(WebElement webElement) {
        waitHelper.waitForInvisibility(webElement);
    }

    public void waitForElementToAppear(WebElement webElement) {
        waitHelper.waitForVisibility(webElement);
    }
}
