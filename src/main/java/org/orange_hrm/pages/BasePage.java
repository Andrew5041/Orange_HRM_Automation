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

    public String getLoggedEmployeeName() {
        return getElementText(loggedEmployeeName);
    }

    public void clickButton(WebElement webElement) {
        logger.info("Attempting to click on element {}", webElement);
        WebElement element = waitHelper.waitForElementToBeClickable(webElement);

        try {
            element.click();
            logger.info("Successfully clicked on element {}", element);
        } catch (ElementNotInteractableException e) {
            logger.warn("First attempt to click element {}", element + "failed. Attempting to scroll to element and click");
            scrollToElementAndClick(element);
        }
    }

    private void scrollToElementAndClick(WebElement element) {
        try {
            actions.scrollToElement(element).click(element).build().perform();
            logger.info("Successfully clicked on element {} ", element + "via scroll to element and click ");
        } catch (ElementClickInterceptedException e) {
            logger.warn("Scroll to element and click failed. Attempting to move to element and click");
            moveToElementAndClick(element);
        }
    }

    private void moveToElementAndClick(WebElement element) {
        try {
            actions.moveToElement(element).click().build().perform();
            logger.info("Successfully clicked on element {} ", element + "via move to element and click ");
        } catch (ElementClickInterceptedException e) {
            logger.warn("Move to element and click failed. Attempting to click via JS");
            clickViaJS(element);
        }
    }

    private void clickViaJS(WebElement element) {
        try {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
            logger.info("Successfully clicked on element via JavaScript execution.");
        } catch (WebDriverException e) {
            logger.error("Failed to click element {}", element + " via JS.");
            throw new RuntimeException("Not able to click the element " + element);
        }
    }

    public void type(WebElement webelement, String text) {
        if (text == null) {
            logger.error("Attempting to type null text.");
            throw new IllegalArgumentException("Text cannot be null.");
        }
        WebElement element = waitHelper.waitForVisibility(webelement);

        clearInput(element);
        try {
            element.sendKeys(text);
            logger.info("Successfully typed text into element {}", element);
        } catch (ElementNotInteractableException e) {
            logger.warn("Standard send keys failed. Attempting to send text via scroll to element and type.");
            scrollToElementAndType(element, text);
        }
    }

    private void scrollToElementAndType(WebElement element, String text) {
        try {
            actions.scrollToElement(element).click(element).sendKeys(text).build().perform();
            logger.info("Successfully typed text via scroll and type.");
        } catch (ElementNotInteractableException e) {
            logger.warn("Scroll to element and type failed. Attempting to move to element and type.");
            moveToElementAndType(element, text);
        }
    }

    private void moveToElementAndType(WebElement element, String text) {
        try {
            actions.moveToElement(element).click(element).sendKeys(text).build().perform();
            logger.info("Successfully typed text via moveToElement");
        } catch (ElementNotInteractableException e) {
            logger.warn("Move to element and type failed. Attempting to type via JS.");
            typeViaJS(element, text);
        }
    }

    private void typeViaJS(WebElement element, String text) {
        try {
            ((JavascriptExecutor) getDriver()).executeScript(
                    "arguments[0].value = arguments[1];" +
                            "arguments[0].dispatchEvent(new Event('input', {bubbles: true}))"
                    , element, text);
            logger.info("Successfully typed text via JS");
        } catch (WebDriverException e) {
            logger.error("Failed to send text " + text + " to element {}", element + " via JS.");
            throw new RuntimeException("Not able to send the text " + text + "to the element " + element);
        }
    }

    public void clearInput(WebElement webElement) {
        logger.info("Clearing input field");
        WebElement element = waitHelper.waitForVisibility(webElement);

        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);

        if (!(element.getDomProperty("value").equals(""))) {
            try {
                element.clear();
                logger.info("Successfully cleared the input for element {}", element);
            } catch (ElementNotInteractableException e) {
                logger.error("Failed to clear the input for element {}", element);
                throw new RuntimeException("Not able to clear the element " + element);
            }
        }
    }

    public void chooseOptionFromList(List<WebElement> options, String text) {
        logger.info("Choosing option '{}' from the dropdown list", text);
        waitHelper.waitForVisibilityOfElements(options);

        WebElement option = options.stream()
                .filter(element -> element.getText().equals(text.trim()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Option " + text + " not found"));

        clickButton(option);
        logger.info("Option {}", text + "was successfully chosen");
    }

    public void uploadFile(WebElement file, String relativePath) {
        String absolutePath = Path.of(relativePath).toAbsolutePath().toString();
        logger.info("Attempting to upload file from path: {}", absolutePath);

        if (!Files.exists(Path.of(absolutePath))) {
            logger.error("File to upload does not exist at path: {}", absolutePath);
            throw new IllegalArgumentException("There is no file in : " + absolutePath);
        }
        try {
            file.sendKeys(absolutePath);
            logger.info("File successfully uploaded");
        } catch (WebDriverException e) {
            logger.error("File upload failed for path: {}", absolutePath);
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
