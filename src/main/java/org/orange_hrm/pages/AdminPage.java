package org.orange_hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.orange_hrm.driver.DriverSingleton.getDriver;

public class AdminPage {

    @FindBy(xpath = "//label[text()='Username']/ancestor::div[contains(@class, 'oxd-input-group')]//input")
    WebElement usernameInput;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement searchButton;

    @FindBy(xpath = "//div[@class='oxd-toast-content oxd-toast-content--info']//p[text()='No Records Found']")
    WebElement noRecordsFoundPopup;

    public AdminPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public AdminPage searchUser(String username) {
        usernameInput.sendKeys(username);
        searchButton.click();
        return this;
    }

    public boolean isUserDataPresentInTable(String userData) {
        String xpathLocator = "//div[@role='cell']//div[text()='" + userData + "']";
        WebElement cellElement = getDriver().findElement(By.xpath(xpathLocator));
        return cellElement.isDisplayed();
    }

    public boolean isNoRecordsFoundPopupVisible(){
        return noRecordsFoundPopup.isDisplayed();
    }
}
