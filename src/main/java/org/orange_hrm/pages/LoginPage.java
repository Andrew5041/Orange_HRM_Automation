package org.orange_hrm.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.orange_hrm.driver.DriverSingleton.getDriver;

public class LoginPage {

    @FindBy(name = "username")
    private WebElement usernameInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//p[text()='Invalid credentials']")
    private WebElement errorMessage;

    public LoginPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public LoginPage loginSuccessfully(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
        return this;
    }

    public LoginPage loginWithFailure(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
        return this;
    }

    public boolean isErrorMessagedDisplayed() {
        return errorMessage.isDisplayed();
    }
}